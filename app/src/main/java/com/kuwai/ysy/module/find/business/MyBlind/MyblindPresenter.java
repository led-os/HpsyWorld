package com.kuwai.ysy.module.find.business.MyBlind;

import android.util.Log;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.module.find.business.TuoDan.TuoDanContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MyblindPresenter extends RBasePresenter<MyblindContract.IHomeView> implements MyblindContract.IHomePresenter {
    private static final String TAG ="TuoDanPresenter";
    public MyblindPresenter(MyblindContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int page,int uid) {
        addSubscription(FoundApiFactory.getMyBlindList(page,uid).subscribe(new Consumer<MyBlindBean>() {
            @Override
            public void accept(MyBlindBean myBlindBean) throws Exception {
                mView.setHomeData(myBlindBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
