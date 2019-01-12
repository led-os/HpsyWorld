package com.kuwai.ysy.module.find.business.CityMeet;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.FoundHome.FoundContract;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class CityMeetPresenter extends RBasePresenter<CityMeetContract.IHomeView> implements CityMeetContract.IHomePresenter {
    private static final String TAG = "CityMeetPresenter";

    public CityMeetPresenter(CityMeetContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(Map<String,Object> param) {
        addSubscription(FoundApiFactory.getCityMeetList(param).subscribe(new Consumer<CityMeetBean>() {
            @Override
            public void accept(CityMeetBean cityMeetBean) throws Exception {
                mView.setHomeData(cityMeetBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getMeetfilter() {
        addSubscription(FoundApiFactory.getMeetFilter().subscribe(new Consumer<MeetThemeBean>() {
            @Override
            public void accept(MeetThemeBean meetThemeBean) throws Exception {
                mView.setMeetfilter(meetThemeBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }



}
