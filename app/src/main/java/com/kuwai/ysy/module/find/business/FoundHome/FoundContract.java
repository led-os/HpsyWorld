package com.kuwai.ysy.module.find.business.FoundHome;

import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.Map;

public class FoundContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(FoundBean foundBean);

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
        void requestHomeData(Map<String ,Object> map);
    }
}
