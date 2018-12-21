package com.kuwai.ysy.module.find.business.CommisDetail;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.rayhahah.rbase.base.IRBaseView;

public class CommisDetailContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(CommisDetailBean commisDetailBean);

        void setApply(BlindBean blindBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
        void cancelResult(SimpleResponse response);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(int rid,String uid);

        void getApply(int rid,int uid,String text);

        void cancelApply(int rdid, int uid);

    }
}
