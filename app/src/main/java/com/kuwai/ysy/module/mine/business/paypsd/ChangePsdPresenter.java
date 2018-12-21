package com.kuwai.ysy.module.mine.business.paypsd;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.business.like.ILikeContract;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ChangePsdPresenter extends RBasePresenter<ChangePsdContract.IHomeView> implements ChangePsdContract.IHomePresenter {
    private static final String TAG = "ChangePsdPresenter";

    public ChangePsdPresenter(ChangePsdContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String uid, String psd, String newPsd, String reNewPsd, int type) {
        addSubscription(MineApiFactory.updatePasswordOrParmentPassword(uid, psd, newPsd, reNewPsd, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.setHomeData(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
