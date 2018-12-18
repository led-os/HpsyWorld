package com.kuwai.ysy.module.find.business.CommisDetail;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
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
public class CommisDetailPresenter extends RBasePresenter<CommisDetailContract.IHomeView> implements CommisDetailContract.IHomePresenter {
    private static final String TAG ="CommisDetailPresenter";
    public CommisDetailPresenter(CommisDetailContract.IHomeView view) {
        super(view);
    }


    @Override
    public void requestHomeData(int rid,String uid) {
        addSubscription(FoundApiFactory.getCommisDetail(rid,uid).subscribe(new Consumer<CommisDetailBean>() {
            @Override
            public void accept(CommisDetailBean commisDetailBean) throws Exception {
                mView.setHomeData(commisDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    public void getApply(int rid, int uid, String text) {
        addSubscription(FoundApiFactory.sendMeetApply(rid, uid, text).subscribe(new Consumer<BlindBean>() {
            @Override
            public void accept(BlindBean blindBean) throws Exception {
                mView.setApply(blindBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
