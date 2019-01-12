package com.kuwai.ysy.module.circle.business.HoleDetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.circle.business.TreeHoleMain.TreeHoleMainContract;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

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

    @Override
    public void addFirComment(String d_id, String uid, String text) {
        addSubscription(CircleApiFactory.holeComment(d_id, uid, text).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                mView.addFirCallBack(dyDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getAllGifts() {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        mView.setGifts(dateTheme);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }

    @Override
    public void dyReward(String uid, String type, String tid, int gid, int nums) {
        addSubscription(CircleApiFactory.dynamicReward(uid, type, tid, gid,nums).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                if (simpleResponse.code == 200) {
                    mView.rewardSuc();
                } else {
                    ToastUtils.showShort(simpleResponse.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
