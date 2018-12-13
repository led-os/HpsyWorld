package com.kuwai.ysy.module.find.business.FoundLocation;

import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.rayhahah.rbase.base.IRBaseView;

public class FoundLocationContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(ProvincesAndCityBean provincesAndCityBean);

        void setCityData(LocalNextBean localNextBean);

        void setAreaData(LocalNextBean localNextBean);

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
        void requestHomeData();
        void requestNextData(int id);
        void requestAreaData(int id);
    }
}
