package com.kuwai.ysy.module.mine.business.vip;

import android.util.Log;

import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.business.mine.MineContract;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.Map;

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
    public void getAliOrderInfo(Map<String,Object> param) {
        addSubscription(MineApiFactory.openVip(param).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse infoBean) throws Exception {
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
