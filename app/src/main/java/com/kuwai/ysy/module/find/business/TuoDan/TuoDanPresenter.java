package com.kuwai.ysy.module.find.business.TuoDan;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.module.find.business.FoundHome.FoundContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class TuoDanPresenter extends RBasePresenter<TuoDanContract.IHomeView> implements TuoDanContract.IHomePresenter {
    private static final String TAG ="TuoDanPresenter";
    public TuoDanPresenter(TuoDanContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int page) {
        addSubscription(FoundApiFactory.getTuoDanList(page).subscribe(new Consumer<TuoDanBean>() {
            @Override
            public void accept(TuoDanBean tuoDanBean) throws Exception {
                mView.setHomeData(tuoDanBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
