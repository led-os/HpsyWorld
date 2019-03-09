package com.kuwai.ysy.module.mine.business.homepage.otherpage;

import android.util.Log;

import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class PageDetail2Presenter extends RBasePresenter<PageDetail2Contract.IHomeView> implements PageDetail2Contract.IHomePresenter {
    private static final String TAG ="FoundPresenter";
    public PageDetail2Presenter(PageDetail2Contract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid ,String otherid) {
        addSubscription(MineApiFactory.getOtherHomepage2Info(uid,otherid).subscribe(new Consumer<PersolHome2PageBean>() {
            @Override
            public void accept(PersolHome2PageBean persolHomePageBean) throws Exception {
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
