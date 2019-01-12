package com.kuwai.ysy.module.circle.business.publishdy;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.amap.api.services.core.PoiItem;
import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.aliyun.AlivcSvideoRecordActivity;
import com.kuwai.ysy.module.circle.business.RightChooseActivity;
import com.kuwai.ysy.module.mine.business.homepage.MineHomepageFragment;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.exchange.BGASortableNinePhotoLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.DY_FILM;
import static com.kuwai.ysy.app.C.DY_PIC;
import static com.kuwai.ysy.app.C.DY_TXT;

public class PublishDyActivity extends BaseActivity<PublishPresenter> implements View.OnClickListener, BGASortableNinePhotoLayout.GridAdd, PublishDyContract.IPublishView {

    private String accessKeyId, accessKeySecret, securityToken, expriedTime;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private String[] imgList = new String[]{"http://img.kaiyanapp.com/fa978756b844c4facbc08656a9916415.jpeg?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/gl.jpg",
            "http://img.kaiyanapp.com/d7e21f93f4dcb6e78271d125a1f41a9e.png?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/cq.jpg"};

    private static final int REQUST_CODE_PICTURE = 1001;
    private static final int REQUST_CODE_VIDEO = 1002;
    private static final int REQUST_CODE_ADDRESS = 1003;
    private static final int REQUST_CODE_RIGHT = 1004;
    private String videoPath = "";
    private String mVideoId;
    private Bitmap mBitmap;
    private String imagePath;
    private TextView mAddressTv;
    private TextView mRightTv, mDetailTv;
    private PoiItem poiItem;
    private TextView mInfoTv;
    private NavigationLayout navigationLayout;
    private EditText et_content;
    private int themeId = R.style.picture_default_style;

    private StsBean stsTokenBean;
    private VodSessionCreateInfo vodSessionCreateInfo;
    private VODSVideoUploadClient vodsVideoUploadClient;

    private int selectType = PictureMimeType.ofAll();
    private int maxSelectNum = 9;
    private int type = DY_TXT;
    private int publicId = 1;
    private String TAG = "gggg";
    private CustomDialog customDialog;

