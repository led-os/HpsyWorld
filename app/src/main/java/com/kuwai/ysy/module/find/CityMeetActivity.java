package com.kuwai.ysy.module.find;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.find.business.CityMeet.CityMeetListFragment;
import com.kuwai.ysy.module.findtwo.CityMeetTwoFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class CityMeetActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;

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
        loadRootFragment(R.id.container, CityMeetTwoFragment.newInstance(),false,true);
    }

    @Override
    public void onClick(View v) {

    }
}
