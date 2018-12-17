package com.kuwai.ysy.module.home.business.loginmoudle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.net.glide.ProgressInterceptor;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import retrofit2.http.PATCH;

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
                Map<String, String> params = new HashMap<>();
                params.put("phone", SPManager.get().getStringValue(C.REGIST_PHONE));
                params.put("check_code", SPManager.get().getStringValue(C.REGIST_CODE));
                params.put("city", SPManager.get().getStringValue(C.REGIST_CITY));
                params.put("gender", SPManager.get().getStringValue(C.REGIST_GENDER));
                params.put("birthday", SPManager.get().getStringValue(C.REGIST_BIRTHDAY));
                params.put("height", SPManager.get().getStringValue(C.REGIST_HEIGHT));
                params.put("annual_income", SPManager.get().getStringValue(C.REGIST_INCOME));
                params.put("education", SPManager.get().getStringValue(C.REGIST_EDUCATION));
                params.put("login_type", "1");
                params.put("type", "phone");

                params.put("nickname", SPManager.get().getStringValue(C.REGIST_NAME));
                params.put("avatar", SPManager.get().getStringValue(C.REGIST_AVATAR));
                params.put("password", SPManager.get().getStringValue(C.REGIST_PSD));
                params.put("longitude", SPManager.get().getStringValue(C.REGIST_LONGITUDE));
                params.put("latitude", SPManager.get().getStringValue(C.REGIST_LATITUDE));
                params.put("referee", mEtId.getText().toString());

                regist(params);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvPass = mRootView.findViewById(R.id.tv_pass);
        mTitle = mRootView.findViewById(R.id.title);
        mEtId = mRootView.findViewById(R.id.et_id);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnStart = mRootView.findViewById(R.id.btn_start);

        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void regist(Map<String, String> param) {
        addSubscription(HomeApiFactory.regist(param).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse codeBean) throws Exception {
                if (codeBean.code == 200) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
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
