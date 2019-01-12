package com.kuwai.ysy.module.home.business.loginmoudle;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.business.homepage.MineHomepageFragment;
import com.kuwai.ysy.utils.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class InfoHeadFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvPass;
    private TextView mTitle;
    private CircleImageView mImgHead;
    private TextView mTvHead;
    private EditText mEtName;
    private View mLine1;
    private SuperButton mBtnNext;
    private CustomDialog customDialog;

    private int PHOTO_RESULT = 10000;

    public static InfoHeadFragment newInstance() {
        Bundle args = new Bundle();
        InfoHeadFragment fragment = new InfoHeadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_head;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (!Utils.isNullString(mEtName.getText().toString())) {
                    SPManager.get().putString(C.REGIST_NAME, mEtName.getText().toString());
                }
                if (media != null) {
                    SPManager.get().putString(C.REGIST_AVATAR, media.getCompressPath());
                }
                start(InfoSexFragment.newInstance());
                break;
            case R.id.tv_pass:
                start(InfoSexFragment.newInstance());
                break;
            case R.id.img_head:
                showSelectdialog();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvPass = mRootView.findViewById(R.id.tv_pass);
        mTitle = mRootView.findViewById(R.id.title);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvHead = mRootView.findViewById(R.id.tv_head);
        mEtName = mRootView.findViewById(R.id.et_name);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mTvPass.setOnClickListener(this);
        mImgHead.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    public void showSelectdialog() {

        if (customDialog == null) {

            View parent = View.inflate(getActivity(), R.layout.dialog_add_picture, null);
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

            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(parent)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        customDialog.show();
    }

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            PictureSelector.create(InfoHeadFragment.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .enableCrop(true)// 是否裁剪 true or false
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .forResult(PHOTO_RESULT);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RxPermissionsActivity.this, "已拒绝权限"+ permission.name +"并不再询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void requestWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector.create(InfoHeadFragment.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .isCamera(false)// 是否显示拍照按钮 true or false
                                    .compress(true)
                                    .maxSelectNum(1)// 最大图片选择数量 int
                                    .minSelectNum(1)// 最小选择数量 int
                                    .enableCrop(true)// 是否裁剪 true or false
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .forResult(PHOTO_RESULT);
                        } else {
                            Toast.makeText(getActivity(), "该功能需要获取相册权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;
    private Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    media = selectList.get(0);
                    bitmap = BitmapFactory.decodeFile(media.getCompressPath());
                }
            }
            if (requestCode == PHOTO_RESULT) {
                if (media != null) {
                    //File file = new File(media.getCompressPath());
                    GlideUtil.load(getActivity(), media.getCompressPath(), mImgHead);
                }
            }
        }
    }
}
