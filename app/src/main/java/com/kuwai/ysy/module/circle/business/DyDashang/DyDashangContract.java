package com.kuwai.ysy.module.circle.business.DyDashang;

import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.rayhahah.rbase.base.IRBaseView;

public class DyDashangContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(DyRewardlistBean dyRewardlistBean);

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
        void requestHomeData(String d_id, int page);

        void requestHoleData(String t_id,int page);
    }
}
