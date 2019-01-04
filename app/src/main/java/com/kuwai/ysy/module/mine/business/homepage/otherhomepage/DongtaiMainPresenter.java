package com.kuwai.ysy.module.mine.business.homepage.otherhomepage;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DongtaiMainPresenter extends RBasePresenter<DongtaiOtherContract.IHomeView> implements DongtaiOtherContract.IHomePresenter {
    private static final String TAG = "DongtaiMainPresenter";

    public DongtaiMainPresenter(DongtaiOtherContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalDynamic(uid,page).subscribe(new Consumer<DyMainListBean>() {
            @Override
            public void accept(DyMainListBean dyMainListBean) throws Exception {
                mView.setHomeData(dyMainListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void dyListZan(String did, String uid, String otherid, int status) {
        addSubscription(CircleApiFactory.dyLikeOrNot(did, uid, otherid, status).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                mView.dyListZan(simpleResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void requestMore(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalDynamic(uid,page).subscribe(new Consumer<DyMainListBean>() {
            @Override
            public void accept(DyMainListBean dyMainListBean) throws Exception {
                mView.setMoreData(dyMainListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
