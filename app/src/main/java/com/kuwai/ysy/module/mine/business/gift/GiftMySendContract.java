package com.kuwai.ysy.module.mine.business.gift;

import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.rayhahah.rbase.base.IRBaseView;

public class GiftMySendContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(GiftAcceptBean giftAcceptBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(String uid, int page);
    }
}
