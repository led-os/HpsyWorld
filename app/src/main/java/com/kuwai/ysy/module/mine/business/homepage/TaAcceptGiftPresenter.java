package com.kuwai.ysy.module.mine.business.homepage;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.business.gift.GiftMySendContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class TaAcceptGiftPresenter extends RBasePresenter<TaAcceptGiftContract.IHomeView> implements TaAcceptGiftContract.IHomePresenter {
    private static final String TAG = "TaAcceptGiftPresenter";

    public TaAcceptGiftPresenter(TaAcceptGiftContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid, int page) {
        addSubscription(MineApiFactory.getTaGift(uid, page).subscribe(new Consumer<TaGiftBean>() {
            @Override
            public void accept(TaGiftBean giftBean) throws Exception {
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
