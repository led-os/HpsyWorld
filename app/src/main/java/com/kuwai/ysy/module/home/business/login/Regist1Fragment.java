package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;

public class Regist1Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private MyEditText mEtTel;
    private View mLine1;
    private SuperButton mBtnNext;
    private TextView mToLogin;

    public static Regist1Fragment newInstance() {
        Bundle args = new Bundle();
        Regist1Fragment fragment = new Regist1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_regist_1;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                start(Regist2Fragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        mEtTel = mRootView.findViewById(R.id.et_tel);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);
        mToLogin = mRootView.findViewById(R.id.to_login);

        mBtnNext.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
