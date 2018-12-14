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
public class PageDetailPresenter extends RBasePresenter<PageDetailContract.IHomeView> implements PageDetailContract.IHomePresenter {
    private static final String TAG ="FoundPresenter";
    public PageDetailPresenter(PageDetailContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid ,String otherid) {
        addSubscription(MineApiFactory.getOtherHomepageInfo(uid,otherid).subscribe(new Consumer<PersolHomePageBean>() {
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
