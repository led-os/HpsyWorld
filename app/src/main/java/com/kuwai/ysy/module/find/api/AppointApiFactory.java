package com.kuwai.ysy.module.find.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;

public class AppointApiFactory {

    public static Observable<MyAppointMent> getMyAppoint(String uid, int page, int status) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(AppointService.class)
                .getMyAppoint(uid, page, status)
                .compose(RxSchedulers.<MyAppointMent>ioMain());
    }

    public static Observable<MyCommis> getMyCommis(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(AppointService.class)
                .getMyCommis(uid, page)
                .compose(RxSchedulers.<MyCommis>ioMain());
    }

    public static Observable<DateTheme> getAllThemes(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(AppointService.class)
                .getAlltheme(uid)
                .compose(RxSchedulers.<DateTheme>ioMain());
    }

    public static Observable<SimpleResponse> delCustomTheme(String uid,int sid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(AppointService.class)
                .delCustomTheme(uid,sid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }
}