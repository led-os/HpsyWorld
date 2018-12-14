package com.kuwai.ysy.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.language.LocalManageUtil;
import com.kuwai.ysy.widget.MultipleStatusView;
import com.rayhahah.rbase.base.RBaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.fragmentmanage.anim.DefaultHorizontalAnimator;
import com.rayhahah.rbase.fragmentmanage.anim.FragmentAnimator;
import com.rayhahah.rbase.utils.base.StatusBarUtil;
import com.rayhahah.rbase.utils.base.StatusBartext;


public abstract class BaseActivity<T extends RBasePresenter> extends RBaseActivity<T> {

    protected MultipleStatusView mLayoutStatusView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needImmersive()) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.transparent), 0);
            StatusBarUtil.setLightMode(this);
            //StatusBartext.StatusBarDarkMode(this,3);
        }
        initView();
        initEventAndData(savedInstanceState);
        initListener();
    }

    /**
     * 初始化视图以及数据操作
     *
     * @param savedInstanceState
     */
    protected abstract void initEventAndData(Bundle savedInstanceState);

    private void initListener() {
        if (mLayoutStatusView != null) {
            mLayoutStatusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start();
                }
            });
        }
    }

    protected abstract void initView();

    /**
     * 获取数据
     */
    public void start() {

    }

    ;

    private boolean needImmersive() {
        return true;
    }

    /**
     * 根据传入的类名打开指定activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        Intent intent = new Intent();
        intent.setClass(this, pClass);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void openActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> clz, int type) {
        Intent intent = new Intent(this, clz);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
