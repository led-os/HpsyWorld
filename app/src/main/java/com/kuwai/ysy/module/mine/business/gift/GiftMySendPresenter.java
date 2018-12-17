package com.kuwai.ysy.module.mine.business.gift;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class GiftMySendPresenter extends RBasePresenter<GiftMySendContract.IHomeView> implements GiftMySendContract.IHomePresenter {
    private static final String TAG ="GiftMySendPresenter";
    public GiftMySendPresenter(GiftMySendContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid ,int page) {
        addSubscription(MineApiFactory.getGiftSend(uid,page).subscribe(new Consumer<GiftAcceptBean>() {
            @Override
            public void accept(GiftAcceptBean giftAcceptBean) throws Exception {
                mView.setHomeData(giftAcceptBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
