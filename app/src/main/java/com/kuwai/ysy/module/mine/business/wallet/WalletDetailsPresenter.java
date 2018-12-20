package com.kuwai.ysy.module.mine.business.wallet;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class WalletDetailsPresenter extends RBasePresenter<WalletDetailsContract.IHomeView> implements WalletDetailsContract.IHomePresenter {
    private static final String TAG = "MyWalletPresenter";

    public WalletDetailsPresenter(WalletDetailsContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid,int page) {
        addSubscription(MineApiFactory.getWalletDetails(uid,page).subscribe(new Consumer<WalletDetailsBean>() {
            @Override
            public void accept(WalletDetailsBean walletDetailsBean) throws Exception {
                mView.setHomeData(walletDetailsBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