    @Override
    protected PublishPresenter getPresenter() {
        return new PublishPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_publish_dy;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

        type = getIntent().getIntExtra("type", DY_TXT);

        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        et_content = findViewById(R.id.et_content);

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Utils.isNullString(s.toString())) {
                    navigationLayout.setRightColor(getResources().getColor(R.color.theme));
                }else{
                    navigationLayout.setRightColor(getResources().getColor(R.color.gray_7b));
                }
            }
        });
        navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNullString(videoPath)) {
                    DialogUtil.showLoadingDialog(PublishDyActivity.this, "", getResources().getColor(R.color.theme));
                    mPresenter.getStsToken(SPManager.get().getStringValue("uid"), SPManager.get().getStringValue("token"), "");
                } else {
                    publishDy();
                }
            }
        });
        mAddressTv = findViewById(R.id.tv_address);
        mRightTv = findViewById(R.id.tv_right);
        mInfoTv = findViewById(R.id.tv_info);
        mInfoTv.setOnClickListener(this);
        mRightTv.setOnClickListener(this);
        mDetailTv = findViewById(R.id.tv_detail);
        mAddressTv.setOnClickListener(this);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setSortable(true);
        mPhotosSnpl.setGridAdd(this);

        vodsVideoUploadClient = new VODSVideoUploadClientImpl(this.getApplicationContext());
        vodsVideoUploadClient.init();

        switch (type) {
            case DY_TXT:
                break;
            case DY_PIC:
                photoAndVideo();
                break;
            case DY_FILM:
                startActivityForResult(new Intent(PublishDyActivity.this, AlivcSvideoRecordActivity.class), REQUST_CODE_VIDEO);
                break;
            default:
                break;
        }
    }

    private void publishDy() {
        String type = "0";
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("text", et_content.getText().toString());
        helper.addParameter("visibility", String.valueOf(publicId));
        if (poiItem != null) {
            helper.addParameter("longitude", String.valueOf(poiItem.getLatLonPoint().getLongitude()));
            helper.addParameter("latitude", String.valueOf(poiItem.getLatLonPoint().getLatitude()));
            helper.addParameter("city", poiItem.getCityName());
            helper.addParameter("address", mAddressTv.getText().toString());
        }

        if (!Utils.isNullString(videoPath)) {
            type = "2";
            helper.addParameter("video_id", mVideoId);
            File file = new File(imagePath);
            helper.addParameter("file" + 0 + "\";filename=\"" + imagePath, file);
        } else if (selectList.size() > 0) {
            DialogUtil.showLoadingDialog(PublishDyActivity.this, "", getResources().getColor(R.color.theme));
            type = "1";
            for (int i = 0; i < selectList.size(); i++) {
                File file = new File(selectList.get(i).getCompressPath());
                helper.addParameter("file" + i + "\";filename=\"" + selectList.get(i).getCompressPath(), file);
            }
        } else {
            DialogUtil.showLoadingDialog(PublishDyActivity.this, "", getResources().getColor(R.color.theme));
            type = "0";
        }
        helper.addParameter("type", type);
        //map.put("video_id", "0");
        mPresenter.publishDy(helper.builder());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address:
                requestLocationPermission();
                break;
            case R.id.tv_right:
                startActivityForResult(new Intent(PublishDyActivity.this, RightChooseActivity.class), REQUST_CODE_RIGHT);
                break;
            case R.id.tv_info:
                //photoAndVideo();
                break;
        }
    }

    private void photoAndVideo() {
        PictureSelector.create(PublishDyActivity.this)
                .openGallery(selectType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code

    }

    private void requestLocationPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startActivityForResult(new Intent(PublishDyActivity.this, AddressChooseActivity.class), REQUST_CODE_ADDRESS);
                        } else {
                            ToastUtils.showShort("该功能需要获取定位权限");
                        }
                    }
                });
    }

    /**
     * 选择图片对话框
     */
    public void showSelectdialog() {

        if (customDialog == null) {

            View parent = View.inflate(this, R.layout.dialog_add_picture, null);
            TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);
            TextView tv_gallery = (TextView) parent.findViewById(R.id.tv_gallery);
            TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);

            final View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tv_cancel:
                            //selectDialog.dismiss();
                            break;
                        case R.id.tv_camera:
                            requestCameraPermission();
                            break;
                        case R.id.tv_gallery:
                            requestWritePermission();
                            break;
                    }
                    customDialog.dismiss();
                }
            };

            tv_camera.setOnClickListener(clickListener);
            tv_cancel.setOnClickListener(clickListener);
            tv_gallery.setOnClickListener(clickListener);

            customDialog = new CustomDialog.Builder(this)
                    .setView(parent)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        customDialog.show();
    }

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            startActivityForResult(new Intent(PublishDyActivity.this, AlivcSvideoRecordActivity.class), REQUST_CODE_VIDEO);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            Toast.makeText(PublishDyActivity.this, "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(PublishDyActivity.this, "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RxPermissionsActivity.this, "已拒绝权限"+ permission.name +"并不再询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void requestWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            photoAndVideo();
                        } else {
                            Toast.makeText(PublishDyActivity.this, "该功能需要获取相册权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void gridAdd() {
        showSelectdialog();
        //startActivityForResult(new Intent(PublishDyActivity.this, AlivcSvideoRecordActivity.class), REQUST_CODE_VIDEO);
    }

    @Override
    public void gridClick(int position, View v) {
        if (selectList.size() > 0) {
            media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);

            switch (mediaType) {
                case 1:
                    // 预览图片 可自定长按保存路径
                    //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                    PictureSelector.create(PublishDyActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(PublishDyActivity.this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(PublishDyActivity.this).externalPictureAudio(media.getPath());
                    break;
            }
        }
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;

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
                        if ("video/mp4".equals(pictureType)) {
                            videoPath = media.getPath();
                            mBitmap = FileUtils.voidToFirstBitmap(videoPath);
                            imagePath = FileUtils.bitmapToStringPath(this, mBitmap);
                        }
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        mPhotosSnpl.setData(selectList);
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
                        media.setCompressPath(data.getStringExtra("imgpath"));
                    }

                    selectList.add(media);
                    mPhotosSnpl.setData(selectList);
                    break;
                case REQUST_CODE_ADDRESS:
                    if (data != null) {
                        poiItem = (PoiItem) data.getParcelableExtra("data");
                        String name = poiItem.getTitle();
                        String address = poiItem.getSnippet();
                        mAddressTv.setText(name + address);
                    }
                    break;
                case REQUST_CODE_RIGHT:
                    if (data != null) {
                        String title = data.getStringExtra("data");
                        publicId = data.getIntExtra("id", 1);
                        mDetailTv.setText(title);
                    }
                    break;
            }

        }
    }

    @Override
    public void setPublishCallBack(SimpleResponse dyDetailBean) {
        DialogUtil.dismissDialog(true);
        if (dyDetailBean.code == 200) {
            ToastUtils.showShort("发布成功");
            finish();
        } else {
            ToastUtils.showShort("发布失败");
        }
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
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

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

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
                publishDy();
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
                mPresenter.getStsToken(SPManager.getStringValue("uid"), SPManager.getStringValue("token"), "refresh");

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
}
