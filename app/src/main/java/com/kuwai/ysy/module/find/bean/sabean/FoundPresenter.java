package com.kuwai.ysy.module.find.bean.sabean;

import android.util.Log;

import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class FoundPresenter extends RBasePresenter<FoundContract.IHomeView> implements FoundContract.IHomePresenter {
    private static final String TAG ="FoundPresenter";
    public FoundPresenter(FoundContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData() {
        addSubscription(FoundApiFactory.getTeamList().subscribe(new Consumer<FoundBean>() {
            @Override
            public void accept(FoundBean foundBean) throws Exception {
                mView.setHomeData(foundBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
