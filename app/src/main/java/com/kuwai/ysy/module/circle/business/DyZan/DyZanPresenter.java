package com.kuwai.ysy.module.circle.business.DyZan;

import android.util.Log;

import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyLikeListBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.circle.business.DyDashang.DyDashangContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DyZanPresenter extends RBasePresenter<DyZanContract.IHomeView> implements DyZanContract.IHomePresenter {
    private static final String TAG = "DyDetailPresenter";

    public DyZanPresenter(DyZanContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String did, String uid, int page) {
        addSubscription(CircleApiFactory.getDyLikeListData(did, uid, page).subscribe(new Consumer<DyLikeListBean>() {
            @Override
            public void accept(DyLikeListBean dyLikeListBean) throws Exception {
                mView.setHomeData(dyLikeListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
