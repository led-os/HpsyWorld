package com.kuwai.ysy.module.mine.business.question;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.rayhahah.rbase.base.IRBaseView;

public class AskContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(MyAskBean askBean);

        void delAsk(SimpleResponse simpleResponse);

        void UpdateProblem(SimpleResponse simpleResponse);

        void setAddAsk(SimpleResponse simpleResponse);

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
        void requestHomeData(String uid);

        /**
         * 删除问题
         */
        void sendDelAsk(String uid,int pid);

        /**
         * 修改 问题/回答/添加回答
         */
        void getUpdateProblem(String uid ,int pid ,String pro,String answer);
        /**
         * 添加问题
         */
        void getAddAsk(String uid ,String pro);
    }
}
