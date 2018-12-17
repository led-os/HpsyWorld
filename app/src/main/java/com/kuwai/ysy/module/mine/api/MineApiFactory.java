package com.kuwai.ysy.module.mine.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.module.find.api.AppointService;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.ESUser;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
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

    public static Observable<GiftAcceptBean> getGiftAccept(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftMyAccept(uid, page)
                .compose(RxSchedulers.<GiftAcceptBean>ioMain());
    }

    public static Observable<GiftAcceptBean> getGiftSend(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftMySend(uid, page)
                .compose(RxSchedulers.<GiftAcceptBean>ioMain());
    }

    public static Observable<VisitorBean> getVisitor(String uid, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVisitors(uid, type)
                .compose(RxSchedulers.<VisitorBean>ioMain());
    }

    public static Observable<VisitorBean> getVisitorEarlier(String uid, int type,int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVisitorsEarlier(uid, type,page)
                .compose(RxSchedulers.<VisitorBean>ioMain());
    }

}
