package com.kuwai.ysy.module.mine.business.mine;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.CityMeet.CityMeetContract;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MinePresenter extends RBasePresenter<MineContract.IMineView> implements MineContract.IUserPresenter {
    private static final String TAG = "CityMeetPresenter";

    public MinePresenter(MineContract.IMineView view) {
        super(view);
    }


    @Override
    public void requestUserData(String uid) {
        addSubscription(MineApiFactory.getUserInfo(uid).subscribe(new Consumer<UserInfo>() {
            @Override
            public void accept(UserInfo cityMeetBean) throws Exception {
                mView.setUserData(cityMeetBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
