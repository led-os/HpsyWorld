package com.kuwai.ysy.module.circle.business.dongtai;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.IRBaseView;

public class DongtaiMainContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(DyMainListBean dyMainListBean);

        void deteleDy(SimpleResponse simpleResponse);

        void dyListZan(SimpleResponse simpleResponse);

        void setFriendData(DyMainListBean dyMainListBean);

        void setMoreFriendData(DyMainListBean dyMainListBean);
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

        void dyDetelt(String did, String uid);

        void dyListZan(String did, String uid, String otherid, int status);

        void requestFriendData(int page, String uid);

        void requestMore(int page, String uid);

        void requestMoreFriend(int page, String uid);

    }
}
