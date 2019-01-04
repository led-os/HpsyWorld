package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PasswordInputView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class ChangePayPsdFragment extends BaseFragment<ChangePayPsdPresenter> implements ChangePayPsdContract.IHomeView, View.OnClickListener {


    private PasswordInputView mCurretPsd, mPsdView, mSurePsd;
    private NavigationLayout navigationLayout;
    private String mCPsd, mPsd, mRePsd;

    public static ChangePayPsdFragment newInstance() {
        Bundle args = new Bundle();
        ChangePayPsdFragment fragment = new ChangePayPsdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.change_payfor_psd_fragment;
    }

    @Override
    protected ChangePayPsdPresenter getPresenter() {
        return new ChangePayPsdPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mCPsd) && !TextUtils.isEmpty(mPsd) && !TextUtils.isEmpty(mRePsd)) {
                    if (mPsd.equals(mRePsd)) {
                        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), Utils.encrypt32(mCPsd), Utils.encrypt32(mPsd), Utils.encrypt32(mRePsd), C.CHANGE_PAY_PSD);
                    } else {
                        ToastUtils.showShort("两次输入的新密码不一致");
                    }
                } else {
                    ToastUtils.showShort("密码不能为空");
                }
            }
        });

        mPsdView = mRootView.findViewById(R.id.current_psd);
        mPsdView.setOnFinishListener(new PasswordInputView.OnFinishListener() {
            @Override
            public void setOnPasswordFinished(String text) {
                if (text.length() == 6) {
                    mCPsd = text;
                } else {
                    mCPsd = "";
                }
            }
        });

        mSurePsd = mRootView.findViewById(R.id.new_psd);
        mSurePsd.setOnFinishListener(new PasswordInputView.OnFinishListener() {
            @Override
            public void setOnPasswordFinished(String text) {
                if (text.length() == 6) {
                    mPsd = text;
                } else {
                    mPsd = "";
                }
            }
        });

        mSurePsd = mRootView.findViewById(R.id.sure_new_psd);
        mSurePsd.setOnFinishListener(new PasswordInputView.OnFinishListener() {
            @Override
            public void setOnPasswordFinished(String text) {
                if (text.length() == 6) {
                    mRePsd = text;
                } else {
                    mRePsd = "";
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void setHomeData(SimpleResponse response) {
        ToastUtils.showShort(response.msg);
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
