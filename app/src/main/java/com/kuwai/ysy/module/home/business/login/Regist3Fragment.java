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

public class Regist3Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private MyEditText mEtPsd;
    private ImageView mImgEye;
    private View mLine1;
    private SuperButton mBtnComplete;

    public static Regist3Fragment newInstance() {
        Bundle args = new Bundle();
        Regist3Fragment fragment = new Regist3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_regist_3;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                start(InfoHeadFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        mEtPsd = mRootView.findViewById(R.id.et_psd);
        mImgEye = mRootView.findViewById(R.id.img_eye);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnComplete = mRootView.findViewById(R.id.btn_complete);

        mBtnComplete.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
