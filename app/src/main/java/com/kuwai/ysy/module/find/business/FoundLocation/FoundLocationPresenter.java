package com.kuwai.ysy.module.find.business.FoundLocation;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class FoundLocationPresenter extends RBasePresenter<FoundLocationContract.IHomeView> implements FoundLocationContract.IHomePresenter {
    private static final String TAG ="FoundLocationPresenter";
    public FoundLocationPresenter(FoundLocationContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData() {
        addSubscription(FoundApiFactory.getProvinceList().subscribe(new Consumer<ProvincesAndCityBean>() {
            @Override
            public void accept(ProvincesAndCityBean provincesAndCityBean) throws Exception {
                mView.setHomeData(provincesAndCityBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void requestNextData(String id) {
        addSubscription(FoundApiFactory.getLocalNextList(id).subscribe(new Consumer<LocalNextBean>() {
            @Override
            public void accept(LocalNextBean localNextBean) throws Exception {
                mView.setCityData(localNextBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void requestAreaData(String id) {
        addSubscription(FoundApiFactory.getLocalNextList(id).subscribe(new Consumer<LocalNextBean>() {
            @Override
            public void accept(LocalNextBean localNextBean) throws Exception {
                mView.setAreaData(localNextBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }


}
