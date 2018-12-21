package com.kuwai.ysy.module.mine.business.paypsd;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.rayhahah.rbase.base.IRBaseView;

public class ChangePsdContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(SimpleResponse response);

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
        void requestHomeData(String uid, String psd, String newPsd, String reNewPsd, int type);
    }
}
