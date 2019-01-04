package com.kuwai.ysy.module.circle.business.publishdy;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.HashMap;

import okhttp3.RequestBody;

public class PublishDyContract {

    public interface IPublishView extends IRBaseView {

        void setPublishCallBack(SimpleResponse dyDetailBean);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);

        void getTokenResult(StsBean stsBean,String type);
    }

    public interface IPublishPresenter {

        void publishDy(HashMap<String, RequestBody> map);

        void getStsToken(String uid,String token,String type);
    }
}
