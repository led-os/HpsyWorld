package com.kuwai.ysy.module.mine.business.like;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.LikeParentBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ILikePresenter extends RBasePresenter<ILikeContract.IHomeView> implements ILikeContract.IHomePresenter {
    private static final String TAG = "ILikePresenter";

    public ILikePresenter(ILikeContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getILike(uid).subscribe(new Consumer<LikeParentBean>() {
            @Override
            public void accept(LikeParentBean giftBean) throws Exception {
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
