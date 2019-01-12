package com.kuwai.ysy.module.find;

import android.os.Bundle;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.find.business.FoundLocation.FoundLocationFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class FoundLocationActivity extends BaseActivity implements View.OnClickListener {

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
        //loadRootFragment(R.id.container, new FoundLocationFragment(),false,true);
    }

    @Override
    public void onClick(View v) {

    }
}
