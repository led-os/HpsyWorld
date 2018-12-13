package com.kuwai.ysy.module.home.business;

import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.IRBaseView;

public class HomeContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(HomeBean homeBean);

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
        void requestHomeData(int num);
    }
}
