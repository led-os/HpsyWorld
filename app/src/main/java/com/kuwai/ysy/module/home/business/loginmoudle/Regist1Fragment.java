package com.kuwai.ysy.module.home.business.loginmoudle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class Regist1Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private MyEditText mEtTel;
    private View mLine1;
    private SuperButton mBtnNext;
    private TextView mToLogin;
    private TextView tv_xieyi;

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
                if (StringUtils.isLegalPhone(mEtTel.getText().toString())) {
                    SPManager.get().putString(C.REGIST_PHONE, mEtTel.getText().toString());
                    start(Regist2Fragment.newInstance(mEtTel.getText().toString()));
                } else {
                    ToastUtils.showShort("请输入正确的手机号");
                }
                break;
            case R.id.to_login:
                pop();
                break;
            case R.id.tv_xieyi:
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.XIEYI);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        tv_xieyi = mRootView.findViewById(R.id.tv_xieyi);
        mEtTel = mRootView.findViewById(R.id.et_tel);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);
        mToLogin = mRootView.findViewById(R.id.to_login);

        mBtnNext.setOnClickListener(this);
        mToLogin.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);

        mEtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Utils.isNullString(s.toString())) {
                    mBtnNext.setEnabled(true);
                    mBtnNext.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtnNext.setEnabled(false);
                    mBtnNext.setTextColor(getResources().getColor(R.color.gray_7b));
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
