package com.kuwai.ysy.module.find.business.mycommis;

import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/5/17.
 */

public class MyCommisListPresenter extends RBasePresenter<MyCommisListContract.MyCommisListView>
        implements MyCommisListContract.MyCommisListPresenter {
    public MyCommisListPresenter(MyCommisListContract.MyCommisListView view) {
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
}
