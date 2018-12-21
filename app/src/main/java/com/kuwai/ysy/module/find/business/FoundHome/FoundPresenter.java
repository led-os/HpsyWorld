package com.kuwai.ysy.module.find.business.FoundHome;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class FoundPresenter extends RBasePresenter<FoundContract.IHomeView> implements FoundContract.IHomePresenter {
    private static final String TAG = "FoundPresenter";

    public FoundPresenter(FoundContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(Map<String, Object> map) {
        mView.showViewLoading();
        addSubscription(FoundApiFactory.getTeamList(map).subscribe(new Consumer<FoundBean>() {
            @Override
            public void accept(FoundBean foundBean) throws Exception {
                mView.setHomeData(foundBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
                mView.showViewError(throwable);
            }
        }));
    }
}
