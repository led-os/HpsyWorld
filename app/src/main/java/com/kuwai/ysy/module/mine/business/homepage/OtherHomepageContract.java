package com.kuwai.ysy.module.mine.business.homepage;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.rayhahah.rbase.base.IRBaseView;

public class OtherHomepageContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(PersolHomePageBean persolHomePageBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
        void likeResult(SimpleResponse response);
        void onlineResult(SimpleResponse response);
        void cancelResult(SimpleResponse response);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(String uid ,String otherid);

        void like(String uid,String otherId,int type);

        void online(String uid,String otherId);

        void cancelRemind(String uid,String otherId);
    }
}
