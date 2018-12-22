package com.kuwai.ysy.module.mine.business.paypsd;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class SetPayPsdPresenter extends RBasePresenter<SetPayPsdContract.IHomeView> implements SetPayPsdContract.IHomePresenter {
    private static final String TAG = "ChangePhonePresenter";

    public SetPayPsdPresenter(SetPayPsdContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid, String psd, String pay_psd, String re_pay_psd) {
        addSubscription(MineApiFactory.getAddParmentPassword(uid, psd, pay_psd, re_pay_psd).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.setHomeData(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
