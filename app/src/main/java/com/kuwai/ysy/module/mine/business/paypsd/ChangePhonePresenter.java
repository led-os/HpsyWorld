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
public class ChangePhonePresenter extends RBasePresenter<ChangePhoneContract.IHomeView> implements ChangePhoneContract.IHomePresenter {
    private static final String TAG = "ChangePhonePresenter";

    public ChangePhonePresenter(ChangePhoneContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid, String phone, String newPhone, String newVcode) {
        addSubscription(MineApiFactory.updateUserPhone(uid, phone, newPhone, newVcode).subscribe(new Consumer<SimpleResponse>() {
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
