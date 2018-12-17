package com.kuwai.ysy.module.mine.business.question;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.module.mine.business.homepage.GiftBoxContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class AskPresenter extends RBasePresenter<AskContract.IHomeView> implements AskContract.IHomePresenter {
    private static final String TAG = "AskPresenter";

    public AskPresenter(AskContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getAskList(uid).subscribe(new Consumer<MyAskBean>() {
            @Override
            public void accept(MyAskBean askBean) throws Exception {
                mView.setHomeData(askBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void sendDelAsk(String uid, int pid) {
        addSubscription(MineApiFactory.getDelAsk(uid, pid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                mView.delAsk(simpleResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getUpdateProblem(String uid, int pid, String pro, String answer) {
        addSubscription(MineApiFactory.getUpdateProblem(uid, pid, pro, answer).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                mView.UpdateProblem(simpleResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getAddAsk(String uid, String pro) {
        addSubscription(MineApiFactory.getAddAsk(uid, pro).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                mView.setAddAsk(simpleResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
