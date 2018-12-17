package com.kuwai.ysy.module.circle.business.DyDashang;

import android.util.Log;

import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.circle.business.DyDetail.DyDetailContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DyDashangPresenter extends RBasePresenter<DyDashangContract.IHomeView> implements DyDashangContract.IHomePresenter {
    private static final String TAG = "DyDetailPresenter";

    public DyDashangPresenter(DyDashangContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String did, int page) {
        mView.showViewLoading();
        addSubscription(CircleApiFactory.getDyRewardListData(did, page).subscribe(new Consumer<DyRewardlistBean>() {
            @Override
            public void accept(DyRewardlistBean dyRewardlistBean) throws Exception {
                mView.setHomeData(dyRewardlistBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void requestHoleData(String tid, int page) {
        addSubscription(CircleApiFactory.getHoleDetaiZanlData(tid, page).subscribe(new Consumer<DyRewardlistBean>() {
            @Override
            public void accept(DyRewardlistBean dyRewardlistBean) throws Exception {
                mView.setHomeData(dyRewardlistBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
