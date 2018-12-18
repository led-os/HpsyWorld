package com.kuwai.ysy.module.circle.business.HoleDetail;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.rayhahah.rbase.base.IRBaseView;

public class HoleDetailContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(HoleDetailBean holeDetailBean);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);

        void addFirCallBack(SimpleResponse response);

        void setGifts(GiftPopBean popBean);

        void rewardSuc();
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(String tid, String uid);

        void addFirComment(String d_id, String uid, String text);

        void getAllGifts();

        void dyReward(String uid, String type, String tid,int gid, int nums);

    }
}
