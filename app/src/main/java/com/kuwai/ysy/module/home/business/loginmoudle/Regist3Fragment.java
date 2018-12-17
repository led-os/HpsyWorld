package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.common.utils.MD5Util;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class Regist3Fragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvRegistTitle;
    private TextView mTitle;
    private MyEditText mEtPsd;
    private ImageView mImgEye;
    private View mLine1;
    private SuperButton mBtnComplete;

    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true

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
                if (!StringUtils.strIsLetterOrNumer(mEtPsd.getText().toString())) {
                    ToastUtils.showShort("密码需为6位以上数字、字母混合");
                } else {
                    SPManager.get().putString(C.REGIST_PSD, Utils.encrypt32(mEtPsd.getText().toString()));
                    start(InfoHeadFragment.newInstance());
                }
                break;
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
        mImgEye.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
