package com.kuwai.ysy.module.home.business.loginmoudle;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class StartupPageActivity extends BaseActivity implements Animation.AnimationListener {

    private static int SPLASH_TIME_OUT = 1500;
    private ImageView splashImage, TxtImg;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_startup;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        splashImage = (ImageView) findViewById(R.id.back_logo);
        TxtImg = findViewById(R.id.normal_img);
        Glide.with(StartupPageActivity.this)
                .load(R.drawable.bg_start)
                .into(splashImage);

            /*Glide.with(StartupPageActivity.this)
                    .load(R.drawable.bg_start)
                    .into(TxtImg);*/

        Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
        animation.setDuration(SPLASH_TIME_OUT); // 动画持续时间
        animation.setFillAfter(true); // 动画结束后停留在结束的位置
        animation.setAnimationListener(this);
        splashImage.startAnimation(animation);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }


    @Override
    public void onAnimationEnd(Animation animation) {
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Intent i = new Intent(StartupPageActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        if (aBoolean) {
                        } else {
                        }
                    }
                });
    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
