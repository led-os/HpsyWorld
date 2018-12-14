package com.kuwai.ysy.module.mine.business.mine;

import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.rayhahah.rbase.base.IRBaseView;

public class MineContract {

    public interface IMineView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setUserData(UserInfo cityMeetBean);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IUserPresenter {

        void requestUserData(String uid);

    }
}
