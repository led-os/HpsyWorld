package com.kuwai.ysy.module.home.business.loginmoudle.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.VideohomeActivity;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.home.business.loginmoudle.ChongzhiPsdFragment;
import com.kuwai.ysy.module.home.business.loginmoudle.Regist1Fragment;
import com.kuwai.ysy.module.home.business.loginmoudle.SendCodeFragment;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import static com.kuwai.ysy.app.C.MSG_LOGIN;

public class LoginFragment extends BaseFragment<LoginPresenter> implements View.OnClickListener, LoginContract.ILoginView {

    private ImageView mImgClose;
    private TextView mTvRegist;
    private TextView mTitle;
    private TextView mTvTel;
    private MyEditText mEtTel;
    private View mLine1;
    private TextView mTvCode;
    private ImageView mImgEye;
    private MyEditText mEtCode;
    private View mLine2;
    private TextView mTvForget;
    private TextView mTvLoginByCode;
    private SuperButton mBtnLogin;
    private ImageView mImgQq;
    private ImageView mImgWechat;
    private ImageView mImgSina;
    private TextView mTvThird;

    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_by_code:
                start(SendCodeFragment.newInstance());
                break;
            case R.id.img_eye:
                if (isHideFirst == true) {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_ce);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    mEtCode.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    mImgEye.setImageResource(R.drawable.sl_login_ic_blink);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    mEtCode.setTransformationMethod(method);
                    isHideFirst = true;

                }
                // 光标的位置
                int index = mEtCode.getText().toString().length();
                mEtCode.setSelection(index);
                break;
            case R.id.tv_forget:
                start(ChongzhiPsdFragment.newInstance());
                break;
            case R.id.tv_regist:
                SPManager.get().putString(C.SAN_FANG, "");
                start(Regist1Fragment.newInstance());
                break;
            case R.id.btn_login:
                if (checkNull()) {
                    DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
                    HashMap<String, String> param = new HashMap<>();
                    param.put("type", C.LOGIN_PHONE);
                    param.put("login_type", "1"); //1代表android
                    param.put("phone", mEtTel.getText().toString());
                    param.put("password", Utils.encrypt32(mEtCode.getText().toString()));
                    mPresenter.login(param, "");
                }
                break;
            case R.id.img_qq:
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.img_sina:
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.img_wechat:
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.img_close:
                getActivity().finish();
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //Toast.makeText(mContext, "开始", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //Toast.makeText(mContext, "成功了" + "后台id：" + data.get("uid") + "名字" + data.get("name") + "头像" + data.get("iconurl"), Toast.LENGTH_LONG).show();
            DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
            HashMap<String, String> param = new HashMap<>();
            String type = "";
            SPManager.get().putString(C.SAN_FANG_ID, data.get("uid"));
            Log.e("e", SPManager.get().getStringValue(C.SAN_FANG_ID));
            if (SHARE_MEDIA.QQ.equals(platform)) {
                type = C.LOGIN_QQ;
                param.put("type", C.LOGIN_QQ);
                param.put(C.LOGIN_QQ, data.get("uid"));
            } else if (SHARE_MEDIA.SINA.equals(platform)) {
                type = C.LOGIN_SINA;
                param.put("type", C.LOGIN_SINA);
                param.put(C.LOGIN_SINA, data.get("uid"));
            } else if (SHARE_MEDIA.WEIXIN.equals(platform)) {
                type = C.LOGIN_WECHAT;
                param.put("type", C.LOGIN_WECHAT);
                param.put(C.LOGIN_WECHAT, data.get("uid"));
            }
            param.put("login_type", "1"); //1代表android
            mPresenter.login(param, type);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (SHARE_MEDIA.QQ.equals(platform)) {
                UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.QQ, null);
            } else if (SHARE_MEDIA.SINA.equals(platform)) {
                UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.SINA, null);
            } else if (SHARE_MEDIA.WEIXIN.equals(platform)) {
                UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN, null);
            }
            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private boolean checkNull() {
        if (Utils.isNullString(mEtTel.getText().toString())) {
            ToastUtils.showShort("请输入手机号");
            return false;
        } else if (Utils.isNullString(mEtCode.getText().toString())) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgClose = mRootView.findViewById(R.id.img_close);
        mTvRegist = mRootView.findViewById(R.id.tv_regist);
        mTitle = mRootView.findViewById(R.id.title);
        mTvTel = mRootView.findViewById(R.id.tv_tel);
        mEtTel = mRootView.findViewById(R.id.et_tel);
        mLine1 = mRootView.findViewById(R.id.line1);
        mTvCode = mRootView.findViewById(R.id.tv_code);
        mImgEye = mRootView.findViewById(R.id.img_eye);
        mEtCode = mRootView.findViewById(R.id.et_code);
        mLine2 = mRootView.findViewById(R.id.line2);
        mTvForget = mRootView.findViewById(R.id.tv_forget);
        mTvLoginByCode = mRootView.findViewById(R.id.tv_login_by_code);
        mBtnLogin = mRootView.findViewById(R.id.btn_login);
        mImgQq = mRootView.findViewById(R.id.img_qq);
        mImgWechat = mRootView.findViewById(R.id.img_wechat);
        mImgSina = mRootView.findViewById(R.id.img_sina);
        mTvThird = mRootView.findViewById(R.id.tv_third);

        mTvLoginByCode.setOnClickListener(this);
        mImgEye.setOnClickListener(this);
        mTvForget.setOnClickListener(this);
        mTvRegist.setOnClickListener(this);
        mImgClose.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mImgQq.setOnClickListener(this);
        mImgWechat.setOnClickListener(this);
        mImgSina.setOnClickListener(this);

        mEtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeState(s.toString(), 1);
            }
        });

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeState(s.toString(), 2);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void changeState(String s, int type) {
        if (!Utils.isNullString(type == 1 ? mEtCode.getText().toString() : mEtTel.getText().toString()) && !Utils.isNullString(s)) {
            mBtnLogin.setEnabled(true);
            mBtnLogin.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtnLogin.setEnabled(false);
            mBtnLogin.setTextColor(getResources().getColor(R.color.gray_7b));
        }
    }

    @Override
    public void loginResult(LoginBean loginBean, String type) {
        DialogUtil.dismissDialog(true);
        if (loginBean.getCode() == 200) {
            SPManager.get().putString(C.SAN_FANG, type);
            SPManager.get().putString("uid", String.valueOf(loginBean.getData().getUid()));
            SPManager.get().putString("nickname", loginBean.getData().getNickname());
            SPManager.get().putString("phone_", loginBean.getData().getPhone());
            SPManager.get().putString("isvip_", String.valueOf(loginBean.getData().getIs_vip()));
            SPManager.get().putString("password_", Utils.encrypt32(mEtCode.getText().toString()));
            SPManager.get().putString("icon", loginBean.getData().getAvatar());
            SPManager.get().putString("sex_", String.valueOf(loginBean.getData().getGender()));
            SPManager.get().putString(C.HAS_THIRD_PASS, String.valueOf(loginBean.getData().getPayment()));
            SPManager.get().putString("rongyun_token", loginBean.getData().getRongyun_token());
            SPManager.get().putString("token", loginBean.getData().getToken());
            SPManager.get().putString("cityName", "苏州");
            SPManager.get().putString("cityId", "114");
            connectRongYun(loginBean.getData().getRongyun_token(), loginBean);
            EventBusUtil.sendEvent(new MessageEvent(MSG_LOGIN));
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        } else if (loginBean.getCode() == 201) {
            SPManager.get().putString(C.SAN_FANG, type);
            start(Regist1Fragment.newInstance());
        } else {
            ToastUtils.showShort(loginBean.getMsg());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
