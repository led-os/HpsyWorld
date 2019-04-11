package com.kuwai.ysy.module.circletwo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.widget.exchange.BGASortableNinePhotoLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.functions.Consumer;

public class PublishHoleTwoActivity extends BaseActivity implements BGASortableNinePhotoLayout.GridAdd, View.OnClickListener {

    private Switch btn_siliao;
    private LinearLayout ll_shang;
    private ImageView imgAdd;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private LinearLayout wheelLay;
    private String[] data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String selectString;
    private CustomDialog customDialog;
    private EditText mEtTitle;
    private LinearLayout ll_photo;
    private TextView mPhotoNum;
    private TextView mTitleNum;
    private EditText mEtContent;
    private TextView mContentNum;

    @Override
    protected void initView() {
        btn_siliao = findViewById(R.id.btn_siliao);
        ll_shang = findViewById(R.id.ll_shang);
        imgAdd = findViewById(R.id.img_add);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        wheelLay = findViewById(R.id.wheelview_container);
        btn_siliao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //popHeightCustom();
                    ll_shang.setVisibility(View.VISIBLE);
                } else {
                    ll_shang.setVisibility(View.GONE);
                }
            }
        });
        imgAdd.setOnClickListener(this);
        mPhotosSnpl.setGridAdd(this);
        mEtTitle = findViewById(R.id.et_title);
        mTitleNum = findViewById(R.id.title_num);
        ll_photo = findViewById(R.id.ll_photo);
        mPhotoNum = findViewById(R.id.photo_num);
        mEtContent = findViewById(R.id.et_content);
        mContentNum = findViewById(R.id.content_num);

        mEtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mTitleNum.setText(s.length() + "/25");
                } else {
                    mTitleNum.setText("0/25");
                }
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mContentNum.setText(s.length() + "/500");
                } else {
                    mContentNum.setText("0/500");
                }
            }
        });
        popHeightCustom();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_publish_hole_two;
    }

    private void popHeightCustom() {
        final SinglePicker<String> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(2);
        picker.setOffset(3);
        picker.setCycleDisable(true);
        picker.setDividerVisible(false);
        picker.setTextSize(16);
        picker.setShadowColor(0x1a5fbeff);
        picker.setTextColor(getResources().getColor(R.color.theme), getResources().getColor(R.color.gray_7b));
        picker.setTextPadding(20);
        picker.setOnWheelListener(new SinglePicker.OnWheelListener<String>() {
            @Override
            public void onWheeled(int index, String item) {
                selectString = item;
            }
        });
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        wheelLay.addView(picker.getContentView());
    }

    @Override
    public void gridAdd() {
        showSelectdialog();
    }

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
                            //startActivityForResult(new Intent(PublishHoleTwoActivity.this, AlivcSvideoRecordActivity.class), REQUST_CODE_VIDEO);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            Toast.makeText(PublishHoleTwoActivity.this, "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(PublishHoleTwoActivity.this, "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PublishHoleTwoActivity.this, "该功能需要获取相册权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void photoAndVideo() {
        PictureSelector.create(PublishHoleTwoActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
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

    private int themeId = R.style.picture_default_style;

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
                    PictureSelector.create(PublishHoleTwoActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(PublishHoleTwoActivity.this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(PublishHoleTwoActivity.this).externalPictureAudio(media.getPath());
                    break;
            }
        }
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;
    private static final int REQUST_CODE_PICTURE = 1001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        ll_photo.setVisibility(View.VISIBLE);
                        imgAdd.setVisibility(View.GONE);
                        mPhotoNum.setText(selectList.size() + "/9");
                        media = selectList.get(0);
                        String pictureType = media.getPictureType();
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        mPhotosSnpl.setData(selectList);
                    } else {
                        ll_photo.setVisibility(View.GONE);
                        imgAdd.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
                showSelectdialog();
                break;
        }
    }
}
