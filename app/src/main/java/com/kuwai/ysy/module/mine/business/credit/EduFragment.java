package com.kuwai.ysy.module.mine.business.credit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

public class EduFragment extends BaseFragment<EduPresenter> implements EduContract.IHomeView, View.OnClickListener {

    private NavigationLayout navigationLayout;
    private EditText etName, etSchool;
    private SuperTextView stEdu;
    private ImageView mIvEdu;
    private TextView tvEdu;
    private String mName, mSchool;
    private Button btnSend;

    private int selectType = PictureMimeType.ofImage();
    private int maxSelectNum = 1;
    private static final int REQUST_CODE_PICTURE = 1001;

    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;
    private CustomDialog eduDialog = null;
    private String mEdu = "大专";

    public static EduFragment newInstance() {
        Bundle args = new Bundle();
        EduFragment fragment = new EduFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_edu_authentication_fragment;
    }

    @Override
    protected EduPresenter getPresenter() {
        return new EduPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_edu:
                getPhoto();
                break;
            case R.id.my_edu:
                popEduCustom();
                break;
            case R.id.btn_cash:
                sendAuth();
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
        stEdu = mRootView.findViewById(R.id.my_edu);
        etSchool = mRootView.findViewById(R.id.my_school);
        mIvEdu = mRootView.findViewById(R.id.iv_edu);
        tvEdu = mRootView.findViewById(R.id.tv_edu);


        mRootView.findViewById(R.id.rl_edu).setOnClickListener(this);
        mRootView.findViewById(R.id.my_edu).setOnClickListener(this);

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
        mName = etName.getText().toString();
        mEdu = stEdu.getLeftString();
        mSchool = etSchool.getText().toString();

        UploadHelper helper = UploadHelper.getInstance();
        helper.addParameter("uid", SPManager.getStringValue("uid"));
        helper.addParameter("full_name", mName);
        helper.addParameter("education", mEdu);
        helper.addParameter("school", mSchool);

        File file = new File(selectList.get(0).getCompressPath());
        helper.addParameter("file1" + "\";filename=\"" + selectList.get(0).getCompressPath(), file);

        mPresenter.requestHomeData(helper.builder());

    }

    private void getPhoto() {
        PictureSelector.create(EduFragment.this)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    media = selectList.get(0);

                    mIvEdu.setImageURI(Uri.fromFile(new File(media.getCompressPath())));
                    tvEdu.setVisibility(View.GONE);
                    break;

            }

        }
    }

    private void popEduCustom() {
        if (eduDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择学历");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                    }
                }
            });

            List<GoodsCategory> data = new ArrayList<>();
            data.add(new GoodsCategory(1, "大专以下"));
            data.add(new GoodsCategory(2, "大专"));
            data.add(new GoodsCategory(3, "本科"));
            data.add(new GoodsCategory(4, "硕士"));
            data.add(new GoodsCategory(5, "博士"));
            data.add(new GoodsCategory(6, "海外留学"));
            SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
                @Override
                public void onWheeled(int index, GoodsCategory item) {
                    mEdu = item.getName();
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                        stEdu.setLeftString(mEdu);
                        stEdu.setLeftTextColor(Color.BLACK);
                    }
                }
            });
            eduDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        eduDialog.show();
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("学历作用？")
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
