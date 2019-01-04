package com.kuwai.ysy.module.mine.business.credit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CarFragment extends BaseFragment<CarPresenter> implements CarContract.IHomeView, View.OnClickListener {

    private ImageView mIvCar,mIvBackCar;
    private TextView tvCar,tvBackCar;
    private Button btnSend;

    private int selectType = PictureMimeType.ofImage();
    private int maxSelectNum = 1;
    private static final int REQUST_CODE_PICTURE = 1001;
    private static final int REQUST_CODE_PICFACE = 1002;

    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> selectList1 = new ArrayList<>();
    private LocalMedia media;

    public static CarFragment newInstance() {
        Bundle args = new Bundle();
        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_car_authentication_fragment;
    }

    @Override
    protected CarPresenter getPresenter() {
        return new CarPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_car:
                getPhoto();
                break;
            case R.id.rl_back_car:
                getPhotoFace();
                break;
            case R.id.btn_cash:
                sendAuth();
                break;

        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mIvCar = mRootView.findViewById(R.id.iv_car);
        tvCar= mRootView.findViewById(R.id.tv_car);
        mIvBackCar = mRootView.findViewById(R.id.iv_back_car);
        tvBackCar= mRootView.findViewById(R.id.tv_back_car);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mRootView.findViewById(R.id.rl_car).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_back_car).setOnClickListener(this);

        btnSend = mRootView.findViewById(R.id.btn_cash);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @Override
    public void setHomeData(SimpleResponse response) {
        ToastUtils.showShort(response.msg);
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

    private void sendAuth() {

        UploadHelper helper = UploadHelper.getInstance();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));

        File file = new File(selectList.get(0).getCompressPath());
        helper.addParameter("file1" + "\";filename=\"" + selectList.get(0).getCompressPath(), file);

        File file1 = new File(selectList1.get(0).getCompressPath());
        helper.addParameter("file2" + "\";filename=\"" + selectList1.get(0).getCompressPath(), file1);


        mPresenter.requestHomeData(helper.builder());

    }

    private void getPhoto() {
        PictureSelector.create(CarFragment.this)
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
//                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code

    }

    private void getPhotoFace() {
        PictureSelector.create(CarFragment.this)
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
//                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICFACE);//结果回调onActivityResult code

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    media = selectList.get(0);

                    mIvCar.setImageURI(Uri.fromFile(new File(media.getCompressPath())));
                    tvCar.setVisibility(View.GONE);
                    break;
                case REQUST_CODE_PICFACE:
                    // 图片选择结果回调
                    selectList1 = PictureSelector.obtainMultipleResult(data);
                    media = selectList1.get(0);

                    mIvBackCar.setImageURI(Uri.fromFile(new File(media.getCompressPath())));
                    tvBackCar.setVisibility(View.GONE);
                    break;

            }

        }
    }
}
