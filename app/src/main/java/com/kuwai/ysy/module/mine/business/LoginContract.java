package com.kuwai.ysy.module.mine.business;

import com.rayhahah.rbase.base.IRBaseView;

public class LoginContract {

    public interface ILoginView extends IRBaseView {

        void loginSuccess();

        void loginFailed();
    }

    public interface ILoginPresenter {

        void login(String username, String password);
    }

}
