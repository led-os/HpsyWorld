package com.kuwai.ysy.module.mine.business.credit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class AuthFragment extends BaseFragment<AuthPresenter> implements AuthContract.IHomeView, View.OnClickListener {

    private NavigationLayout navigationLayout;
    private EditText etName, etNumber;
    private ImageView mID, mHandID;
    private TextView tvID, tvHandID;
    private String name, number;
    private Button btnSend;

    private int selectType = PictureMimeType.ofImage();
    private int maxSelectNum = 1;
    private static final int REQUST_CODE_PICTURE = 1001;
    private static final int REQUST_CODE_PICFACE = 1002;

    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> selectList1 = new ArrayList<>();
    private LocalMedia media;
    private String videoPath;
    private Bitmap mBitmap;
    private String imagePath;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_real_name_authentication_fragment;
    }

    @Override
    protected AuthPresenter getPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cash:

                sendAuth();

                break;

            case R.id.rl_ID:
                getPhoto();
                break;

            case R.id.rl_hand_ID:
                getPhotoFace();
                break;

        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NormalAlertDialog dialog = initCleanDialog();
                dialog.show();
            }
        });

        etName = mRootView.findViewById(R.id.my_name);
        etNumber = mRootView.findViewById(R.id.my_number);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mID = mRootView.findViewById(R.id.iv_ID);
        mHandID = mRootView.findViewById(R.id.iv_hand_ID);
        tvID = mRootView.findViewById(R.id.tv_ID);
        tvHandID = mRootView.findViewById(R.id.tv_hand_ID);


        mRootView.findViewById(R.id.rl_ID).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_hand_ID).setOnClickListener(this);

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
        name = etName.getText().toString();
        number = etNumber.getText().toString();

        UploadHelper helper = UploadHelper.getInstance();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("full_name", name);
        helper.addParameter("id_number", number);

        File file = new File(selectList.get(0).getCompressPath());
        helper.addParameter("file1" + "\";filename=\"" + selectList.get(0).getCompressPath(), file);

        File file1 = new File(selectList1.get(0).getCompressPath());
        helper.addParameter("file2" + "\";filename=\"" + selectList1.get(0).getCompressPath(), file1);


        mPresenter.requestHomeData(helper.builder());

    }

    private void getPhoto() {
        PictureSelector.create(AuthFragment.this)
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
        PictureSelector.create(AuthFragment.this)
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

                    mID.setImageURI(Uri.fromFile(new File(media.getCompressPath())));
                    tvID.setVisibility(View.GONE);
                    break;
                case REQUST_CODE_PICFACE:
                    // 图片选择结果回调
                    selectList1 = PictureSelector.obtainMultipleResult(data);
                    media = selectList1.get(0);

                    mHandID.setImageURI(Uri.fromFile(new File(media.getCompressPath())));
                    tvHandID.setVisibility(View.GONE);
                    break;
            }

        }
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("认证作用？")
                .setContentText(getResources().getString(R.string.auth_notice))
                .setSingleButtonText("好的，知道了")
                .setSingleMode(true)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build();
    }
}
