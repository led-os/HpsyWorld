package com.kuwai.ysy.module.home.business.loginmoudle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class StartupPageActivity extends BaseActivity implements Animation.AnimationListener {

    private static int SPLASH_TIME_OUT = 1500;
    private ImageView splashImage, TxtImg;
    private SharedPreferences sharedPreferences;
    private Long currenttime, starttime, endtime, showtime;


    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private boolean needImmersive() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_startup;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        splashImage = (ImageView) findViewById(R.id.back_logo);
//        TxtImg = findViewById(R.id.normal_img);

        sharedPreferences = getSharedPreferences(C.SP_NAME, Context.MODE_PRIVATE); //私有数据
        starttime = sharedPreferences.getLong("start_time", 0);
        endtime = sharedPreferences.getLong("end_time", 0);

        currenttime = System.currentTimeMillis() / 1000;
        showtime = sharedPreferences.getLong("show_time", 0);

        if (currenttime > starttime && currenttime < endtime) {
            //活动启动页
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dskgxt/pic/ysy_start_page.jpg");
            if (f.exists()) {
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                Glide.with(StartupPageActivity.this)
                        .load(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dskgxt/pic/ysy_start_page.jpg")
                        .apply(options)
                        .into(splashImage);
            }

            /*Animation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0); // 将图片放大1.2倍，从中心开始缩放
            animation.setDuration(showtime * 1000); // 动画持续时间
            animation.setFillAfter(true); // 动画结束后停留在结束的位置
            animation.setAnimationListener(this);
            splashImage.startAnimation(animation);*/
            requestLocationPermission();

        } else {
            //默认启动页
            /*Glide.with(StartupPageActivity.this)
                    .load(R.drawable.bg_start)
                    .into(splashImage);*/

           /* Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
            animation.setDuration(SPLASH_TIME_OUT); // 动画持续时间
            animation.setFillAfter(true); // 动画结束后停留在结束的位置
            animation.setAnimationListener(this);
            splashImage.startAnimation(animation);*/
            requestLocationPermission();
        }

        getSts();

    }

    void getSts() {
        addSubscription(ChatApiFactory.getSts().subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                if (stsBean.getCode() == 200) {
                    SPManager.get().putString(C.ALI_ACID, stsBean.getData().getAccessKeyId());
                    SPManager.get().putString(C.ALI_SECRET, stsBean.getData().getAccessKeySecret());
                    SPManager.get().putString(C.ALI_TOKEN, stsBean.getData().getSecurityToken());
                } else {
                    //ToastUtils.showShort(stsBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }


    @Override
    public void onAnimationEnd(Animation animation) {
        //requestLocationPermission();
    }

    private void requestLocationPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Intent i = new Intent(StartupPageActivity.this, HomeActivity.class);
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
