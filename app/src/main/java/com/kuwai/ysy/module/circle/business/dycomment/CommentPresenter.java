package com.kuwai.ysy.module.circle.business.dycomment;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.HashMap;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class CommentPresenter extends RBasePresenter<CommentContract.IPublishView> implements CommentContract.IPublishPresenter {
    private static final String TAG = "DyDetailPresenter";

    public CommentPresenter(CommentContract.IPublishView view) {
        super(view);
    }


    @Override
    public void getCommentList(String d_id, String uid, int page) {
        addSubscription(CircleApiFactory.getDyCommentListData(d_id, uid, page).subscribe(new Consumer<DyCommentListBean>() {
            @Override
            public void accept(DyCommentListBean dyDetailBean) throws Exception {
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
        addSubscription(CircleApiFactory.dySecComment(d_clid, uid, text, other_uid).subscribe(new Consumer<SimpleResponse>() {
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

    @Override
    public void commenZan(String d_id, String uid, int status, int d_clid, int other_uid) {
        addSubscription(CircleApiFactory.dyLikeOrNot(d_id, uid, status, d_clid, other_uid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                mView.commantZanResult();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
