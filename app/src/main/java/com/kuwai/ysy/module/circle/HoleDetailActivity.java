package com.kuwai.ysy.module.circle;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.circle.business.HoleDetail.HoleDetailMainFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class HoleDetailActivity extends BaseActivity implements View.OnClickListener {

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
        bundle = new Bundle();
        bundle.putString("tid", getIntent().getStringExtra("tid"));
        loadRootFragment(R.id.container, HoleDetailMainFragment.newInstance(bundle), false, true);
    }

    @Override
    public void onClick(View v) {

    }
}