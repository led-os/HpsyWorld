package com.kuwai.ysy.module.home.business.loginmoudle.login;

import android.util.Log;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.business.DyDetail.DyDetailContract;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LoginPresenter extends RBasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {
    private static final String TAG = "DyDetailPresenter";

    public LoginPresenter(LoginContract.ILoginView view) {
        super(view);
    }

    @Override
    public void login(Map<String, String> param, final String type) {
        addSubscription(HomeApiFactory.login(param).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                mView.loginResult(loginBean,type);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }
}
