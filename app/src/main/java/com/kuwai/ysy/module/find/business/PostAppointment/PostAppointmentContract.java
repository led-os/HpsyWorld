package com.kuwai.ysy.module.find.business.PostAppointment;

import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public class PostAppointmentContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 发送约会信息
         */
        void getInfo(BlindBean blindBean);

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
         * 发送约会信息
         */
        void sendInfo(HashMap<String, RequestBody> params);
    }
}
