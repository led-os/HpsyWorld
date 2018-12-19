package com.kuwai.ysy.module.mine.business.vip;

import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.rayhahah.rbase.base.IRBaseView;

public class VipHuangjinContract {

    public interface IMineView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setUserData(UserInfo cityMeetBean);

        void setAliOrderInfo(AliOrderInfoBean infoBean);
        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IUserPresenter {

        void requestUserData(String uid);

        void getAliOrderInfo();

    }
}
