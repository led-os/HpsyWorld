package com.kuwai.ysy.module.home.business.loginmoudle.login;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.Map;

public class LoginContract {

    public interface ILoginView extends IRBaseView {

        void loginResult(LoginBean loginBean,String type);
        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface ILoginPresenter {

        void login(Map<String,String> param,String type);

    }
}
