package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;
    private Bundle bundle;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_container;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

        loadRootFragment(R.id.container, MineHomepageFragment.newInstance(), false, true);
    }

    @Override
    public void onClick(View v) {

    }
}
