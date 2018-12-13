package com.kuwai.ysy.module.home.business.sading;

import android.os.Bundle;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by sunnysa on 2018/7/25.
 */

public class Mainactivity extends BaseActivity{

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.fl_tab_container,Home2Fragment.newInstance());
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main2;
    }
}
