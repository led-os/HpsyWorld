package com.kuwai.ysy.module.find.business.MyPromise;

import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/5/17.
 */

public class MyPromisePresenter extends RBasePresenter<MyPromiseContract.MyCommisListView>
        implements MyPromiseContract.MyCommisListPresenter {
    public MyPromisePresenter(MyPromiseContract.MyCommisListView view) {
        super(view);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void getMyCommis(String uid, int page) {
        mView.showViewLoading();
        addSubscription(AppointApiFactory.getMyCommis(uid, page)
                .subscribe(new Consumer<MyCommis>() {
                    @Override
                    public void accept(@NonNull MyCommis appointMent) throws Exception {
                        mView.dismissLoading();
                        mView.getMyCommis(appointMent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }

    @Override
    public void getCancelApply(int rdid, int uid) {
        addSubscription(FoundApiFactory.sendCancelApply(rdid, uid)
                .subscribe(new Consumer<BlindBean>() {
                    @Override
                    public void accept(@NonNull BlindBean blindBean) throws Exception {
                        mView.sedCancelApply(blindBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }


}
