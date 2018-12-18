package com.kuwai.ysy.module.circle.business.dongtai;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.CityMeet.CityMeetContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DongtaiMainPresenter extends RBasePresenter<DongtaiMainContract.IHomeView> implements DongtaiMainContract.IHomePresenter {
    private static final String TAG = "DongtaiMainPresenter";

    public DongtaiMainPresenter(DongtaiMainContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int page, String uid) {
        addSubscription(CircleApiFactory.getDyMainListData(page, uid).subscribe(new Consumer<DyMainListBean>() {
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
    public void dyDetelt(String did, String uid) {
        addSubscription(CircleApiFactory.deleteDy(did, uid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                mView.deteleDy(simpleResponse);
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
        addSubscription(CircleApiFactory.dyLikeOrNot(uid, otherid, did, status).subscribe(new Consumer<SimpleResponse>() {
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
    public void requestFriendData(int page, String uid) {
        addSubscription(CircleApiFactory.getDyMyFriendListData(page, uid).subscribe(new Consumer<DyMainListBean>() {
            @Override
            public void accept(DyMainListBean dyMainListBean) throws Exception {
                mView.setFriendData(dyMainListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
