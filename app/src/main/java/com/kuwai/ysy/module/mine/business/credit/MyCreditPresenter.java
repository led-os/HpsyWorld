package com.kuwai.ysy.module.mine.business.credit;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.business.Integral.IntegralDetailContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MyCreditPresenter extends RBasePresenter<MyCreditContract.IHomeView> implements MyCreditContract.IHomePresenter {
    private static final String TAG = "MyCreditPresenter";

    public MyCreditPresenter(MyCreditContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getMyAuthenticationList(uid).subscribe(new Consumer<CreditBean>() {
            @Override
            public void accept(CreditBean creditBean) throws Exception {
                mView.setHomeData(creditBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
