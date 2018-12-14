package com.kuwai.ysy.module.mine.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.module.find.api.AppointService;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.ESUser;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.net.ApiClient;
import com.kuwai.ysy.utils.HuPuHelper;
import com.kuwai.ysy.utils.security.MD5;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class MineApiFactory {

    public static Observable<UserInfo> getUserInfo(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUsetInfo(uid)
                .compose(RxSchedulers.<UserInfo>ioMain());
    }

    public static Observable<PersolHomePageBean> getOtherHomepageInfo(String uid, String otherid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getOtherPersonalInfo(uid, otherid)
                .compose(RxSchedulers.<PersolHomePageBean>ioMain());
    }

}
