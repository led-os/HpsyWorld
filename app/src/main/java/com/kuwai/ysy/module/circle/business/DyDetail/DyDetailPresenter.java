package com.kuwai.ysy.module.circle.business.DyDetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainContract;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class DyDetailPresenter extends RBasePresenter<DyDetailContract.IHomeView> implements DyDetailContract.IHomePresenter {
    private static final String TAG = "DyDetailPresenter";

    public DyDetailPresenter(DyDetailContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(String did, String uid) {
        addSubscription(CircleApiFactory.getDyDetailData(did, uid).subscribe(new Consumer<DyDetailBean>() {
            @Override
            public void accept(DyDetailBean dyDetailBean) throws Exception {
                mView.setHomeData(dyDetailBean);
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
        addSubscription(CircleApiFactory.dyComment(d_id, uid, text).subscribe(new Consumer<SimpleResponse>() {
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
    public void dyDetailZan(String d_id, String uid, String other_id, int status) {
        addSubscription(CircleApiFactory.dyLikeOrNot(d_id, uid, other_id, status).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                if (simpleResponse.code == 200) {
                    mView.zanResult();
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

    @Override
    public void dyReward(String uid, String type, String tid,int gid, int nums) {
        addSubscription(CircleApiFactory.dynamicReward(uid, type, tid, gid,nums).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                    mView.rewardSuc(simpleResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

}
