package com.kuwai.ysy.module.circle.business.TreeHoleMain;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class TreeHoleMainPresenter extends RBasePresenter<TreeHoleMainContract.IHomeView> implements TreeHoleMainContract.IHomePresenter {
    private static final String TAG = "TreeHoleMainPresenter";

    public TreeHoleMainPresenter(TreeHoleMainContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int page, String uid) {
        addSubscription(CircleApiFactory.getHoleMainListData(page, uid).subscribe(new Consumer<HoleMainListBean>() {
            @Override
            public void accept(HoleMainListBean holeMainListBean) throws Exception {
                mView.setHomeData(holeMainListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void requestMore(int page, String uid) {
        addSubscription(CircleApiFactory.getHoleMainListData(page, uid).subscribe(new Consumer<HoleMainListBean>() {
            @Override
            public void accept(HoleMainListBean holeMainListBean) throws Exception {
                mView.setMoreData(holeMainListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
