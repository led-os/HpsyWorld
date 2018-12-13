package com.kuwai.ysy.module.mine.business.credit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class MyCreditFragment extends BaseFragment implements View.OnClickListener {

    private SuperTextView mAuthTv;

    public static MyCreditFragment newInstance() {
        Bundle args = new Bundle();
        MyCreditFragment fragment = new MyCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_credit_certification;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_auth:
                start(AuthFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAuthTv = mRootView.findViewById(R.id.tv_auth);
        mAuthTv.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
