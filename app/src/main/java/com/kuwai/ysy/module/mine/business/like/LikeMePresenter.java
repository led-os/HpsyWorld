package com.kuwai.ysy.module.mine.business.like;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.business.homepage.TaAcceptGiftContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LikeMePresenter extends RBasePresenter<LikeMeContract.IHomeView> implements LikeMeContract.IHomePresenter {
    private static final String TAG = "LikeMePresenter";

    public LikeMePresenter(LikeMeContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getLikeMe(uid).subscribe(new Consumer<VisitorBean>() {
            @Override
            public void accept(VisitorBean giftBean) throws Exception {
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
