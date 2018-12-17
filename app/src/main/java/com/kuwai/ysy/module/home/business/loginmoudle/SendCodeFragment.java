package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.Map;

import io.reactivex.functions.Consumer;

public class SendCodeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgLeft;
    private TextView mTitle;
    private MyEditText mEtCode;
    private View mLine1;
    private SuperButton mBtnGetcode;

    public static SendCodeFragment newInstance() {
        Bundle args = new Bundle();
        SendCodeFragment fragment = new SendCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_login_code;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getcode:
                if (StringUtils.isLegalPhone(mEtCode.getText().toString())) {
                    start(InputCodeFragment.newInstance(mEtCode.getText().toString()));
                } else {
                    ToastUtils.showShort("请输入正确的手机号");
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgLeft = mRootView.findViewById(R.id.img_left);
        mTitle = mRootView.findViewById(R.id.title);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnGetcode = mRootView.findViewById(R.id.btn_getcode);

        mBtnGetcode.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

}
