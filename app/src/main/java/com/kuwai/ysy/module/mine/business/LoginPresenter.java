package com.kuwai.ysy.module.mine.business;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.ESUser;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LoginPresenter extends RBasePresenter<LoginContract.ILoginView>
        implements LoginContract.ILoginPresenter {
    public LoginPresenter(LoginContract.ILoginView view) {
        super(view);
    }

    @Override
    public void login(final String username, String password) {
        /*addSubscription(MineApiFactory.getUserInfo(username, password).subscribe(new Consumer<ESUser>() {
            @Override
            public void accept(@NonNull ESUser esUser) throws Exception {
                if (esUser.getStatus() != C.RESPONSE_SUCCESS) {
                    mView.loginFailed();
                    return;
                }
                Observable.just(esUser).compose(RxSchedulers.<ESUser>ioMain()).subscribe(new Consumer<ESUser>() {
                    @Override
                    public void accept(@NonNull ESUser rowId) throws Exception {
                        if (rowId != null) {
                            mView.loginSuccess();
                        } else {
                            mView.loginFailed();
                        }
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.loginFailed();
            }
        }));*/

    }
}
