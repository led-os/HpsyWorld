package com.kuwai.ysy.module.find.business.theme;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/5/17.
 */

public class ThemeListPresenter extends RBasePresenter<ThemeListContract.ThemeListView>
        implements ThemeListContract.ThemeListPresenter {
    public ThemeListPresenter(ThemeListContract.ThemeListView view) {
        super(view);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void getAllTheme(String uid) {
        mView.showViewLoading();
        addSubscription(AppointApiFactory.getAllThemes(uid)
                .subscribe(new Consumer<DateTheme>() {
                    @Override
                    public void accept(@NonNull DateTheme dateTheme) throws Exception {
                        mView.dismissLoading();
                        mView.getAllThemes(dateTheme);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }

    @Override
    public void delCustomTheme(String uid, int sid) {
        addSubscription(AppointApiFactory.delCustomTheme(uid, sid)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        //mView.dismissLoading();
                        //mView.getAllThemes(dateTheme);
                        if (dateTheme.code == 200) {
                            mView.delSuccess();
                        }else{
                            ToastUtils.showShort("删除失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }
}
