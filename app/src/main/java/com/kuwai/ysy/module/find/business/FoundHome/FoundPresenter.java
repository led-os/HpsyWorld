package com.kuwai.ysy.module.find.business.FoundHome;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.FindHomeBean;
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
        addSubscription(Appoint2ApiFactory.getFindHome(map).subscribe(new Consumer<FindHomeBean>() {
            @Override
            public void accept(FindHomeBean foundBean) throws Exception {
                mView.setHomeData(foundBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
                //mView.showViewError(throwable);
            }
        }));
    }
}
