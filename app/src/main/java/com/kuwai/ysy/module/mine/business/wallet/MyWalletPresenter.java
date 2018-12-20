package com.kuwai.ysy.module.mine.business.wallet;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.business.visitor.LookMeContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MyWalletPresenter extends RBasePresenter<MyWalletContract.IHomeView> implements MyWalletContract.IHomePresenter {
    private static final String TAG = "MyWalletPresenter";

    public MyWalletPresenter(MyWalletContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getWallet(uid).subscribe(new Consumer<MyWalletBean>() {
            @Override
            public void accept(MyWalletBean walletBean) throws Exception {
                mView.setHomeData(walletBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
