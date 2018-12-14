package com.kuwai.ysy.module.circle.business.publishdy;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.aliyun.AlivcSvideoRecordActivity;
import com.kuwai.ysy.module.circle.business.RightChooseActivity;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.exchange.BGASortableNinePhotoLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.DY_FILM;
import static com.kuwai.ysy.app.C.DY_PIC;
import static com.kuwai.ysy.app.C.DY_TXT;

public class PublishDyActivity extends BaseActivity<PublishPresenter> implements View.OnClickListener, BGASortableNinePhotoLayout.GridAdd, PublishDyContract.IPublishView {

    private TitleBar mTitleBar;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private String[] imgList = new String[]{"http://img.kaiyanapp.com/fa978756b844c4facbc08656a9916415.jpeg?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/gl.jpg",
            "http://img.kaiyanapp.com/d7e21f93f4dcb6e78271d125a1f41a9e.png?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/cq.jpg"};
    private ArrayList<LocalMedia> mData = new ArrayList<>();

    private static final int REQUST_CODE_PICTURE = 1001;
    private static final int REQUST_CODE_VIDEO = 1002;
    private static final int REQUST_CODE_ADDRESS = 1003;
    private static final int REQUST_CODE_RIGHT = 1004;
    private String videoPath;
    private Bitmap mBitmap;
    private String imagePath;
    private TextView mAddressTv;
    private TextView mRightTv, mDetailTv;
    private PoiItem poiItem;
    private TextView mInfoTv;
    private NavigationLayout navigationLayout;

    private int selectType = PictureMimeType.ofAll();
    private int maxSelectNum = 9;

    private int type = DY_TXT;

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
                publishDy();
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
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", SPManager.get().getStringValue("uid"));
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        map.put("night", "0");
        mPresenter.publishDy(map);
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
                photoAndVideo();
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

    @Override
    public void gridAdd() {
        startActivityForResult(new Intent(PublishDyActivity.this, AlivcSvideoRecordActivity.class), REQUST_CODE_VIDEO);
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
                    }

                    mData.add(media);
                    mPhotosSnpl.setData(mData);
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
                        mDetailTv.setText(title);
                    }
                    break;
            }

        }
    }

    @Override
    public void setPublishCallBack(SimpleResponse dyDetailBean) {

    }

    @Override
    public void showError(int errorCode, String msg) {

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
}
