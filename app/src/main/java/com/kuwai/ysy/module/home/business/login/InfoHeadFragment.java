package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;

public class InfoHeadFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvPass;
    private TextView mTitle;
    private CircleImageView mImgHead;
    private TextView mTvHead;
    private EditText mEtName;
    private View mLine1;
    private SuperButton mBtnNext;

    public static InfoHeadFragment newInstance() {
        Bundle args = new Bundle();
        InfoHeadFragment fragment = new InfoHeadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_head;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                start(InfoSexFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvPass = mRootView.findViewById(R.id.tv_pass);
        mTitle = mRootView.findViewById(R.id.title);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvHead = mRootView.findViewById(R.id.tv_head);
        mEtName = mRootView.findViewById(R.id.et_name);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
