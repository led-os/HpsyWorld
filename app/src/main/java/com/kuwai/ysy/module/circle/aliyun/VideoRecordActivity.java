package com.kuwai.ysy.module.circle.aliyun;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.aliyun.common.utils.MySystemParams;
import com.aliyun.common.utils.StorageUtils;
import com.aliyun.qupai.import_core.AliyunIImport;
import com.aliyun.qupai.import_core.AliyunImportCreator;
import com.aliyun.svideo.sdk.external.struct.common.AliyunDisplayMode;
import com.aliyun.svideo.sdk.external.struct.common.AliyunVideoClip;
import com.aliyun.svideo.sdk.external.struct.common.AliyunVideoParam;
import com.aliyun.svideo.sdk.external.struct.common.VideoDisplayMode;
import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.encoder.VideoCodecs;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.mine.VideoPreActivity;
import com.kuwai.ysy.module.mine.business.credit.MyCreditFragment;
import com.kuwai.ysy.utils.PermissionUtils;

import java.io.File;
import java.lang.ref.WeakReference;

public class VideoRecordActivity extends AppCompatActivity {
    public static final String NEED_GALLERY = "need_gallery";

    private EasyRecordView videoRecordView;

    /** 上个页面传参 */
    private int mResolutionMode;
    private int mMinDuration;
    private int mMaxDuration;
    private int mGop;
    private int mBitrate;
    private VideoQuality mVideoQuality = VideoQuality.HD;
    private VideoCodecs mVideoCodec = VideoCodecs.H264_HARDWARE;
    private int mRatioMode = AliyunSnapVideoParam.RATIO_MODE_9_16;
    private int mSortMode = AliyunSnapVideoParam.SORT_MODE_MERGE;
    private boolean isNeedClip;
    private boolean isNeedGallery;
    private AliyunVideoParam mVideoParam;
    private int mFrame = 25;
    private VideoDisplayMode mCropMode = VideoDisplayMode.SCALE;
    private int mMinCropDuration = 2000;
    private int mMaxVideoDuration = 10000;
    private int mMinVideoDuration = 2000;
    private int mGalleryVisibility;
    private static final int REQUEST_CODE_PLAY = 2002;
    /**
     * 判断是否电话状态
     * true: 响铃, 通话
     * false: 挂断
     */
    private boolean isCalling = false;
    /**
     * 权限申请
     */
    String[] permission = {
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Toast phoningToast;
    private PhoneStateManger phoneStateManger;
    /**
     *  判断是编辑模块进入还是通过社区模块的编辑功能进入
     *
     */
    private static final String INTENT_PARAM_KEY_ENTRANCE = "entrance";

    /**
     *  判断是编辑模块进入还是通过社区模块的编辑功能进入
     *  svideo: 短视频
     *  community: 社区
     */
    private String entrance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //乐视x820手机在AndroidManifest中设置横竖屏无效，并且只在该activity无效其他activity有效
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MySystemParams.getInstance().init(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!NotchScreenUtil.checkNotchScreen(this)){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_svideo_record);

        getData();

        boolean checkResult = PermissionUtils.checkPermissionsGroup(this, permission);
        if (!checkResult) {
            PermissionUtils.requestPermissions(this, permission, PERMISSION_REQUEST_CODE);
        }
        videoRecordView = findViewById(R.id.testRecordView);
        videoRecordView.setActivity(this);
        videoRecordView.setGop(mGop);
        videoRecordView.setBitrate(mBitrate);
        videoRecordView.setMaxRecordTime(mMaxDuration);
        videoRecordView.setMinRecordTime(mMinDuration);
        //videoRecordView.setRatioMode(mRatioMode);
        videoRecordView.setVideoQuality(mVideoQuality);
        videoRecordView.setResolutionMode(mResolutionMode);

        new Handler().postDelayed(new Runnable(){
            public void run() {
                videoRecordView.startRecord();
            }
        }, 5000);   //5秒
        //videoRecordView.startRecord();
    }

    private String[] mEffDirs;
    private AsyncTask<Void, Void, Void> copyAssetsTask;
    private AsyncTask<Void, Void, Void> initAssetPath;

