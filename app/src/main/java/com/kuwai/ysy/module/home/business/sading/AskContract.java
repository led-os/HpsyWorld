package com.kuwai.ysy.module.home.business.sading;

import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by sunnysa on 2018/7/24.
 */

public class AskContract {

    public interface IAskView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setAskData(HomeBean homeBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<AskBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestAskData(int num);
    }
}
