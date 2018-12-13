package com.kuwai.ysy.module.circle.business.DyDetail;

import android.util.Log;

import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DyDetailPresenter extends RBasePresenter<DyDetailContract.IHomeView> implements DyDetailContract.IHomePresenter {
    private static final String TAG = "DyDetailPresenter";

    public DyDetailPresenter(DyDetailContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String did, String uid) {
        addSubscription(CircleApiFactory.getDyDetailData(did, uid).subscribe(new Consumer<DyDetailBean>() {
            @Override
            public void accept(DyDetailBean dyDetailBean) throws Exception {
                mView.setHomeData(dyDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
