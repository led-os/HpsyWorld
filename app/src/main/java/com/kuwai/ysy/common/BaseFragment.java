package com.kuwai.ysy.common;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.kuwai.ysy.widget.MultipleStatusView;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;


public abstract class BaseFragment<T extends RBasePresenter>
        extends RBaseFragment<T> {
    static {
        //设置VectorDrawable兼容支持，否则会闪退
        AppCompatDelegate
                .setCompatVectorFromResourcesEnabled(true);
    }

    protected MultipleStatusView mLayoutStatusView = null;

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        if (mLayoutStatusView != null) {
            mLayoutStatusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onLazyInitView(savedInstanceState);
                    retry();
                }
            });
        }
    }

    /**
     * 获取数据
     */
    public void retry(){

    };

    public abstract void initView(Bundle savedInstanceState);
}
