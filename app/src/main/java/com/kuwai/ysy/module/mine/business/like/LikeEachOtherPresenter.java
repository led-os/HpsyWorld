package com.kuwai.ysy.module.mine.business.like;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LikeEachOtherPresenter extends RBasePresenter<LikeEachOtherContract.IHomeView> implements LikeEachOtherContract.IHomePresenter {
    private static final String TAG = "LikeEachOtherPresenter";

    public LikeEachOtherPresenter(LikeEachOtherContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid,int page) {
        addSubscription(MineApiFactory.getLikeEachOther(uid,page).subscribe(new Consumer<TodayBean>() {
            @Override
            public void accept(TodayBean todayBean) throws Exception {
                mView.setHomeData(todayBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
