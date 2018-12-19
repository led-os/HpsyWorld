package com.kuwai.ysy.module.mine.business.vip;

import android.util.Log;

import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.business.mine.MineContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class VipHuangjinPresenter extends RBasePresenter<VipHuangjinContract.IMineView> implements VipHuangjinContract.IUserPresenter {
    private static final String TAG = "CityMeetPresenter";

    public VipHuangjinPresenter(VipHuangjinContract.IMineView view) {
        super(view);
    }

    @Override
    public void requestUserData(String uid) {

    }

    @Override
    public void getAliOrderInfo() {
        addSubscription(MineApiFactory.getAliOrderInfo().subscribe(new Consumer<AliOrderInfoBean>() {
            @Override
            public void accept(AliOrderInfoBean infoBean) throws Exception {
                mView.setAliOrderInfo(infoBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
