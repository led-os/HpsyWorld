package com.kuwai.ysy.module.find.business.appointment;

import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/5/17.
 */

public class MyAppointListPresenter extends RBasePresenter<AppointMyListContract.MyAppointListView>
        implements AppointMyListContract.MyAppointMentListPresenter {
    public MyAppointListPresenter(AppointMyListContract.MyAppointListView view) {
        super(view);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void getMyAppointMent(String uid, int page, int status) {
        mView.showViewLoading();
        addSubscription(AppointApiFactory.getMyAppoint(uid, page, status)
                .subscribe(new Consumer<MyAppointMent>() {
                    @Override
                    public void accept(@NonNull MyAppointMent appointMent) throws Exception {
                        mView.dismissLoading();
                        mView.getMyAppintMent(appointMent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }
}
