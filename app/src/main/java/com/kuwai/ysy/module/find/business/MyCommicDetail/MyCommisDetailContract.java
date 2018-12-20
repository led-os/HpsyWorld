package com.kuwai.ysy.module.find.business.MyCommicDetail;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.rayhahah.rbase.base.IRBaseView;

public class MyCommisDetailContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(MyCommisDetailBean myCommisDetailBean);

        void setAgree(BlindBean blindBean);
        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);

        void deleteResult(SimpleResponse response);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(int rid);

        void getAgree(int rdid ,int status);

        void deleteAppoint(int rid);
    }
}
