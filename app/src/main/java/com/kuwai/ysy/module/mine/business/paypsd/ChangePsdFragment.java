package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.security.MD5;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class ChangePsdFragment extends BaseFragment<ChangePsdPresenter> implements ChangePsdContract.IHomeView, View.OnClickListener {

    private TextView mCurrentPsd, mNewPsd, mReNewPsd;
    private String cpsd, newpsd, mrenewpsd;
    private NavigationLayout navigationLayout;
    private Boolean isComp;

    public static ChangePsdFragment newInstance() {
        Bundle args = new Bundle();
        ChangePsdFragment fragment = new ChangePsdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.change_psd_fragment;
    }

    @Override
    protected ChangePsdPresenter getPresenter() {
        return new ChangePsdPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mCurrentPsd = mRootView.findViewById(R.id.current_psd);
        mNewPsd = mRootView.findViewById(R.id.new_psd);
        mReNewPsd = mRootView.findViewById(R.id.renew_psd);


        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toChange();

            }
        });
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });


    }

    private void toChange() {

        cpsd = mCurrentPsd.getText().toString();
        newpsd = mNewPsd.getText().toString();
        mrenewpsd = mReNewPsd.getText().toString();

        isComp = true;
        if (TextUtils.isEmpty(cpsd) || TextUtils.isEmpty(newpsd) || TextUtils.isEmpty(mrenewpsd)) {
            ToastUtils.showShort("密码不能为空");
            isComp = false;
            return;
        }

        if (!StringUtils.strIsLetterOrNumer(newpsd)) {
            ToastUtils.showShort("新密码需为6位以上数字、字母混合");
            isComp = false;
            return;
        }
        if (!StringUtils.strIsLetterOrNumer(mrenewpsd)) {
            ToastUtils.showShort("新密码需为6位以上数字、字母混合");
            isComp = false;
            return;
        }
        if (!newpsd.equals(mrenewpsd)) {
            ToastUtils.showShort("输入新密码不一致");
            isComp = false;
            return;
        }

        if (isComp) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"),
                    Utils.encrypt32(cpsd), Utils.encrypt32(newpsd), Utils.encrypt32(mrenewpsd), C.CHANGE_LOGIN_PSD);
        }
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
