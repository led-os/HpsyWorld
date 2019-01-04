package com.kuwai.ysy.module.find;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.google.gson.Gson;
import com.kuwai.ysy.common.BaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class UploadActivity extends BaseActivity{

    private static final String TAG = "FilmUpload";

    private VODSVideoUploadClient vodsVideoUploadClient;
    private VodSessionCreateInfo vodSessionCreateInfo;
    private String accessKeyId, accessKeySecret, securityToken, expriedTime;

    private long progress;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //更新进度
                    //tvProgress.setText("进度：" + String.valueOf(progress));
                    //progressDialog.setTextProgress("上传进度" + String.valueOf(progress) + "%");
                    break;
                case 1:

            }
        }
    };

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        initData();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return 0;
    }

    private void initData() {
        //1.初始化短视频上传对象
        vodsVideoUploadClient = new VODSVideoUploadClientImpl(this.getApplicationContext());
        vodsVideoUploadClient.init();
        getSTStoken();

        /*ivFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FilmUpload.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FilmUpload.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    FilmUploadPermissionsDispatcher.needStoreWithPermissionCheck(FilmUpload.this);
                } else {
                    videoSelect();
                }
            }
        });*/

    }

    private void getSTStoken() {
        uploadSetting();
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
        svideoInfo.setTitle("标题");
        svideoInfo.setDesc("");
        svideoInfo.setCateId(1);
        //构建点播上传参数(重要)

        vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                //.setImagePath(imagePath)//图片地址
                //.setVideoPath(videoPath)//视频地址
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
                Log.d(TAG, "onUploadSucceed" + "videoId:" + videoId + "imageUrl" + imageUrl);
                //sendVideoId(videoId);
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

                progress = uploadedSize * 100 / totalSize;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onSTSTokenExpried() {
                Log.d(TAG, "onSTSTokenExpried");
                //STS token过期之后刷新STStoken，如正在上传将会断点续传
                //getRefreshSTStoken();
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

   /* private void getRefreshSTStoken() {
        OkHttpUtils
                .post()
                .url(Constants.BASE_URL + Constants.STSTOKEN)
                .addParams("uid", PreferenceUtils.getPrefString(FilmUpload.this, "uid", ""))
                .addParams("token", PreferenceUtils.getPrefString(FilmUpload.this, "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.i(TAG, "onResponse——rsts: " + response);
                        stsTokenBean = new Gson().fromJson(response, STSTokenBean.class);
                        if (stsTokenBean.getCode() == 200) {
                            accessKeyId = stsTokenBean.getData().getAccessKeyId();
                            accessKeySecret = stsTokenBean.getData().getAccessKeySecret();
                            securityToken = stsTokenBean.getData().getSecurityToken();
                            expriedTime = stsTokenBean.getData().getExpiration();
                            if (StringUtil.isEmpty(accessKeyId)) {
                                Toast.makeText(FilmUpload.this, "The specified parameter accessKeyId cannot be null", Toast.LENGTH_LONG).show();
                                return;
                            } else if (StringUtil.isEmpty(accessKeySecret)) {
                                Toast.makeText(FilmUpload.this, "The specified parameter \"accessKeySecret\" cannot be null", Toast.LENGTH_LONG).show();
                                return;
                            } else if (StringUtil.isEmpty(securityToken)) {
                                Toast.makeText(FilmUpload.this, "The specified parameter \"securityToken\" cannot be null", Toast.LENGTH_LONG).show();
                                return;
                            } else if (StringUtil.isEmpty(expriedTime)) {
                                Toast.makeText(FilmUpload.this, "The specified parameter \"expriedTime\" cannot be null", Toast.LENGTH_LONG).show();
                                return;
                            }
                            vodsVideoUploadClient.refreshSTSToken(accessKeyId, accessKeySecret, securityToken, expriedTime);
                        }
                    }
                });
    }*/
}
