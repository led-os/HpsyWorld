package com.kuwai.ysy.module.mine.business.vip;

import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.Map;

public class VipHuangjinContract {

    public interface IMineView extends IRBaseView {

        void setAliOrderInfo(SimpleResponse infoBean);
        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IUserPresenter {


        void getAliOrderInfo(Map<String,Object> param);

    }
}
