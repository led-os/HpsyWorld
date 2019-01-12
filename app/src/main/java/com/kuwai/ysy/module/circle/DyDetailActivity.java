package com.kuwai.ysy.module.circle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.circle.business.DyDashang.DyDashangListFragment;
import com.kuwai.ysy.module.circle.business.DyDetail.DyDetailMainFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.umeng.socialize.UMShareAPI;

public class DyDetailActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;
    protected Bundle bundle;

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
        //bundle = new Bundle();
        //bundle.putString("did", getIntent().getStringExtra("did"));
       // bundle.putInt("index",getIntent().getIntExtra("index"));
        loadRootFragment(R.id.container, DyDetailMainFragment.newInstance(getIntent().getExtras()), false, true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
