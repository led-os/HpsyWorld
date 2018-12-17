package com.kuwai.ysy.module.mine.business.Integral;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.business.like.ILikeContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class IntegralDetailPresenter extends RBasePresenter<IntegralDetailContract.IHomeView> implements IntegralDetailContract.IHomePresenter {
    private static final String TAG = "IntegralDetailPresenter";

    public IntegralDetailPresenter(IntegralDetailContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid, int page) {
        addSubscription(MineApiFactory.getUserIntegralDetails(uid, page).subscribe(new Consumer<IntegralDetailBean>() {
            @Override
            public void accept(IntegralDetailBean integralDetailBean) throws Exception {
                mView.setHomeData(integralDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
