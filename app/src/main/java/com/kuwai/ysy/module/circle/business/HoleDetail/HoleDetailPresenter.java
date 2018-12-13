package com.kuwai.ysy.module.circle.business.HoleDetail;

import android.util.Log;

import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.circle.business.TreeHoleMain.TreeHoleMainContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class HoleDetailPresenter extends RBasePresenter<HoleDetailContract.IHomeView> implements HoleDetailContract.IHomePresenter {
    private static final String TAG = "HoleDetailPresenter";

    public HoleDetailPresenter(HoleDetailContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String tid, String uid) {
        addSubscription(CircleApiFactory.getHoleDetailData(tid, uid).subscribe(new Consumer<HoleDetailBean>() {
            @Override
            public void accept(HoleDetailBean holeDetailBean) throws Exception {
                mView.setHomeData(holeDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
