package com.kuwai.ysy.module.find.business.MyCommicDetail;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailContract;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MyCommisDetailPresenter extends RBasePresenter<MyCommisDetailContract.IHomeView> implements MyCommisDetailContract.IHomePresenter {
    private static final String TAG = "MyCommisDetailPresenter";

    public MyCommisDetailPresenter(MyCommisDetailContract.IHomeView view) {
        super(view);
    }


    @Override
    public void requestHomeData(int rid) {
        addSubscription(FoundApiFactory.getMyCommisDetail(rid).subscribe(new Consumer<MyCommisDetailBean>() {
            @Override
            public void accept(MyCommisDetailBean myCommisDetailBean) throws Exception {
                mView.setHomeData(myCommisDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getAgree(int rdid, int status) {
        addSubscription(FoundApiFactory.sendMeetAgree(rdid, status).subscribe(new Consumer<BlindBean>() {
            @Override
            public void accept(BlindBean blindBean) throws Exception {
                mView.setAgree(blindBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void deleteAppoint(int rid) {
        addSubscription(FoundApiFactory.deleteAppoint(rid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse myCommisDetailBean) throws Exception {
                mView.deleteResult(myCommisDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
    }

    @Override
    public void addChengyi(String uid, String rid, String money) {
        addSubscription(FoundApiFactory.addChengyi(uid, rid, money).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse blindBean) throws Exception {
                mView.addChengyiResult(blindBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
