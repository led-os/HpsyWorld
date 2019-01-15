package com.kuwai.ysy.module.mine.business.homepage;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.business.FoundHome.FoundContract;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class OtherHomepagePresenter extends RBasePresenter<OtherHomepageContract.IHomeView> implements OtherHomepageContract.IHomePresenter {
    private static final String TAG ="FoundPresenter";
    public OtherHomepagePresenter(OtherHomepageContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid ,String otherid) {
        addSubscription(MineApiFactory.getOtherHomepageInfo(uid,otherid).subscribe(new Consumer<PersolHomePageBean>() {
            @Override
            public void accept(PersolHomePageBean persolHomePageBean) throws Exception {
                mView.setHomeData(persolHomePageBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void like(String uid, String otherId, int type) {
        addSubscription(MineApiFactory.getUserLike(uid,otherId,type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.likeResult(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void online(String uid, String otherId) {
        addSubscription(MineApiFactory.setReminder(uid,otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.onlineResult(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void cancelRemind(String uid, String otherId) {
        addSubscription(MineApiFactory.setCancelReminder(uid,otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.cancelResult(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
