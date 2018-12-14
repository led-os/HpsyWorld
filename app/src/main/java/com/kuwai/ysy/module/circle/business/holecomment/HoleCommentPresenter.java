package com.kuwai.ysy.module.circle.business.holecomment;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.HoleCommentListBean;
import com.rayhahah.rbase.base.RBasePresenter;

import io.reactivex.functions.Consumer;

public class HoleCommentPresenter extends RBasePresenter<HoleCommentContract.IPublishView> implements HoleCommentContract.IPublishPresenter {
    private static final String TAG = "DyDetailPresenter";

    public HoleCommentPresenter(HoleCommentContract.IPublishView view) {
        super(view);
    }


    @Override
    public void getCommentList(String d_id, String uid, int page) {
        addSubscription(CircleApiFactory.getHoleCommentListData(d_id, uid, page).subscribe(new Consumer<HoleCommentListBean>() {
            @Override
            public void accept(HoleCommentListBean dyDetailBean) throws Exception {
                mView.setCommenList(dyDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }


    @Override
    public void addSecComment(String d_clid, String uid, String text, int other_uid) {
        addSubscription(CircleApiFactory.dySecComment(d_clid, uid,text,other_uid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                mView.addSecCallBack(dyDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
