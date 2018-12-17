package com.kuwai.ysy.module.mine.business.homepage;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MineHomepagePresenter extends RBasePresenter<MineHomepageContract.IHomeView> implements MineHomepageContract.IHomePresenter {
    private static final String TAG ="FoundPresenter";
    public MineHomepagePresenter(MineHomepageContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getUsetInfoMine(uid).subscribe(new Consumer<PersolHomePageBean>() {
            @Override
            public void accept(PersolHomePageBean persolHomePageBean) throws Exception {
                mView.setHomeData(persolHomePageBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
