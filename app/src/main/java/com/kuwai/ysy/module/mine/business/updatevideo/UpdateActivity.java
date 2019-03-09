package com.kuwai.ysy.module.mine.business.updatevideo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.mine.adapter.HomePageVideoAdapter;
import com.kuwai.ysy.module.mine.adapter.MyPicAdapter;
import com.kuwai.ysy.module.mine.adapter.MyVideoAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class UpdateActivity extends BaseActivity implements View.OnClickListener, MyVideoAdapter.OnAddItemClickListener, MyPicAdapter.OnAddItemClickListener {

    private NavigationLayout mNavigation;
    private ImageView mImgVideoMore;
    private RecyclerView mRlVideo;
    private ImageView mImgPicMore;
    private RecyclerView mRlPic;

    private MyVideoAdapter myVideoAdapter;
    private MyPicAdapter myPicAdapter;
    private WallBean.DataBean mImagBean;

    private boolean picClose = false;
    private boolean videoClose = false;

    private static final int REQUST_CODE_VIDEO = 1002;
    private static final int REQUST_CODE_PICTURE = 1001;
    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;
    private String videoPath = "";
    private String mVideoId;
    private Bitmap mBitmap;
    private String imagePath;
    private String TAG = "UpdateActivity";

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        EventBusUtil.register(this);
        mNavigation = findViewById(R.id.navigation);
        mImgVideoMore = findViewById(R.id.img_normal_more);
        mRlVideo = findViewById(R.id.rl_normal);
        mImgPicMore = findViewById(R.id.img_suyan_more);
        mRlPic = findViewById(R.id.rl_suyan);
        mImgPicMore.setOnClickListener(this);
        mImgVideoMore.setOnClickListener(this);

        vodsVideoUploadClient = new VODSVideoUploadClientImpl(this.getApplicationContext());
        vodsVideoUploadClient.init();

        mRlVideo.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRlPic.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        myVideoAdapter = new MyVideoAdapter(this);
        myPicAdapter = new MyPicAdapter(this);
        mRlVideo.setAdapter(myVideoAdapter);
        mRlPic.setAdapter(myPicAdapter);

        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getWall();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_update_video;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_normal_more:
                if (videoClose) {
                    mImgVideoMore.setImageResource(R.drawable.ic_list1_down);
                    myVideoAdapter.setData(mImagBean.getVideo());
                    videoClose = false;
                } else {
                    if (mImagBean.getVideo().size() > 3) {
                        mImgVideoMore.setImageResource(R.drawable.ic_list1_up);
                        myVideoAdapter.setData(mImagBean.getVideo().subList(0, 3));
                        videoClose = true;
                    }
                }
                break;
            case R.id.img_suyan_more:
                if (picClose) {
                    mImgPicMore.setImageResource(R.drawable.ic_list1_down);
                    myPicAdapter.setData(mImagBean.getImage());
                    picClose = false;
                } else {
                    if (mImagBean.getImage().size() > 3) {
                        mImgPicMore.setImageResource(R.drawable.ic_list1_up);
                        myPicAdapter.setData(mImagBean.getImage().subList(0, 3));
                        picClose = true;
                    }
                }
                break;
        }
    }

    @Override
    public void onItemVideoAddClick() {
        requestWritePermission(2);
    }

    private void getWall() {
        addSubscription(MineApiFactory.getWall(SPManager.get().getStringValue("uid")).subscribe(new Consumer<WallBean>() {
            @Override
            public void accept(WallBean wallBean) throws Exception {
                mImagBean = wallBean.getData();
                myVideoAdapter.setData(wallBean.getData().getVideo());
                myPicAdapter.setData(wallBean.getData().getImage());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
            }
        }));
    }

    @Override
    public void onItemPicAddClick() {
        requestWritePermission(1);
    }

    private void requestWritePermission(final int type) {
        RxPermissions rxPermissions = new RxPermissions(UpdateActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            photoAndVideo(type);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        media = selectList.get(0);
                        String pictureType = media.getPictureType();
                        DialogUtil.showLoadingDialog(UpdateActivity.this, "上传中", getResources().getColor(R.color.theme));
                        if ("video/mp4".equals(pictureType)) {
                            //视频上传
                            videoPath = media.getPath();
                            mBitmap = FileUtils.voidToFirstBitmap(videoPath);
                            imagePath = FileUtils.bitmapToStringPath(this, mBitmap);
                            getStsToken(SPManager.getStringValue("uid"), SPManager.getStringValue("token"), "");
                        } else {
                            //图片上传
                            publishPic();
                        }
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        //mPhotosSnpl.setData(selectList);
                    }
                    break;
                case REQUST_CODE_VIDEO:
                    LocalMedia media = new LocalMedia();
                    if (data.getStringExtra("path") != null) {
                        videoPath = data.getStringExtra("path");
                        media.setPath(data.getStringExtra("path"));
                        media.setPictureType("video");
                        media.setMimeType(PictureMimeType.ofVideo());
                        mBitmap = FileUtils.voidToFirstBitmap(videoPath);
                        imagePath = FileUtils.bitmapToStringPath(this, mBitmap);
                    } else if (data.getStringExtra("imgpath") != null) {
                        media.setPath(data.getStringExtra("imgpath"));
                    }

                    selectList.add(media);
                    //mPhotosSnpl.setData(selectList);
                    break;
            }

        }
    }

    private int maxSelectNum = 9;

    private void photoAndVideo(int type) {
        PictureSelector.create(UpdateActivity.this)
                .openGallery(type == 1 ? PictureMimeType.ofImage() : PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .previewVideo(true)// 是否可预览视频
                .selectionMode(type == 1 ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                //.enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code

    }

    private void requestCameraPermission(final int type) {
        RxPermissions rxPermissions = new RxPermissions(UpdateActivity.this);
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            PictureSelector.create(UpdateActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .enableCrop(true)// 是否裁剪 true or false
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .forResult(type);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            //Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            // Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RxPermissionsActivity.this, "已拒绝权限"+ permission.name +"并不再询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String accessKeyId, accessKeySecret, securityToken, expriedTime;
    private StsBean stsTokenBean;
    private VodSessionCreateInfo vodSessionCreateInfo;
    private VODSVideoUploadClient vodsVideoUploadClient;
    private int selectType = PictureMimeType.ofAll();

    public void getStsToken(String uid, String token, final String type) {
        addSubscription(CircleApiFactory.getVideoSts(uid, token).subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                stsTokenBean = stsBean;
                if (stsTokenBean.getCode() == 200) {
                    accessKeyId = stsTokenBean.getData().getAccessKeyId();
                    accessKeySecret = stsTokenBean.getData().getAccessKeySecret();
                    securityToken = stsTokenBean.getData().getSecurityToken();
                    expriedTime = stsTokenBean.getData().getExpiration();
                    if (StringUtil.isEmpty(accessKeyId)) {
                        return;
                    } else if (StringUtil.isEmpty(accessKeySecret)) {
                        return;
                    } else if (StringUtil.isEmpty(securityToken)) {
                        return;
                    } else if (StringUtil.isEmpty(expriedTime)) {
                        return;
                    }
                    if (!Utils.isNullString(type)) {
                        vodsVideoUploadClient.refreshSTSToken(accessKeyId, accessKeySecret, securityToken, expriedTime);
                    } else {
                        uploadSetting();
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void uploadSetting() {

        //参数请确保存在，如不存在SDK内部将会直接将错误throw Exception
        // 文件路径保证存在之外因为Android 6.0之后需要动态获取权限，请开发者自行实现获取"文件读写权限".
        VodHttpClientConfig vodHttpClientConfig = new VodHttpClientConfig.Builder()
                .setMaxRetryCount(2)//重试次数
                .setConnectionTimeout(15 * 1000)//连接超时
                .setSocketTimeout(15 * 1000)//socket超时
                .build();
        //构建短视频VideoInfo,常见的描述，标题，详情都可以设置
        SvideoInfo svideoInfo = new SvideoInfo();
        svideoInfo.setTitle("android");
        svideoInfo.setDesc("");
        svideoInfo.setCateId(1);
        //构建点播上传参数(重要)

        vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                .setImagePath(imagePath)//图片地址
                .setVideoPath(videoPath)//视频地址
                .setAccessKeyId(accessKeyId)//临时accessKeyId
                .setAccessKeySecret(accessKeySecret)//临时accessKeySecret
                .setSecurityToken(securityToken)//securityToken
                .setExpriedTime(expriedTime)//STStoken过期时间
//                .setRequestID(requestID)//requestID，开发者可以传将获取STS返回的requestID设置也可以不设.
                .setIsTranscode(true)//是否转码.如开启转码请AppSever务必监听服务端转码成功的通知
                .setSvideoInfo(svideoInfo)//短视频视频信息
                .setVodHttpClientConfig(vodHttpClientConfig)//网络参数
                .build();

        uploadListener();
    }

    private void uploadListener() {
        vodsVideoUploadClient.uploadWithVideoAndImg(vodSessionCreateInfo, new VODSVideoUploadCallback() {
            @Override
            public void onUploadSucceed(String videoId, String imageUrl) {
                //上传成功返回视频ID和图片URL.
                mVideoId = videoId;
                publishVideo();
            }

            @Override
            public void onUploadFailed(String code, String message) {
                //上传失败返回错误码和message.错误码有详细的错误信息请开发者仔细阅读
                Log.d(TAG, "onUploadFailed" + "code" + code + "message" + message);
            }

            @Override
            public void onUploadProgress(long uploadedSize, long totalSize) {
                //上传的进度回调,非UI线程
                Log.d(TAG, "onUploadProgress" + uploadedSize * 100 / totalSize);
                //progress = uploadedSize * 100 / totalSize;
                //handler.sendEmptyMessage(0);
            }

            @Override
            public void onSTSTokenExpried() {
                Log.d(TAG, "onSTSTokenExpried");
                //STS token过期之后刷新STStoken，如正在上传将会断点续传
                getStsToken(SPManager.getStringValue("uid"), SPManager.getStringValue("token"), "refresh");
            }

            @Override
            public void onUploadRetry(String code, String message) {
                //上传重试的提醒
                Log.d(TAG, "onUploadRetry" + "code" + code + "message" + message);
            }

            @Override
            public void onUploadRetryResume() {
                //上传重试成功的回调.告知用户重试成功
                Log.d(TAG, "onUploadRetryResume");
            }
        });
    }

    private void publishPic() {
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getCompressPath());
            helper.addParameter("file" + i + "\";filename=\"" + selectList.get(i).getCompressPath(), file);
        }
        addSubscription(MineApiFactory.addPhotoWall(helper.builder()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //mView.setPublishCallBack(dyDetailBean);
                if (dyDetailBean.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_UPDATE_VIDEO));
                    getWall();
                }
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(dyDetailBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    public void publishVideo() {
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("video_id", mVideoId);
        File file = new File(imagePath);
        helper.addParameter("file" + 0 + "\";filename=\"" + imagePath, file);
        addSubscription(MineApiFactory.addVideoWall(helper.builder()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //mView.setPublishCallBack(dyDetailBean);
                if (dyDetailBean.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_UPDATE_VIDEO));
                    getWall();
                }
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(dyDetailBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_DELETE_VIDEO) {
            getWall();
        }
    }
}
