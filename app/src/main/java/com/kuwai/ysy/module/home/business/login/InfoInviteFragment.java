package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class InfoInviteFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvPass;
    private TextView mTitle;
    private EditText mEtId;
    private View mLine1;
    private SuperButton mBtnStart;

    public static InfoInviteFragment newInstance() {
        Bundle args = new Bundle();
        InfoInviteFragment fragment = new InfoInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_invite;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
               // start(InfoSexFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvPass = mRootView.findViewById(R.id.tv_pass);
        mTitle = mRootView.findViewById(R.id.title);
        mEtId = mRootView.findViewById(R.id.et_id);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnStart = mRootView.findViewById(R.id.btn_start);

        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
