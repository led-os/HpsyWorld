package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class PayPsdFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private SuperTextView mStSetPayforPsd;
    private SuperTextView mStChangePayforPsd;

    public static PayPsdFragment newInstance() {
        Bundle args = new Bundle();
        PayPsdFragment fragment = new PayPsdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.payfor_psd_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.st_set_payfor_psd:
                start(SetPayPsdFragment.newInstance());
                break;
            case R.id.st_change_payfor_psd:
                start(ChangePayPsdFragment.newInstance());
                break;

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolbar = mRootView.findViewById(R.id.toolbar);
        mTitle = mRootView.findViewById(R.id.title);
        mStSetPayforPsd = mRootView.findViewById(R.id.st_set_payfor_psd);
        mStChangePayforPsd = mRootView.findViewById(R.id.st_change_payfor_psd);
        mStChangePayforPsd.setOnClickListener(this);
        mStSetPayforPsd.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}