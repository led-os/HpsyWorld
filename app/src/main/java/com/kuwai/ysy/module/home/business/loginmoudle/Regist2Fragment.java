package com.kuwai.ysy.module.home.business.loginmoudle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.kuwai.ysy.module.mine.MyPointActivity;
import com.kuwai.ysy.net.glide.ProgressInterceptor;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
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
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import static com.kuwai.ysy.app.C.MSG_LOGIN;

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

    private boolean isSanFang = false;

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
                if (isSanFang) {
                    //三方登录验证是否已完善过信息
                    DialogUtil.showLoadingDialog(getActivity(),"",getResources().getColor(R.color.theme));
                    String type = SPManager.get().getStringValue(C.SAN_FANG);
                    String id = SPManager.get().getStringValue(C.SAN_FANG_ID);
                    HashMap<String, String> param = new HashMap<>();
                    param.put("phone", mPhone);
                    param.put("check_code", mEtCode.getText().toString()); //1代表android
                    param.put("type", type);
                    param.put(type, id);
                    codeAuth(param);
                } else {
                    if (Utils.isNullString(mEtCode.getText().toString())) {
                        ToastUtils.showShort("请输入验证码", getActivity());
                    } else if (!code.equals(mEtCode.getText().toString())) {
                        ToastUtils.showShort("验证码错误", getActivity());
                    } else {
                        SPManager.get().putString(C.REGIST_CODE, mEtCode.getText().toString());
                        start(Regist3Fragment.newInstance());
                    }
                }

                break;
            case R.id.tv_count_down:
                mTvCountDown.start();
                if (isSanFang) {
                    getCode(mPhone, C.CODE_SANFANG);
                } else {
                    getCode(mPhone, C.CODE_REGIST);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPhone = getArguments().getString("phone");
        if (!Utils.isNullString(SPManager.get().getStringValue(C.SAN_FANG))) {
            isSanFang = true;
        }

        mTvRegistTitle = mRootView.findViewById(R.id.tv_regist_title);
        mTitle = mRootView.findViewById(R.id.title);
        mCodeInfo = mRootView.findViewById(R.id.code_info);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mEtCode.setFocusable(true);
        mEtCode.setFocusableInTouchMode(true);
        mEtCode.requestFocus();
        mTvCountDown = mRootView.findViewById(R.id.tv_count_down);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mCodeInfo.setText("短信验证码已发送至" + StringUtils.hideMobilePhone4(mPhone));
        mBtnNext.setOnClickListener(this);
        mTvCountDown.start();
        if (isSanFang) {
            getCode(mPhone, C.CODE_SANFANG);
        } else {
            getCode(mPhone, C.CODE_REGIST);
        }
        mTvCountDown.setOnClickListener(this);

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Utils.isNullString(s.toString())) {
                    mBtnNext.setEnabled(true);
                    mBtnNext.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtnNext.setEnabled(false);
                    mBtnNext.setTextColor(getResources().getColor(R.color.gray_7b));
                }
            }
        });
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

    public void codeAuth(Map<String, String> param) {
        addSubscription(HomeApiFactory.codeAuth(param).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                DialogUtil.dismissDialog(true);
                if (loginBean.getCode() == 200) {
                    SPManager.get().putString("uid", String.valueOf(loginBean.getData().getUid()));
                    SPManager.get().putString("rongyun_token", loginBean.getData().getRongyun_token());
                    SPManager.get().putString("nickname", loginBean.getData().getNickname());
                    SPManager.get().putString("phone_", loginBean.getData().getPhone());
                    SPManager.get().putString("sex_", String.valueOf(loginBean.getData().getGender()));
                    SPManager.get().putString("icon", loginBean.getData().getAvatar());
                    SPManager.get().putString("token", loginBean.getData().getToken());
                    SPManager.get().putString(C.HAS_THIRD_PASS, String.valueOf(loginBean.getData().getPayment()));
                    connectRongYun(loginBean.getData().getRongyun_token(),loginBean);
                    EventBusUtil.sendEvent(new MessageEvent(MSG_LOGIN));
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
                } else if (loginBean.getCode() == 203) {
                    SPManager.get().putString(C.REGIST_CODE, mEtCode.getText().toString());
                    start(Regist3Fragment.newInstance());
                } else {
                    ToastUtils.showShort(loginBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
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
                //RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE, "237", "测试");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("xxx", "onTokenIncorrect: ");
            }
        });
    }
}
