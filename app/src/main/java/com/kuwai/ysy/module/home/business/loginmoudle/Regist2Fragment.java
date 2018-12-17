package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.mine.MyPointActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CountDownTextView;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class Regist2Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private TextView mCodeInfo;
    private MyEditText mEtCode;
    private CountDownTextView mTvCountDown;
    private View mLine1;
    private SuperButton mBtnNext;
    private String mPhone;
    private String code = "0";

    public static Regist2Fragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone", phone);
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
                if (Utils.isNullString(mEtCode.getText().toString())) {
                    ToastUtils.showShort("请输入验证码", getActivity());
                } else if (!code.equals(mEtCode.getText().toString())) {
                    ToastUtils.showShort("验证码错误", getActivity());
                    /*HashMap<String, String> param = new HashMap<>();
                    param.put("phone", mPhone);
                    param.put("check_code", mEtCode.getText().toString()); //1代表android
                    param.put("type", C.CODE_REGIST);
                    codeAuth(param);*/
                } else {
                    SPManager.get().putString(C.REGIST_CODE, mEtCode.getText().toString());
                    start(Regist3Fragment.newInstance());
                }
                break;
            case R.id.tv_count_down:
                mTvCountDown.start();
                getCode(mPhone, C.CODE_REGIST);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPhone = getArguments().getString("phone");

        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        mCodeInfo = mRootView.findViewById(R.id.code_info);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mCodeInfo.setText("短信验证码已发送至" + StringUtils.hideMobilePhone4(mPhone));
        mBtnNext.setOnClickListener(this);
        mTvCountDown.start();
        getCode(mPhone, C.CODE_REGIST);
        mTvCountDown.setOnClickListener(this);
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
                    code = String.valueOf(codeBean.getData().getMsgTxt());
                    Log.e("code+++++++",code);
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

    public void codeAuth(Map<String, String> param) {
        addSubscription(HomeApiFactory.codeAuth(param).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse codeBean) throws Exception {
                if (codeBean.code == 200) {
                    SPManager.get().putString(C.REGIST_CODE, mEtCode.getText().toString());
                    start(Regist3Fragment.newInstance());
                } else {
                    ToastUtils.showShort(codeBean.msg);
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
