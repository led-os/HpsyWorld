package com.kuwai.ysy.module.home.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class HomeApiFactory {

    public static Observable<HomeBean> getTeamList(String num) {
        return ApiClient.get(C.BaseURL.TEST_URL)
                .create(HomeService.class)
                .getFirstHomeData(num)
                .compose(RxSchedulers.<HomeBean>ioMain());
    }

    public static Observable<LoginBean> login(Map<String, String> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .login(num)
                .compose(RxSchedulers.<LoginBean>ioMain());
    }

    public static Observable<CodeBean> getCode(String phone, String type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getCode(phone, type)
                .compose(RxSchedulers.<CodeBean>ioMain());
    }

    public static Observable<SimpleResponse> resetPassword(String phone, String psd, String code) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .resetPassword(phone, psd,code)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<LoginBean> codeAuth(Map<String, String> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .codeAuth(num)
                .compose(RxSchedulers.<LoginBean>ioMain());
    }

    public static Observable<SimpleResponse> regist(Map<String, RequestBody> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .regist(num)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<HomeVideoBean> getHomeData(Map<String, Object> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getHomeData(num)
                .compose(RxSchedulers.<HomeVideoBean>ioMain());
    }
}
