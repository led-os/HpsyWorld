package com.kuwai.ysy.module.mine.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.business.paypsd.ChangePhoneFragment;
import com.kuwai.ysy.module.mine.business.paypsd.ChangePsdFragment;
import com.kuwai.ysy.module.mine.business.paypsd.MsgNoticeFragment;
import com.kuwai.ysy.module.mine.business.paypsd.PayPsdFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class SecurityFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private SuperTextView mStChangePsd;
    private SuperTextView mStChangePhone;
    private SuperTextView mStPayPsd;
    private SuperTextView mStClearChat;


    public static SecurityFragment newInstance() {
        Bundle args = new Bundle();
        SecurityFragment fragment = new SecurityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.account_and_security_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.st_change_psd:
                start(ChangePsdFragment.newInstance());
                break;
            case R.id.st_change_phone:
                start(ChangePhoneFragment.newInstance());
                break;
            case R.id.st_pay_psd:
                start(PayPsdFragment.newInstance());
                break;
            case R.id.st_clear_chat:
                //start(MsgNoticeFragment.newInstance());
                break;

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mToolbar = mRootView.findViewById(R.id.toolbar);
        mTitle = mRootView.findViewById(R.id.title);
        mStChangePsd = mRootView.findViewById(R.id.st_change_psd);
        mStChangePhone = mRootView.findViewById(R.id.st_change_phone);
        mStPayPsd = mRootView.findViewById(R.id.st_pay_psd);
        mStClearChat = mRootView.findViewById(R.id.st_clear_chat);
        mStChangePhone.setOnClickListener(this);
        mStChangePsd.setOnClickListener(this);
        mStClearChat.setOnClickListener(this);
        mStPayPsd.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
