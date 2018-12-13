package com.kuwai.ysy.module.circle.business;

import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.ArrayList;

public class CircleContract {

    public interface ICircle extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setCircleData(ArrayList<CategoryBean> homeBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface ICirclePresenter {

        /**
         * 获取圈子数据
         */
        void requestCircleData();
    }
}
