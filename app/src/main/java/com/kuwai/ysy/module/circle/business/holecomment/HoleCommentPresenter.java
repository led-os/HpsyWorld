package com.kuwai.ysy.module.circle.business.holecomment;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.HoleCommentListBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class HoleCommentPresenter extends RBasePresenter<HoleCommentContract.IPublishView> implements HoleCommentContract.IPublishPresenter {
    private static final String TAG = "DyDetailPresenter";

    public HoleCommentPresenter(HoleCommentContract.IPublishView view) {
        super(view);
    }


    @Override
    public void getCommentList(String d_id, String uid, int page) {
        addSubscription(CircleApiFactory.getHoleCommentListData(d_id, uid, page).subscribe(new Consumer<DyCommentListBean>() {
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
        addSubscription(CircleApiFactory.holeSecComment(d_clid, uid, String.valueOf(other_uid), text).subscribe(new Consumer<SimpleResponse>() {
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
        Map<String, Object> param = new HashMap<>();
        param.put("uid", uid);
        param.put("other_uid", other_uid);
        param.put("t_id", d_id);
        param.put("t_clid", d_clid);
        param.put("status", status);
        addSubscription(CircleApiFactory.holeLikeOrNot(param).subscribe(new Consumer<SimpleResponse>() {
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
