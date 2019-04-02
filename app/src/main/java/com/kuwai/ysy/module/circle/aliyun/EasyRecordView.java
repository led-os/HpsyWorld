package com.kuwai.ysy.module.circle.aliyun;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.aliyun.common.global.AliyunTag;
import com.aliyun.common.utils.CommonUtil;
import com.aliyun.recorder.AliyunRecorderCreator;
import com.aliyun.recorder.supply.AliyunIClipManager;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.aliyun.recorder.supply.EncoderInfoCallback;
import com.aliyun.recorder.supply.RecordCallback;
import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.effect.EffectBean;
import com.aliyun.svideo.sdk.external.struct.effect.EffectFilter;
import com.aliyun.svideo.sdk.external.struct.effect.EffectPaster;
import com.aliyun.svideo.sdk.external.struct.encoder.EncoderInfo;
import com.aliyun.svideo.sdk.external.struct.encoder.VideoCodecs;
import com.aliyun.svideo.sdk.external.struct.recorder.CameraType;
import com.aliyun.svideo.sdk.external.struct.recorder.FlashType;
import com.aliyun.svideo.sdk.external.struct.recorder.MediaInfo;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.google.gson.Gson;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.aliyun.dialog.BeautyEffectChooser;
import com.kuwai.ysy.module.circle.aliyun.dialog.DialogVisibleListener;
import com.kuwai.ysy.module.circle.aliyun.effects.face.listener.OnBeautyDetailClickListener;
import com.kuwai.ysy.module.circle.aliyun.effects.face.listener.OnBeautyFaceItemSeletedListener;
import com.kuwai.ysy.module.circle.aliyun.effects.face.listener.OnBeautyModeChangeListener;
import com.kuwai.ysy.module.circle.aliyun.effects.face.listener.OnBeautySkinItemSeletedListener;
import com.kuwai.ysy.module.circle.aliyun.effects.filter.EffectInfo;
import com.kuwai.ysy.module.circle.aliyun.effects.filter.interfaces.OnFilterItemClickListener;
import com.kuwai.ysy.utils.OrientationDetector;
import com.kuwai.ysy.utils.PermissionUtils;
import com.kuwai.ysy.utils.SaveToGallery;
import com.kuwai.ysy.utils.TimeFormatterUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CountDownProgressBar;
import com.kuwai.ysy.widget.TakeView;
import com.qu.preview.callback.OnFrameCallBack;
import com.qu.preview.callback.OnTextureIdCallBack;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EasyRecordView extends RelativeLayout
        implements DialogVisibleListener, ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = EasyRecordView.class.getSimpleName();
    private static final String TAG_BEAUTY_CHOOSER = "beauty";
    //最小录制时长
    private static final int MIN_RECORD_TIME = 0;
    //最大录制时长
    private static final int MAX_RECORD_TIME = Integer.MAX_VALUE;
    /**
     * 美颜美肌当前page的下标, 根据这个下标, 读取相应的档位参数
     */
    private static final int PAGE_TAB_BEAUTY_FACE = 1;
    private static final int PAGE_TAB_BEAUTY_SKIN = 2;
    private static int TEST_VIDEO_WIDTH = 540;
    private static int TEST_VIDEO_HEIGHT = 960;
    private GLSurfaceView mGLSurfaceView;
    private RecordTimelineView mRecordTimeView;
    private AlivcCountDownView mCountDownView;
    private AliyunIRecorder recorder;
    private AliyunIClipManager clipManager;
    private CameraType cameraType
            = CameraType.FRONT;
    private FragmentActivity mActivity;
    private boolean isOpenFailed = false;
    //正在准备录制视频,readyview显示期间为true，其他为false
    private boolean isLoadingReady = false;
    //录制视频是否达到最大值
    private boolean isMaxDuration = false;
    //录制时长
    private int recordTime = 0;
    //文件存放位置
    private String videoPath;

    //最小录制时长
    private int minRecordTime = 2000;
    //最大录制时长
    private int maxRecordTime = 15 * 1000;
    //录制码率
    private int mBitrate = 25;
    //关键帧间隔
    private int mGop = 5;
    //视频质量
    private VideoQuality mVideoQuality = VideoQuality.HD;
    //视频比例
    private int mRatioMode = AliyunSnapVideoParam.RATIO_MODE_3_4;
    //编码方式
    private VideoCodecs mVideoCodec = VideoCodecs.H264_HARDWARE;
    //视频分辨率
    private int mResolutionMode = AliyunSnapVideoParam.RESOLUTION_540P;

    private OrientationDetector orientationDetector;
    private int rotation;
    /**
     * 第三方高级美颜支持库faceUnity管理类
     */
    private FaceUnityManager faceUnityManager;
    /**
     * 相机的原始NV21数据
     */
    private byte[] frameBytes;
    private byte[] mFuImgNV21Bytes;
    /**
     * 原始数据宽
     */
    private int frameWidth;
    /**
     * 原始数据高
     */
    private int frameHeight;
    /**
     * faceUnity相关
     */
    private int mFrameId = 0;
    private BeautyParams beautyParams;
    /**
     * 美肌美颜微调dialog是否正在显示
     */
    private boolean isbeautyDetailShowing;

    /**
     * 是否需要使用faceUnity false : 如果当前是普通美颜, 则不需要使用 true: 如果当前是高级美颜, 或者使用了美肌效果, 需要使用
     */
    private boolean isUseFaceUnity = false;

    /**
     * 高级美颜参数值 美白, 红润, 磨皮
     */
    private float beautyColorLevel;
    private float beautyRedLevel;
    private float beautyBlurLevel;
    /**
     * 美颜默认档位
     */
    private BeautyLevel defaultBeautyLevel = BeautyLevel.BEAUTY_LEVEL_THREE;
    /**
     * 当前美颜模式
     */
    private BeautyMode currentBeautyFaceMode = BeautyMode.Advanced;
    public static final int TYPE_FILTER = 1;
    public static final int TYPE_MV = 2;
    public static final int TYPE_MUSIC = 3;
    private EffectBean effectMv;
    //
    private LinkedHashMap<Integer, Object> mConflictEffects = new LinkedHashMap<>();
    private EffectBean effectMusic;
    private AsyncTask<Void, Void, Void> finishRecodingTask;
    private AsyncTask<Void, Void, Void> faceTrackPathTask;

    /**
     * 记录filter选中的item索引
     */
    private int currentFilterPosition;
    /**
     * 记录美颜选中的索引, 默认为3
     */
    private int currentBeautyFacePosition = 3;
    /**
     * 当前美肌选择的item下标, 默认为3
     */
    private int currentBeautySkinPosition = 3;
    /**
     * 控制mv的添加, 开始录制后,不允许切换mv
     */
    private boolean isAllowChangeMv = true;
    private AsyncTask<Void, Void, Void> mFaceUnityTask;
    private List<BeautyParams> rememberParamList;
    private RememberBeautyBean rememberBeautyBean;
    private AsyncTask<Void, Void, Void> beautyParamCopyTask;
    private int currentTabIndex = PAGE_TAB_BEAUTY_FACE;
    private ProgressDialog progressBar;
    private BeautyParams rememberParam;

    /**
     * 用于判断当前音乐界面是否可见, 如果可见, 从后台回到前台时, restoreConflictEffect()不恢复mv的播放, 否则会和音乐重复播放
     * <p>
     * true: 可见 false: 不可见
     */
    private boolean isMusicViewShowing;

    /**
     * faceUnity的初始化结果 true: 初始化成功 false: 初始化失败
     */
    private static boolean faceInitResult;

    /**
     * 底层在onPause时会回收资源, 此处选择的滤镜的资源路径, 用于恢复状态
     */
    private String filterSourcePath;

    /**
     * 美颜美肌点击了back按钮
     */
    private boolean isbeautyDetailBack;
    CountDownProgressBar countDownProgressBar = null;
    private TakeView takeView;

    public EasyRecordView(Context context) {
        super(context);
        initVideoView();
    }

    public EasyRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView();
    }

    public EasyRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView();
    }

    private void initVideoView() {
        //初始化surfaceView

        initSurfaceView();
        takeView = new TakeView(getContext());
        addSubView(takeView);
        takeView.setCallback(new TakeView.TakeCallback() {
            @Override
            public void cancelCallback() {
                if (mBackClickListener != null) {
                    mBackClickListener.onClick();
                }
            }
        });
        initCountDownView();
        initRecorder();
        initRecordTimeView();
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        if (recorder != null) {
            recorder.startPreview();
        }
        if (orientationDetector != null && orientationDetector.canDetectOrientation()) {
            orientationDetector.enable();
        }
    }

    /**
     * 设置视频质量
     *
     * @param mVideoQuality
     */
    public void setVideoQuality(VideoQuality mVideoQuality) {
        this.mVideoQuality = mVideoQuality;
        if (recorder != null) {
            recorder.setVideoQuality(mVideoQuality);
        }
    }

    /**
     * 设置视频码率
     *
     * @param mResolutionMode
     */
    public void setResolutionMode(int mResolutionMode) {
        this.mResolutionMode = mResolutionMode;
        if (recorder != null) {
            recorder.setMediaInfo(getMediaInfo());
        }
    }

    /**
     * 设置视频比例
     *
     * @param mRatioMode
     */
    public void setRatioMode(int mRatioMode) {
        this.mRatioMode = mRatioMode;
        if (recorder != null) {
            recorder.setMediaInfo(getMediaInfo());

        }
        if (mGLSurfaceView != null) {
            LayoutParams params = (LayoutParams) mGLSurfaceView.getLayoutParams();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;

            int height = 0;
            switch (mRatioMode) {
                case AliyunSnapVideoParam.RATIO_MODE_1_1:
                    height = screenWidth;
                    break;
                case AliyunSnapVideoParam.RATIO_MODE_3_4:
                    height = screenWidth * 4 / 3;
                    break;
                case AliyunSnapVideoParam.RATIO_MODE_9_16:
                    height = screenWidth * 16 / 9;
                    break;
                default:
                    height = screenWidth * 16 / 9;
                    break;
            }
            params.height = height;
            mGLSurfaceView.setLayoutParams(params);
        }

    }

    /**
     * 结束预览
     */
    public void stopPreview() {
        mGLSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                /**
                 * 释放faceUnity相关，因为faceUnity的销毁必须要再渲染线程中做.我们使用queueEvent来保证release方法在渲染线程中使用
                 * 目前放在stopPreview中调用release,主要是保证存在mGLSurfaceView没有销毁GL线程还存在
                 */
                if (faceUnityManager != null && faceInitResult) {
                    faceUnityManager.release();
                    faceInitResult = false;
                }
            }
        });
        //解决部分机型锁屏音乐不停止的问题，以后sdk同学需要解决
        if (isAllowChangeMv) {
            recorder.applyMv(null);
        }
        recorder.stopPreview();

        if (mFaceUnityTask != null) {
            mFaceUnityTask.cancel(true);
            mFaceUnityTask = null;
        }
        if (orientationDetector != null) {
            orientationDetector.disable();
        }

    }

    /**
     * 销毁录制，在activity或者fragment被销毁时调用此方法
     */
    public void destroyRecorder() {

        if (finishRecodingTask != null) {
            finishRecodingTask.cancel(true);
            finishRecodingTask = null;
        }

        if (faceTrackPathTask != null) {
            faceTrackPathTask.cancel(true);
            faceTrackPathTask = null;
        }

        if (beautyParamCopyTask != null) {
            beautyParamCopyTask.cancel(true);
            beautyParamCopyTask = null;
        }

        if (recorder != null) {
            recorder.destroy();
            Log.i(TAG, "destroy");
        }

        if (orientationDetector != null) {
            orientationDetector.setOrientationChangedListener(null);
        }
    }

    /**
     * 初始化倒计时view
     */
    private void initCountDownView() {
        if (mCountDownView == null) {
            mCountDownView = new AlivcCountDownView(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            //addView(mCountDownView, params);

        }
    }

    private void initRecordTimeView() {
        mRecordTimeView = new RecordTimelineView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, Utils.dip2px(getContext(), 5));
        mRecordTimeView.setColor(R.color.alivc_green, R.color.aliyun_colorPrimary, R.color.aliyun_white,
                R.color.alivc_bg_record_time);
        mRecordTimeView.setMaxDuration(clipManager.getMaxDuration());
        mRecordTimeView.setMinDuration(clipManager.getMinDuration());
        //addView(mRecordTimeView, params);

        /*countDownProgressBar = new CountDownProgressBar(getContext());
        LayoutParams params1 = new LayoutParams(200, 200);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.bottomMargin = 400;
        countDownProgressBar.setDuration(5000, new CountDownProgressBar.OnFinishListener() {
            @Override
            public void onFinish() {
                ToastUtils.showShort("完成了");
            }
        });
        addView(countDownProgressBar,params1);*/
    }

    /**
     * 初始化surfaceView
     */
    private void initSurfaceView() {
        mGLSurfaceView = new GLSurfaceView(getContext());
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        final GestureDetector gestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        float x = e.getX() / mGLSurfaceView.getWidth();
                        float y = e.getY() / mGLSurfaceView.getHeight();
                        recorder.setFocus(x, y);
                        return true;
                    }
                });
        mGLSurfaceView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() >= 2) {
                    scaleGestureDetector.onTouchEvent(event);
                } else if (event.getPointerCount() == 1) {
                    gestureDetector.onTouchEvent(event);
                }
                return true;
            }
        });
        addSubView(mGLSurfaceView);
    }

    /**
     * 权限申请
     */
    String[] permission = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 开始录制
     */
    public void startRecord() {
        boolean checkResult = PermissionUtils.checkPermissionsGroup(getContext(), permission);
        if (!checkResult && mActivity != null) {
            PermissionUtils.requestPermissions(mActivity, permission,
                    VideoRecordActivity.PERMISSION_REQUEST_CODE);
            return;
        }

        if (CommonUtil.SDFreeSize() < 50 * 1000 * 1000) {
            ToastUtils.showShort(getResources().getString(R.string.aliyun_no_free_memory));
            return;
        }
        if (isMaxDuration) {
            //mControlView.setRecordState(RecordState.STOP);
            return;
        }
        if (recorder != null) {
            //mControlView.setRecordState(RecordState.RECORDING);
            //mControlView.setRecording(true);
            videoPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + System.currentTimeMillis() + ".mp4";
            recorder.setOutputPath(videoPath);
            recorder.startRecording();
            Log.d(TAG, "startRecording    isStopToCompleteDuration:" + isStopToCompleteDuration);

        }

    }

    /**
     * 视频是是否正正在已经调用stopRecord到onComplete回调过程中这段时间，这段时间不可再次调用stopRecord
     */
    private boolean isStopToCompleteDuration;

    /**
     * 停止录制
     */
    private void stopRecord() {
        Log.d(TAG, "stopRecord    isStopToCompleteDuration:" + isStopToCompleteDuration);
        if (recorder != null && !isStopToCompleteDuration) {//
            isStopToCompleteDuration = true;
            //此处添加判断，progressBar弹出，也即当视频片段合成的时候，不调用stopRecording,
            //否则在finishRecording的时候调用stopRecording，会导致finishRecording阻塞
            //暂时规避，等待sdk解决该问题，取消该判断
            if ((progressBar == null || !progressBar.isShowing())) {
                recorder.stopRecording();

            }

            if (effectMv != null && !TextUtils.isEmpty(effectMv.getPath())) {
                recorder.pauseMv();
            }

        }
    }

    /**
     * 取消拍摄倒计时
     */
    private void cancelReadyRecord() {
        if (mCountDownView != null) {
            mCountDownView.cancle();
        }
    }

    /**
     * 显示准备拍摄倒计时view
     */
    private void showReadyRecordView() {
        if (mCountDownView != null) {
            mCountDownView.start();
        }
    }

    private FragmentManager getFragmentManager() {
        FragmentManager fm = null;
        if (mActivity != null) {
            fm = mActivity.getSupportFragmentManager();
        } else {
            Context mContext = getContext();
            if (mContext instanceof FragmentActivity) {
                fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            }
        }
        return fm;
    }

    private void initRecorder() {
        recorder = AliyunRecorderCreator.getRecorderInstance(getContext());
        recorder.setDisplayView(mGLSurfaceView);
        clipManager = recorder.getClipManager();
        clipManager.setMaxDuration(5000);
        clipManager.setMinDuration(getMaxRecordTime());
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setVideoWidth(TEST_VIDEO_WIDTH);
        mediaInfo.setVideoHeight(TEST_VIDEO_HEIGHT);
        //mediaInfo.setHWAutoSize(true);//硬编时自适应宽高为16的倍数
        recorder.setMediaInfo(mediaInfo);
        cameraType = recorder.getCameraCount() == 1 ? CameraType.BACK
                : cameraType;
        recorder.setCamera(cameraType);
        recorder.setBeautyStatus(false);

        initOritationDetector();
        recorder.setOnFrameCallback(new OnFrameCallBack() {
            @Override
            public void onFrameBack(byte[] bytes, int width, int height, Camera.CameraInfo info) {
                //原始数据回调 NV21,这里获取原始数据主要是为了faceUnity高级美颜使用
                frameBytes = bytes;
                frameWidth = width;
                frameHeight = height;
            }

            @Override
            public Camera.Size onChoosePreviewSize(List<Camera.Size> supportedPreviewSizes,
                                                   Camera.Size preferredPreviewSizeForVideo) {

                return null;
            }

            @Override
            public void openFailed() {
                Log.e(AliyunTag.TAG, "openFailed----------");
                isOpenFailed = true;
            }
        });

        recorder.setRecordCallback(new RecordCallback() {
            @Override
            public void onComplete(final boolean validClip, final long clipDuration) {

                Log.e(TAG, "onComplete:" + validClip + clipDuration);
                Log.e(TAG, "thread" + Thread.currentThread().getName());
                post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onComplete    isStopToCompleteDuration:" + isStopToCompleteDuration);
                        isStopToCompleteDuration = false;
                        handleStopCallback(validClip, clipDuration);
                        if (isMaxDuration && validClip) {
                            finishRecording();
                        }

                    }
                });

            }

            /**
             * 合成完毕的回调
             * @param outputPath
             */
            @Override
            public void onFinish(final String outputPath) {
                Log.e(TAG, "onFinish:" + outputPath);

                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCompleteListener != null) {
                            int duration = clipManager.getDuration();
                            //deleteAllPart();
                            mCompleteListener.onComplete(outputPath, duration);
                        }
                    }
                });

            }

            @Override
            public void onProgress(final long duration) {
                Log.e(TAG, "onProgress:" + duration);
                isAllowChangeMv = false;

                //设置录制进度
                if (mRecordTimeView != null) {
                    mRecordTimeView.setDuration((int) duration);
                }
                recordTime = (int) duration + clipManager.getDuration();
            }

            @Override
            public void onMaxDuration() {
                Log.e(TAG, "onMaxDuration:");
                isMaxDuration = true;
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "onError:" + errorCode);
                recordTime = 0;

                handleStopCallback(false, 0);
            }

            @Override
            public void onInitReady() {
                Log.e(TAG, "onInitReady");
            }

            @Override
            public void onDrawReady() {

            }

            @Override
            public void onPictureBack(final Bitmap bitmap) {
                String path = SaveToGallery.saveImageToGallery(mActivity, bitmap);
                mPickPhotoListener.onComplete(path);
                Log.e("","");
            }

            @Override
            public void onPictureDataBack(final byte[] data) {

            }

        });
        recorder.setOnTextureIdCallback(new OnTextureIdCallBack() {
            @Override
            public int onTextureIdBack(int textureId, int textureWidth, int textureHeight, float[] matrix) {
                return textureId;
            }

            @Override
            public int onScaledIdBack(int scaledId, int textureWidth, int textureHeight, float[] matrix) {

                return scaledId;
            }
        });

        recorder.setEncoderInfoCallback(new EncoderInfoCallback() {
            @Override
            public void onEncoderInfoBack(EncoderInfo info) {
            }
        });
        recorder.setFaceTrackInternalMaxFaceCount(2);
    }

    /**
     * 删除所有录制文件
     */
    public void deleteAllPart() {
        if (clipManager != null) {
            clipManager.deleteAllPart();
            if (clipManager.getDuration() == 0) {
                mRecordTimeView.clear();
                isAllowChangeMv = true;
            }
        }
    }

    public void setRecordMute(boolean recordMute) {
        if (recorder != null) {
            recorder.setMute(recordMute);
        }
    }

    private void initOritationDetector() {
        orientationDetector = new OrientationDetector(getContext().getApplicationContext());
        orientationDetector.setOrientationChangedListener(new OrientationDetector.OrientationChangedListener() {
            @Override
            public void onOrientationChanged() {
                rotation = getPictureRotation();
                recorder.setRotation(rotation);
            }
        });
    }

    private int getPictureRotation() {
        int orientation = orientationDetector.getOrientation();
        int rotation = 90;
        if ((orientation >= 45) && (orientation < 135)) {
            rotation = 180;
        }
        if ((orientation >= 135) && (orientation < 225)) {
            rotation = 270;
        }
        if ((orientation >= 225) && (orientation < 315)) {
            rotation = 0;
        }
        if (cameraType == CameraType.FRONT) {
            if (rotation != 0) {
                rotation = 360 - rotation;
            }
        }
        return rotation;
    }

    /**
     * 结束录制，并且将录制片段视频拼接成一个视频
     * 跳转editorActivity在合成完成的回调的方法中，{@link AlivcSvideoRecordActivity#onResume()}
     */
    private void finishRecording() {
        //弹窗提示
        if (progressBar == null) {
            progressBar = new ProgressDialog(getContext());
            progressBar.setMessage("视频合成中....");
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressBar.show();
        //mControlView.setCompleteEnable(false);
        finishRecodingTask = new FinishRecodingTask(this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 录制结束的AsyncTask
     */
    public class FinishRecodingTask extends AsyncTask<Void, Void, Void> {
        WeakReference<EasyRecordView> weakReference;

        FinishRecodingTask(EasyRecordView recordView) {
            weakReference = new WeakReference<>(recordView);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EasyRecordView recordView = weakReference.get();
            if (recordView != null) {
                recordView.recorder.finishRecording();
                Log.e(TAG, "finishRecording");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressBar != null) {
                progressBar.dismiss();
            }

        }
    }

    /**
     * 片段录制完成的回调处理
     *
     * @param isValid
     * @param duration
     */
    private void handleStopCallback(final boolean isValid, final long duration) {
        post(new Runnable() {
            @Override
            public void run() {

                if (isValid && duration > 200) {

                    if (mRecordTimeView != null) {
                        mRecordTimeView.setDuration((int) duration);
                        mRecordTimeView.clipComplete();
                    }
                } else {
                    if (mRecordTimeView != null) {
                        mRecordTimeView.setDuration(0);
                    }
                }
                if (isValid) {
                    if (duration > 200) {
                        isAllowChangeMv = false;
                    } else {

                        clipManager.deletePart();
                        if (clipManager.getDuration() == 0) {
                            isAllowChangeMv = true;
                        }
                        isMaxDuration = false;
                    }
                }

            }
        });
    }

    /**
     * addSubView 添加子view到布局中
     *
     * @param view 子view
     */
    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, params);//添加到布局中
    }

    /**
     * 录制界面返回按钮click listener
     */
    private OnBackClickListener mBackClickListener;

    public void setBackClickListener(OnBackClickListener listener) {
        this.mBackClickListener = listener;
    }

    private OnFinishListener mCompleteListener;
    private OnPickPhotoListener mPickPhotoListener;

    @Override
    public void onDialogDismiss() {
    }

    @Override
    public void onDialogShow() {
    }

    private float lastScaleFactor;
    private float scaleFactor;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float factorOffset = detector.getScaleFactor() - lastScaleFactor;
        scaleFactor += factorOffset;
        lastScaleFactor = detector.getScaleFactor();
        if (scaleFactor < 0) {
            scaleFactor = 0;
        }
        if (scaleFactor > 1) {
            scaleFactor = 1;
        }
        recorder.setZoom(scaleFactor);
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        lastScaleFactor = detector.getScaleFactor();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 返回按钮事件监听
     */
    public interface OnBackClickListener {
        void onClick();
    }

    /**
     * 录制完成事件监听
     */
    public interface OnFinishListener {
        void onComplete(String path, int duration);
    }

    public void setCompleteListener(OnFinishListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    /**
     * 拍照完成事件监听
     */
    public interface OnPickPhotoListener {
        void onComplete(String path);
    }

    public void setPickPhotoListener(OnPickPhotoListener mCompleteListener) {
        this.mPickPhotoListener = mCompleteListener;
    }

    public void setActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 获取最大录制时长
     *
     * @return
     */
    public int getMaxRecordTime() {
        if (maxRecordTime < MIN_RECORD_TIME) {
            return MIN_RECORD_TIME;
        } else if (maxRecordTime > MAX_RECORD_TIME) {
            return MAX_RECORD_TIME;
        } else {

            return maxRecordTime;
        }

    }

    /**
     * 设置录制时长
     *
     * @param maxRecordTime
     */
    public void setMaxRecordTime(int maxRecordTime) {
        this.maxRecordTime = maxRecordTime;
        if (clipManager != null) {
            clipManager.setMaxDuration(getMaxRecordTime());
        }
        if (mRecordTimeView != null) {
            mRecordTimeView.setMaxDuration(getMaxRecordTime());
        }
    }

    /**
     * 设置最小录制时长
     *
     * @param minRecordTime
     */
    public void setMinRecordTime(int minRecordTime) {
        this.minRecordTime = minRecordTime;
        if (clipManager != null) {
            clipManager.setMinDuration(minRecordTime);
        }
        if (mRecordTimeView != null) {
            mRecordTimeView.setMinDuration(minRecordTime);
        }
    }

    /**
     * 设置码率
     *
     * @param mBitrate
     */
    public void setBitrate(int mBitrate) {
        this.mBitrate = mBitrate;
        if (recorder != null) {
            recorder.setVideoBitrate(mBitrate);
        }
    }

    /**
     * 设置Gop
     *
     * @param mGop
     */
    public void setGop(int mGop) {
        this.mGop = mGop;
        if (recorder != null) {
            recorder.setGop(mGop);
        }
    }

    private MediaInfo getMediaInfo() {
        MediaInfo info = new MediaInfo();
        info.setVideoWidth(getVideoWidth());
        info.setVideoHeight(getVideoHeight());
        info.setVideoCodec(mVideoCodec);
        info.setCrf(25);
        return info;
    }

    /**
     * 获取拍摄视频宽度
     *
     * @return
     */
    private int getVideoWidth() {
        int width = 0;
        switch (mResolutionMode) {
            case AliyunSnapVideoParam.RESOLUTION_360P:
                width = 360;
                break;
            case AliyunSnapVideoParam.RESOLUTION_480P:
                width = 480;
                break;
            case AliyunSnapVideoParam.RESOLUTION_540P:
                width = 540;
                break;
            case AliyunSnapVideoParam.RESOLUTION_720P:
                width = 720;
                break;
            default:
                width = 540;
                break;
        }

        return width;
    }

    private int getVideoHeight() {
        int width = getVideoWidth();
        int height = 0;
        switch (mRatioMode) {
            case AliyunSnapVideoParam.RATIO_MODE_1_1:
                height = width;
                break;
            case AliyunSnapVideoParam.RATIO_MODE_3_4:
                height = width * 4 / 3;
                break;
            case AliyunSnapVideoParam.RATIO_MODE_9_16:
                height = width * 16 / 9;
                break;
            default:
                height = width;
                break;
        }
        return height;
    }

}
