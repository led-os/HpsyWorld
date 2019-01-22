package com.kuwai.ysy.module.home;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.MyFriendFragment;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.rayhahah.rbase.base.RBasePresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

public class HomePicActivity extends BaseActivity implements View.OnClickListener {

    public Banner mp_video;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_banner;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        this.mp_video = findViewById(R.id.banner);
        mp_video.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mp_video.setImageLoader(new GlideImageLoader());
        mp_video.isAutoPlay(false);
        mp_video.setImages(getIntent().getStringArrayListExtra("list"));
        mp_video.start();
    }

    @Override
    public void onClick(View v) {

    }
}
