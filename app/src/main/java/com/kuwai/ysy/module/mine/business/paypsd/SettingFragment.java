package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.business.SecurityFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private SuperTextView mStSecurity;
    private SuperTextView mStMessage;
    private SuperTextView mStHelp;
    private SuperTextView mStAgree;
    private SuperTextView mStAbout;
    private SuperTextView mStBlack;
    private SuperTextView mStScore;
    private SuperTextView mStClear;
    private SuperTextView mStExit;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.setting_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.st_security:
                start(SecurityFragment.newInstance());
                break;
            case R.id.st_message:
                start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_help:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_agree:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_about:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_black:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_score:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_clear:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_exit:
                //start(MsgNoticeFragment.newInstance());
                break;

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mToolbar = mRootView.findViewById(R.id.toolbar);
        mTitle = mRootView.findViewById(R.id.title);
        mStSecurity = mRootView.findViewById(R.id.st_security);
        mStMessage = mRootView.findViewById(R.id.st_message);
        mStHelp = mRootView.findViewById(R.id.st_help);
        mStAgree = mRootView.findViewById(R.id.st_agree);
        mStAbout = mRootView.findViewById(R.id.st_about);
        mStBlack = mRootView.findViewById(R.id.st_black);
        mStScore = mRootView.findViewById(R.id.st_score);
        mStClear = mRootView.findViewById(R.id.st_clear);
        mStExit = mRootView.findViewById(R.id.st_exit);
        mStMessage.setOnClickListener(this);
        mStSecurity.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
