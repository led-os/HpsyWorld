package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class ChangePhoneFragment extends BaseFragment<ChangePhonePresenter> implements ChangePhoneContract.IHomeView, View.OnClickListener {

    private EditText mTvPhone, mtvNewPhone;
    private MyEditText vcode;
    private String mphone, mnewphone, mVcode;
    private CountDownTextView mTvCountDown;
    private NavigationLayout navigationLayout;
    private Boolean isComp;
    private String code = "0";

    public static ChangePhoneFragment newInstance() {
        Bundle args = new Bundle();
        ChangePhoneFragment fragment = new ChangePhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.change_phone_fragment;
    }

    @Override
    protected ChangePhonePresenter getPresenter() {
        return new ChangePhonePresenter(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_count_down:
                mnewphone = mtvNewPhone.getText().toString();

                if (StringUtils.isLegalPhone(mnewphone)) {
                    mTvCountDown.start();
                    getCode(mnewphone, C.CODE_REGIST);
                }else {
                    ToastUtils.showShort("手机号不合法");
                }
                break;
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        navigationLayout = mRootView.findViewById(R.id.navigation);
        TextView mtip = mRootView.findViewById(R.id.tv_tip);
        mtip.setText("更换手机后，下次登录可使用新手机号登录。当前手机号为："
                + SPManager.get().getStringValue("phone", "12345678901").substring(0, 3) + "****"
                + SPManager.get().getStringValue("phone", "12345678901").substring(7, 11));
        mTvPhone = mRootView.findViewById(R.id.tv_phone);
        mtvNewPhone = mRootView.findViewById(R.id.tv_newphone);
        vcode = mRootView.findViewById(R.id.et_code);
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mTvCountDown.setOnClickListener(this);

        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toChangePhone();
            }
        });
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

    }

    private void toChangePhone() {

        mphone = mTvPhone.getText().toString();
        mnewphone = mtvNewPhone.getText().toString();
        mVcode = vcode.getText().toString();

        isComp = true;

        if (TextUtils.isEmpty(mphone) || TextUtils.isEmpty(mnewphone)) {
            isComp = false;
            ToastUtils.showShort("手机号不能为空");
            return;
        }

        if (TextUtils.isEmpty(mVcode)) {
            isComp = false;
            ToastUtils.showShort("验证码不能为空");
            return;
        }

        if (mphone.equals(mnewphone)) {
            isComp = false;
            ToastUtils.showShort("手机号不能相同");
            return;
        }

        if (isComp) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), mphone, mnewphone, mVcode);
        }

    }

    public void getCode(String phone, String type) {
        addSubscription(HomeApiFactory.getCode(phone, type).subscribe(new Consumer<CodeBean>() {
            @Override
            public void accept(CodeBean codeBean) throws Exception {
                if (codeBean.getCode() == 200) {
                    // start(InputCodeFragment.newInstance(mEtCode.getText().toString()));
                    code = String.valueOf(codeBean.getData().getMsgTxt());
                    Log.e("code+++++++", code);
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


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void setHomeData(SimpleResponse response) {

    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
