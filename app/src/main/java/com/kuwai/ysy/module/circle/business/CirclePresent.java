package com.kuwai.ysy.module.circle.business;

import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class CirclePresent extends RBasePresenter<CircleContract.ICircle>
        implements CircleContract.ICirclePresenter {
    public CirclePresent(CircleContract.ICircle view) {
        super(view);
    }

    @Override
    public void requestCircleData() {
        mView.showViewLoading();
        addSubscription(CircleApiFactory.getCircleList().subscribe(new Consumer<ArrayList<CategoryBean>>() {
            @Override
            public void accept(ArrayList<CategoryBean> categoryBean) throws Exception {
                mView.dismissLoading();
                mView.setCircleData(categoryBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showError(0, "");
            }
        }));
    }
}
