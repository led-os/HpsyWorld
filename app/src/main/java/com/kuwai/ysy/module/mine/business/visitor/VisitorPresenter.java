package com.kuwai.ysy.module.mine.business.visitor;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class VisitorPresenter extends RBasePresenter<VisitorContract.IHomeView> implements VisitorContract.IHomePresenter {
    private static final String TAG ="LookMePresenter";
    public VisitorPresenter(VisitorContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid ,int type) {
        addSubscription(MineApiFactory.getVisitor(uid,type).subscribe(new Consumer<VisitorBean>() {
            @Override
            public void accept(VisitorBean visitorBean) throws Exception {
                mView.setHomeData(visitorBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