    /**
     * 获取上个页面的传参
     */
    private void getData() {
        mResolutionMode = getIntent().getIntExtra(AliyunSnapVideoParam.VIDEO_RESOLUTION, AliyunSnapVideoParam.RESOLUTION_540P);
        mMinDuration = getIntent().getIntExtra(AliyunSnapVideoParam.MIN_DURATION, 2000);
        mMaxDuration = getIntent().getIntExtra(AliyunSnapVideoParam.MAX_DURATION, 5000);
        //mRatioMode = getIntent().getIntExtra(AliyunSnapVideoParam.VIDEO_RATIO, AliyunSnapVideoParam.RATIO_MODE_3_4);
        mGop = getIntent().getIntExtra(AliyunSnapVideoParam.VIDEO_GOP, 5);
        mBitrate = getIntent().getIntExtra(AliyunSnapVideoParam.VIDEO_BITRATE, 0);
        mVideoQuality = (VideoQuality) getIntent().getSerializableExtra(AliyunSnapVideoParam.VIDEO_QUALITY);
        entrance = getIntent().getStringExtra(INTENT_PARAM_KEY_ENTRANCE);
        if(mVideoQuality == null){
            mVideoQuality = VideoQuality.HD;
        }
        mVideoCodec = (VideoCodecs) getIntent().getSerializableExtra(AliyunSnapVideoParam.VIDEO_CODEC);
        if(mVideoCodec == null) {
            mVideoCodec = VideoCodecs.H264_HARDWARE;
        }
        isNeedClip = getIntent().getBooleanExtra(AliyunSnapVideoParam.NEED_CLIP,true);
        isNeedGallery = getIntent().getBooleanExtra(NEED_GALLERY,true) && mGalleryVisibility == 0;
        mVideoParam = new AliyunVideoParam.Builder()
            .gop(mGop)
            .bitrate(mBitrate)
            .crf(25)
            .frameRate(25)
            .outputWidth(getVideoWidth())
            .outputHeight(getVideoHeight())
            .videoQuality(mVideoQuality)
            .videoCodec(mVideoCodec)
            .build();

        /**
         * 裁剪参数
         */
        mFrame = getIntent().getIntExtra(AliyunSnapVideoParam.VIDEO_FRAMERATE,25);
        mCropMode = (VideoDisplayMode) getIntent().getSerializableExtra(AliyunSnapVideoParam.CROP_MODE);
        if(mCropMode == null){
            mCropMode = VideoDisplayMode.SCALE;
        }
        mMinCropDuration = getIntent().getIntExtra(AliyunSnapVideoParam.MIN_CROP_DURATION,2000);
        mMinVideoDuration = getIntent().getIntExtra(AliyunSnapVideoParam.MIN_VIDEO_DURATION,2000);
        mMaxVideoDuration = getIntent().getIntExtra(AliyunSnapVideoParam.MAX_VIDEO_DURATION,10000);
        mSortMode = getIntent().getIntExtra(AliyunSnapVideoParam.SORT_MODE, AliyunSnapVideoParam.SORT_MODE_MERGE);

    }
    /**
     * 获取拍摄视频宽度
     * @return
     */
    private int getVideoWidth(){
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
    private int getVideoHeight(){
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

    @Override
    protected void onStart() {
        super.onStart();
        initPhoneStateManger();
    }

    private void initPhoneStateManger() {
        if (phoneStateManger == null) {
            phoneStateManger = new PhoneStateManger(this);
            phoneStateManger.registPhoneStateListener();
            phoneStateManger.setOnPhoneStateChangeListener(new PhoneStateManger.OnPhoneStateChangeListener() {

                @Override
                public void stateIdel() {
                    // 挂断
                    videoRecordView.setRecordMute(false);
                    isCalling = false;
                }

                @Override
                public void stateOff() {
                    // 接听
                    videoRecordView.setRecordMute(true);
                    isCalling = true;
                }

                @Override
                public void stateRinging() {
                    // 响铃
                    videoRecordView.setRecordMute(true);
                    isCalling = true;
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoRecordView.startPreview();
        videoRecordView.setBackClickListener(new EasyRecordView.OnBackClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        videoRecordView.setPickPhotoListener(new EasyRecordView.OnPickPhotoListener() {
            @Override
            public void onComplete(String path) {
                Intent intent = new Intent(VideoRecordActivity.this, PublishDyActivity.class);
                intent.putExtra("imgpath", path);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        videoRecordView.setCompleteListener(new EasyRecordView.OnFinishListener() {
            @Override
            public void onComplete(String path,int duration) {
                /*AliyunIImport mImport= AliyunImportCreator.getImportInstance(VideoRecordActivity.this);
                mImport.setVideoParam(mVideoParam);
                mImport.addMediaClip(new AliyunVideoClip.Builder()
                    .source(path)
                    .startTime(0)
                    .endTime(duration)
                    .displayMode(AliyunDisplayMode.DEFAULT)
                    .build());
                String projectJsonPath = mImport.generateProjectConfigure();*/
                Intent intent = new Intent(VideoRecordActivity.this, VideoPreActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        videoRecordView.stopPreview();
        super.onPause();
        if (phoningToast != null) {
            phoningToast.cancel();
            phoningToast = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (phoneStateManger != null) {
            phoneStateManger.setOnPhoneStateChangeListener(null);
            phoneStateManger.unRegistPhoneStateListener();
            phoneStateManger = null;
        }
    }

    @Override
    protected void onDestroy() {

        //闪退处
        videoRecordView.destroyRecorder();
        super.onDestroy();
        if (copyAssetsTask != null) {
            copyAssetsTask.cancel(true);
            copyAssetsTask = null;
        }

        if (initAssetPath != null) {
            initAssetPath.cancel(true);
            initAssetPath = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAY) {
            if (resultCode == Activity.RESULT_OK) {
                videoRecordView.deleteAllPart();
                finish();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCalling) {
            //phoningToast = ToastUtils.showShort(getResources().getString(R.string.alivc_phone_state_calling));
        }
    }

    public static final int PERMISSION_REQUEST_CODE = 1000;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了
                //Toast.makeText(this, "get All Permisison", Toast.LENGTH_SHORT).show();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                showPermissionDialog();
            }
        }
    }
    //系统授权设置的弹框
    AlertDialog openAppDetDialog = null;
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.app_name) + "需要访问 \"相册\"、\"摄像头\" 和 \"外部存储器\",否则会影响绝大部分功能使用, 请到 \"应用信息 -> 权限\" 中设置！");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("暂不设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        if (null == openAppDetDialog) {
            openAppDetDialog = builder.create();
        }
        if (null != openAppDetDialog && !openAppDetDialog.isShowing()) {
            openAppDetDialog.show();
        }
    }
}
