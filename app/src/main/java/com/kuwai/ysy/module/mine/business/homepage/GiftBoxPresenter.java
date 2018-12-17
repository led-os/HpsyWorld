package com.kuwai.ysy.module.mine.business.homepage;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class GiftBoxPresenter extends RBasePresenter<GiftBoxContract.IHomeView> implements GiftBoxContract.IHomePresenter {
    private static final String TAG = "GiftBoxPresenter";

    public GiftBoxPresenter(GiftBoxContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getGiftBox(uid).subscribe(new Consumer<GiftBoxBean>() {
            @Override
            public void accept(GiftBoxBean giftBean) throws Exception {
                mView.setHomeData(giftBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
