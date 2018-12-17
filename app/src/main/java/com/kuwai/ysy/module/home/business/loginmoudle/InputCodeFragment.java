package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class InputCodeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgLeft;
    private TextView mTitle;
    private TextView mCodeInfo;
    private MyEditText mEtCode;
    private CountDownTextView mTvCountDown;
    private View mLine1;
    private SuperButton mBtnAuth;

    private String mPhone;

    public static InputCodeFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone", phone);
        InputCodeFragment fragment = new InputCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_login_input_code;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_count_down:
                getCode(mPhone, C.CODE_LOGIN);
                mTvCountDown.start();
                break;
            case R.id.btn_auth:
                if (Utils.isNullString(mEtCode.getText().toString())) {
                    ToastUtils.showShort("请输入验证码", getActivity());
                } else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("type", C.LOGIN_CODE);
                    param.put("login_type", "1");
                    param.put("phone", mPhone);
                    param.put("check_code", mEtCode.getText().toString());
                    //param.put("password", mEtCode.getText().toString());
                    login(param);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPhone = getArguments().getString("phone");

        mImgLeft = mRootView.findViewById(R.id.img_left);
        mTitle = mRootView.findViewById(R.id.title);
        mCodeInfo = mRootView.findViewById(R.id.code_info);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnAuth = mRootView.findViewById(R.id.btn_auth);

        mCodeInfo.setText("短信验证码已发送至" + StringUtils.hideMobilePhone4(mPhone));
        getCode(mPhone, C.CODE_LOGIN);
        mTvCountDown.start();
        mTvCountDown.setOnClickListener(this);
        mBtnAuth.setOnClickListener(this);
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

    public void login(Map<String, String> param) {
        addSubscription(HomeApiFactory.login(param).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                if (loginBean.getCode() == 200) {

                } else {
                    ToastUtils.showShort(loginBean.getMsg());
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
