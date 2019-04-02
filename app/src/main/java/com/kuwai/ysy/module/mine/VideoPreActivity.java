package com.kuwai.ysy.module.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;

public class VideoPreActivity extends BaseActivity{

    private NavigationLayout mNavigation;
    private ImageView mImgPre;
    private ImageView mImgPlay;
    private TextView mTvUpload;
    private TextView mTvReVideo;

    private Bitmap mBitmap;
    private String imagePath;
    private String videoPath;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        LocalMedia media = new LocalMedia();
        if (getIntent().getStringExtra("path") != null) {
            videoPath = getIntent().getStringExtra("path");
            media.setPath(getIntent().getStringExtra("path"));
            media.setPictureType("video");
            media.setMimeType(PictureMimeType.ofVideo());
            mBitmap = FileUtils.voidToFirstBitmap(videoPath);
            imagePath = FileUtils.bitmapToStringPath(this, mBitmap);
            mImgPre.setImageBitmap(mBitmap);
        }
    }

    @Override
    protected void initView() {
        mNavigation = findViewById(R.id.navigation);
        mImgPre = findViewById(R.id.img_pre);
        mImgPlay = findViewById(R.id.img_play);
        mTvUpload = findViewById(R.id.tv_upload);
        mTvReVideo = findViewById(R.id.tv_re_video);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_pre_video;
    }
}
