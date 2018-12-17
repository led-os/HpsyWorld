package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.Map;

import io.reactivex.functions.Consumer;

public class ChongzhiPsdFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgLeft;
    private TextView mTitle;
    private MyEditText mEtTel;
    private View mLine;
    private MyEditText mEtCode;
    private CountDownTextView mTvCountDown;
    private View mLine1;
    private MyEditText mEtPsd;
    private ImageView mImgEye;
    private View mLine2;
    private SuperButton mBtnLogin;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true

    public static ChongzhiPsdFragment newInstance() {
        Bundle args = new Bundle();
        ChongzhiPsdFragment fragment = new ChongzhiPsdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_change_psd;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_eye:
                if (isHideFirst == true) {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_ce);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    mEtPsd.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_blink);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    mEtPsd.setTransformationMethod(method);
                    isHideFirst = true;

                }
                // 光标的位置
                int index = mEtPsd.getText().toString().length();
                mEtPsd.setSelection(index);
                break;
            case R.id.btn_login:
                if (checkNull()) {
                    chongzhiPsd(mEtTel.getText().toString(), mEtPsd.getText().toString(), mEtCode.getText().toString());
                }
                break;
            case R.id.tv_count_down:
                if (Utils.isNullString(mEtTel.getText().toString())) {
                    ToastUtils.showShort("请输入手机号");
                } else {
                    getCode(mEtTel.getText().toString(), C.CODE_CHANG_PSD);
                    mTvCountDown.start();
                }
                break;
        }
    }

    private boolean checkNull() {
        if (Utils.isNullString(mEtTel.getText().toString())) {
            ToastUtils.showShort("请输入手机号");
            return false;
        } else if (Utils.isNullString(mEtCode.getText().toString())) {
            ToastUtils.showShort("请输入验证码");
            return false;
        } else if (!StringUtils.strIsLetterOrNumer(mEtPsd.getText().toString())) {
            ToastUtils.showShort("密码需为6位以上数字、字母混合");
            return false;
        }
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgLeft = mRootView.findViewById(R.id.img_left);
        mTitle = mRootView.findViewById(R.id.title);
        mEtTel = mRootView.findViewById(R.id.et_tel);
        mLine = mRootView.findViewById(R.id.line);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mLine1 = mRootView.findViewById(R.id.line1);
        mEtPsd = mRootView.findViewById(R.id.et_psd);
        mImgEye = mRootView.findViewById(R.id.img_eye);
        mLine2 = mRootView.findViewById(R.id.line2);
        mBtnLogin = mRootView.findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
        mTvCountDown.setOnClickListener(this);
        mImgEye.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    public void getCode(String phone, String type) {
        addSubscription(HomeApiFactory.getCode(phone, type).subscribe(new Consumer<CodeBean>() {
            @Override
            public void accept(CodeBean codeBean) throws Exception {
                if (codeBean.getCode() == 200) {
                    // start(InputCodeFragment.newInstance(mEtCode.getText().toString()));
                } else {
                    ToastUtils.showShort(codeBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    public void chongzhiPsd(String phone, String psd, String code) {
        addSubscription(HomeApiFactory.resetPassword(phone, psd, code).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse loginBean) throws Exception {
                if (loginBean.code == 200) {
                    ToastUtils.showShort("重置成功");
                    popTo(LoginFragment.class, true);
                } else {
                    ToastUtils.showShort(loginBean.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
