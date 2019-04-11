package com.kuwai.ysy.module.home.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.ResponseWithData;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.home.bean.AuthVideoBean;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.bean.main.NearPerBean;
import com.kuwai.ysy.module.home.bean.main.PersonPicBean;
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

    public static Observable<ResponseWithData> getAes(String phone, String type) {
        return ApiClient.get(C.BaseURL.LOCAL_URL)
                .create(HomeService.class)
                .getAes(phone, type)
                .compose(RxSchedulers.<ResponseWithData>ioMain());
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

    public static Observable<LoginBean> regist(Map<String, RequestBody> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .regist(num)
                .compose(RxSchedulers.<LoginBean>ioMain());
    }

    public static Observable<HomeVideoBean> getHomeData(Map<String, Object> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getHomeData(num)
                .compose(RxSchedulers.<HomeVideoBean>ioMain());
    }

    public static Observable<HomeCardBean> getHomeCardData(Map<String, Object> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getHomeCardData(num)
                .compose(RxSchedulers.<HomeCardBean>ioMain());
    }

    public static Observable<NearPerBean> getNearPer(Map<String, Object> num) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getNearPer(num)
                .compose(RxSchedulers.<NearPerBean>ioMain());
    }

    public static Observable<PersonPicBean> getPic(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getPic(uid)
                .compose(RxSchedulers.<PersonPicBean>ioMain());
    }

    public static Observable<AuthVideoBean> getIdenVideo(String uid, String loginId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(HomeService.class)
                .getIdenVideo(uid,loginId)
                .compose(RxSchedulers.<AuthVideoBean>ioMain());
    }
}
