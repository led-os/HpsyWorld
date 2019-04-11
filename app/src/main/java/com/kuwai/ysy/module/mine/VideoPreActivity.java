package com.kuwai.ysy.module.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.HashMap;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class VideoPreActivity extends BaseActivity{

    private String accessKeyId, accessKeySecret, securityToken, expriedTime;
    private StsBean stsTokenBean;
    private VodSessionCreateInfo vodSessionCreateInfo;
    private VODSVideoUploadClient vodsVideoUploadClient;

    private NavigationLayout mNavigation;
    private ImageView mImgPre;
    private ImageView mImgPlay;
    private TextView mTvUpload;
    private TextView mTvReVideo;

    private Bitmap mBitmap;
    private String imagePath;
    private String mVideoId;
    private String videoPath;
    LocalMedia media = null;
    private String TAG = "video_pre";

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        media = new LocalMedia();
        if (getIntent().getStringExtra("path") != null) {
            videoPath = getIntent().getStringExtra("path");
            media.setPath(getIntent().getStringExtra("path"));
            media.setPictureType("video");
            media.setMimeType(PictureMimeType.ofVideo());
            mBitmap = FileUtils.voidToFirstBitmap(videoPath);
            imagePath = FileUtils.bitmapToStringPath(this, mBitmap);
            mImgPre.setImageBitmap(mBitmap);
        }

        mImgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(VideoPreActivity.this).externalPictureVideo(media.getPath());
            }
        });
        mTvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStsToken(SPManager.get().getStringValue("uid"), SPManager.get().getStringValue("token"), "");
            }
        });
    }

    @Override
    protected void initView() {
        mNavigation = findViewById(R.id.navigation);
        mImgPre = findViewById(R.id.img_pre);
        mImgPlay = findViewById(R.id.img_play);
        mTvUpload = findViewById(R.id.tv_upload);
        mTvReVideo = findViewById(R.id.tv_re_video);

        vodsVideoUploadClient = new VODSVideoUploadClientImpl(this.getApplicationContext());
        vodsVideoUploadClient.init();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_pre_video;
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
                UploadHelper helper = UploadHelper.getInstance();
                helper.clear();
                helper.addParameter("uid", SPManager.get().getStringValue("uid"));

                if (!Utils.isNullString(videoPath)) {
                    helper.addParameter("video_id", mVideoId);
                    File file = new File(imagePath);
                    helper.addParameter("file" + 0 + "\";filename=\"" + imagePath, file);
                } else{
                    ToastUtils.showShort("请上传视频");
                }
                publishDy(helper.builder());
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

    public void getStsToken(String uid, String token, final String type) {
        addSubscription(CircleApiFactory.getVideoSts(uid, token).subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                getTokenResult(stsBean, type);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    public void getTokenResult(StsBean stsBean, String type) {
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

    public void publishDy(HashMap<String, RequestBody> map) {
        //DialogUtil.showLoadingDialog(VideoPreActivity.this,"",getResources().getColor(R.color.theme));
        addSubscription(MineApiFactory.photoAuth(map).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //DialogUtil.dismissDialog(true);
                if (dyDetailBean.code == 200) {
                    ToastUtils.showShort("上传成功");
                    //finish();
                } else {
                    ToastUtils.showShort("上传失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
