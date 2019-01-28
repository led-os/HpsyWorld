package com.kuwai.ysy.module.circle.business.DyDetail;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.rayhahah.rbase.base.IRBaseView;

public class DyDetailContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(DyDetailBean dyDetailBean);

        void addFirCallBack(SimpleResponse response);

        void setGifts(GiftPopBean popBean);

        void rewardSuc(SimpleResponse response);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);

        void zanResult();

    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(String d_id, String uid);

        void addFirComment(String d_id, String uid, String text);

        void getAllGifts();

        void dyReward(String uid, String type, String tid,int gid, int nums);

        void dyDetailZan(String d_id, String uid, String other_id, int status);

    }
}
