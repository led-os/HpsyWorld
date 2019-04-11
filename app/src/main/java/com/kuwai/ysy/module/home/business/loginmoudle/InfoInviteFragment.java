package com.kuwai.ysy.module.home.business.loginmoudle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.kuwai.ysy.net.glide.ProgressInterceptor;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.SPUtils;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imlib.RongIMClient;
import okhttp3.RequestBody;


public class InfoInviteFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvPass;
    private TextView mTitle;
    private EditText mEtId;
    private View mLine1;
    private SuperButton mBtnStart;

    public static InfoInviteFragment newInstance() {
        Bundle args = new Bundle();
        InfoInviteFragment fragment = new InfoInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_invite;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                toRegist();
                break;
            case R.id.tv_pass:
                toRegist();
                break;
        }
    }

    private void toRegist() {
        DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("phone", SPManager.get().getStringValue(C.REGIST_PHONE));
        helper.addParameter("check_code", SPManager.get().getStringValue(C.REGIST_CODE));
        helper.addParameter("city", SPManager.get().getStringValue(C.REGIST_CITY));
        helper.addParameter("gender", SPManager.get().getStringValue(C.REGIST_GENDER));
        helper.addParameter("birthday", SPManager.get().getStringValue(C.REGIST_BIRTHDAY));
        helper.addParameter("height", SPManager.get().getStringValue(C.REGIST_HEIGHT));
        helper.addParameter("annual_income", SPManager.get().getStringValue(C.REGIST_INCOME));
        helper.addParameter("education", SPManager.get().getStringValue(C.REGIST_EDUCATION));
        helper.addParameter("login_type", "1");
        if (!Utils.isNullString(SPManager.get().getStringValue(C.SAN_FANG))) {
            helper.addParameter("type", SPManager.get().getStringValue(C.SAN_FANG));
        } else {
            helper.addParameter("type", "phone");
        }
        String type = SPManager.get().getStringValue(C.SAN_FANG);
        String id = SPManager.get().getStringValue(C.SAN_FANG_ID);
        if (!Utils.isNullString(SPManager.get().getStringValue(C.SAN_FANG_ID))) {
            helper.addParameter(type, id);
        }
        if (!Utils.isNullString(SPManager.get().getStringValue(C.REGIST_NAME))) {
            helper.addParameter("nickname", SPManager.get().getStringValue(C.REGIST_NAME));
        }
        helper.addParameter("password", SPManager.get().getStringValue(C.REGIST_PSD));
        if (!Utils.isNullString(SPManager.get().getStringValue(C.REGIST_LONGITUDE))) {
            helper.addParameter("longitude", SPManager.get().getStringValue(C.REGIST_LONGITUDE));
            helper.addParameter("latitude", SPManager.get().getStringValue(C.REGIST_LATITUDE));
        }
        if (!Utils.isNullString(mEtId.getText().toString())) {
            helper.addParameter("referee", mEtId.getText().toString());
        }
        if (!Utils.isNullString(SPManager.get().getStringValue(C.REGIST_AVATAR))) {
            File file = new File(SPManager.get().getStringValue(C.REGIST_AVATAR));
            helper.addParameter("file" + 0 + "\";filename=\"" + file.getName(), file);
        }

        regist(helper.builder());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvPass = mRootView.findViewById(R.id.tv_pass);
        mTitle = mRootView.findViewById(R.id.title);
        mEtId = mRootView.findViewById(R.id.et_id);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnStart = mRootView.findViewById(R.id.btn_start);

        mBtnStart.setOnClickListener(this);
        mTvPass.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void regist(Map<String, RequestBody> param) {
        addSubscription(HomeApiFactory.regist(param).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                DialogUtil.dismissDialog(true);
                /*if (codeBean.code == 200) {
                    ToastUtils.showShort("注册成功");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    ToastUtils.showShort(codeBean.msg);
                }*/

                if (loginBean.getCode() == 200) {
                    SPUtils.savaLogin(loginBean);
                    SPManager.get().putString(C.SAN_FANG, SPManager.get().getStringValue(C.SAN_FANG));
                    SPManager.get().putString("password_", SPManager.get().getStringValue(C.REGIST_PSD));
                    SPManager.get().putString("cityName", "苏州");
                    SPManager.get().putString("cityId", "114");
                    connectRongYun(loginBean.getData().getRongyun_token(), loginBean);
                    ToastUtils.showShort("注册成功");
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
                } else {
                    ToastUtils.showShort(loginBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    private void connectRongYun(String token, final LoginBean loginBean) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("xxx", "onTokenIncorrect: ");
            }
        });
    }
}
