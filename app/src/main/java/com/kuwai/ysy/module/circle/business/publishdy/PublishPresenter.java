package com.kuwai.ysy.module.circle.business.publishdy;

import android.util.Log;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.business.DyDetail.DyDetailContract;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.HashMap;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class PublishPresenter extends RBasePresenter<PublishDyContract.IPublishView> implements PublishDyContract.IPublishPresenter {
    private static final String TAG = "DyDetailPresenter";

    public PublishPresenter(PublishDyContract.IPublishView view) {
        super(view);
    }


    @Override
    public void publishDy(HashMap<String, RequestBody> map) {

        addSubscription(CircleApiFactory.publishDy(map).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                UploadHelper.getInstance().clear();
                mView.setPublishCallBack(dyDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void getStsToken(String uid, String token, final String type) {
        addSubscription(CircleApiFactory.getVideoSts(uid,token).subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                mView.getTokenResult(stsBean,type);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
