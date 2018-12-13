package com.kuwai.ysy.module.home.business.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgClose;
    private TextView mTvRegist;
    private TextView mTitle;
    private TextView mTvTel;
    private MyEditText mEtTel;
    private View mLine1;
    private TextView mTvCode;
    private ImageView mImgEye;
    private MyEditText mEtCode;
    private View mLine2;
    private TextView mTvForget;
    private TextView mTvLoginByCode;
    private SuperButton mBtnLogin;
    private ImageView mImgQq;
    private ImageView mImgWechat;
    private ImageView mImgSina;
    private TextView mTvThird;

    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_login;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_by_code:
                start(SendCodeFragment.newInstance());
                break;
            case R.id.img_eye:
                if (isHideFirst == true) {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_blink);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    mEtCode.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_ce);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    mEtCode.setTransformationMethod(method);
                    isHideFirst = true;

                }
                // 光标的位置
                int index = mEtCode.getText().toString().length();
                mEtCode.setSelection(index);
                break;
            case R.id.tv_forget:
                start(ChongzhiPsdFragment.newInstance());
                break;
            case R.id.tv_regist:
                start(Regist1Fragment.newInstance());
                break;
            case R.id.btn_login:
                SPManager.get().putString("uid", "");
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
                break;
            case R.id.img_qq:
                SPManager.get().putString("uid", "1");
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgClose = mRootView.findViewById(R.id.img_close);
        mTvRegist = mRootView.findViewById(R.id.tv_regist);
        mTitle = mRootView.findViewById(R.id.title);
        mTvTel = mRootView.findViewById(R.id.tv_tel);
        mEtTel = mRootView.findViewById(R.id.et_tel);
        mLine1 = mRootView.findViewById(R.id.line1);
        mTvCode = mRootView.findViewById(R.id.tv_code);
        mImgEye = mRootView.findViewById(R.id.img_eye);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mLine2 = mRootView.findViewById(R.id.line2);
        mTvForget = mRootView.findViewById(R.id.tv_forget);
        mTvLoginByCode = mRootView.findViewById(R.id.tv_login_by_code);
        mBtnLogin = mRootView.findViewById(R.id.btn_login);
        mImgQq = mRootView.findViewById(R.id.img_qq);
        mImgWechat = mRootView.findViewById(R.id.img_wechat);
        mImgSina = mRootView.findViewById(R.id.img_sina);
        mTvThird = mRootView.findViewById(R.id.tv_third);

        mTvLoginByCode.setOnClickListener(this);
        mImgEye.setOnClickListener(this);
        mTvForget.setOnClickListener(this);
        mTvRegist.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mImgQq.setOnClickListener(this);

        mEtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeState(s.toString(), 1);
            }
        });

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeState(s.toString(), 2);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void changeState(String s, int type) {
        if (!Utils.isNullString(type == 1 ? mEtCode.getText().toString() : mEtTel.getText().toString()) && !Utils.isNullString(s)) {
            mBtnLogin.setEnabled(true);
            mBtnLogin.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtnLogin.setEnabled(false);
            mBtnLogin.setTextColor(getResources().getColor(R.color.gray_7b));
        }
    }
}
