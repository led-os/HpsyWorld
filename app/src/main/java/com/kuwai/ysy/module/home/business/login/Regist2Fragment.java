package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class Regist2Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private TextView mCodeInfo;
    private MyEditText mEtCode;
    private CountDownTextView mTvCountDown;
    private View mLine1;
    private SuperButton mBtnNext;

    public static Regist2Fragment newInstance() {
        Bundle args = new Bundle();
        Regist2Fragment fragment = new Regist2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_regist_input_code;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                start(Regist3Fragment.newInstance());
                break;
            case R.id.tv_count_down:
                if (Utils.isNullString(mEtCode.getText().toString())) {
                    ToastUtils.showShort("请输入验证码", getActivity());
                } else {
                    mTvCountDown.start();
                    //getCode(phoneEt.getText().toString(), "C");
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        mCodeInfo = mRootView.findViewById(R.id.code_info);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mTvCountDown.start();
        mTvCountDown.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
