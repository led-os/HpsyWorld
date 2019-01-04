package com.kuwai.ysy.module.mine.business.homepage.otherhomepage;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.rayhahah.rbase.base.IRBaseView;

public class DongtaiOtherContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(DyMainListBean dyMainListBean);


        void dyListZan(SimpleResponse simpleResponse);

        /**
         * 设置加载更多的数据
         */
        void setMoreData(DyMainListBean dyMainListBean);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(int page, String uid);

        void dyListZan(String did, String uid, String otherid, int status);

        void requestMore(int page, String uid);

    }
}
