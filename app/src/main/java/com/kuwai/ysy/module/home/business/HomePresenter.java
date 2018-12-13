package com.kuwai.ysy.module.home.business;

import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class HomePresenter extends RBasePresenter<HomeContract.IHomeView>
        implements HomeContract.IHomePresenter {
    public HomePresenter(HomeContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(int num) {
        addSubscription(HomeApiFactory.getTeamList(String.valueOf(num)).subscribe(new Consumer<HomeBean>() {
            @Override
            public void accept(HomeBean homeBean) throws Exception {
                mView.setHomeData(homeBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }
}
