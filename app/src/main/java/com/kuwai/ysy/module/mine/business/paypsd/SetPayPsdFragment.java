package com.kuwai.ysy.module.mine.business.paypsd;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PasswordInputView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

public class SetPayPsdFragment extends BaseFragment<SetPayPsdPresenter> implements SetPayPsdContract.IHomeView, View.OnClickListener {

    private PasswordInputView mPsdView, mSurePsd;
    private NavigationLayout navigationLayout;
    private EditText mPsdEt;
    private String mPsd, mRePsd;
    private CustomDialog eduDialog = null;

    public static SetPayPsdFragment newInstance() {
        Bundle args = new Bundle();
        SetPayPsdFragment fragment = new SetPayPsdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.set_payfor_psd_fragment;
    }

    @Override
    protected SetPayPsdPresenter getPresenter() {
        return new SetPayPsdPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPsdEt = mRootView.findViewById(R.id.psd_view);
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
                if (!TextUtils.isEmpty(mPsd) && !TextUtils.isEmpty(mRePsd)) {
                    mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), Utils.encrypt32(mPsdEt.getText().toString()), Utils.encrypt32(mPsd), Utils.encrypt32(mRePsd));
                } else {
                    ToastUtils.showShort("密码不能为空");
                }
            }
        });

        mPsdView = mRootView.findViewById(R.id.password_view);
        mPsdView.setOnFinishListener(new PasswordInputView.OnFinishListener() {
            @Override
            public void setOnPasswordFinished(String text) {
                if (text.length() == 6) {
                    mPsd = text;
                } else {
                    mPsd = "";
                }
            }
        });

        mSurePsd = mRootView.findViewById(R.id.sure_password);
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
        if (response.code == 200) {
            SPManager.get().putString(C.HAS_THIRD_PASS, "1");
        }
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

    private void popSureCustom() {
        if (eduDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_sure_login_psd, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                    }
                }
            });

            eduDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        eduDialog.show();
    }
}
