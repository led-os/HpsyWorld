package com.kuwai.ysy.module.find.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observable;

public class FoundApiFactory {

    public static Observable<FoundBean> getTeamList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getFirstHomeData()
                .compose(RxSchedulers.<FoundBean>ioMain());
    }

    public static Observable<ProvincesAndCityBean> getProvinceList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getLocalData()
                .compose(RxSchedulers.<ProvincesAndCityBean>ioMain());
    }

    public static Observable<LocalNextBean> getLocalNextList(int id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getNextData(id)
                .compose(RxSchedulers.<LocalNextBean>ioMain());
    }

    public static Observable<CityMeetBean> getCityMeetList(int page, String city) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMeetListData(page, city)
                .compose(RxSchedulers.<CityMeetBean>ioMain());
    }

    public static Observable<BlindBean> sendMeetApply(int rid, int uid, String text) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .sendMeetApply(rid, uid, text)
                .compose(RxSchedulers.<BlindBean>ioMain());
    }

    public static Observable<SimpleResponse> sendCancelApply(int rid, int uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .sendCancelApply(rid, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<BlindBean> sendMeetAgree(int rdid, int status) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .sendMeetAgree(rdid, status)
                .compose(RxSchedulers.<BlindBean>ioMain());
    }

    public static Observable<SimpleResponse> addChengyi(String uid, String rid, String money) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .addChengyi(uid, rid, money)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> addGift(String uid, String rid, String gift) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .addGift(uid, rid, gift)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<MeetThemeBean> getMeetFilter() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMeetFilterData()
                .compose(RxSchedulers.<MeetThemeBean>ioMain());
    }

    public static Observable<CommisDetailBean> getCommisDetail(int rid, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getCommisDetailData(rid, uid)
                .compose(RxSchedulers.<CommisDetailBean>ioMain());
    }

    public static Observable<MyCommisDetailBean> getMyCommisDetail(int rid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMyCommisDetailData(rid)
                .compose(RxSchedulers.<MyCommisDetailBean>ioMain());
    }

    public static Observable<SimpleResponse> deleteAppoint(int rid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .deleteAppoint(rid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<TuoDanBean> getTuoDanList(int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getTuoDanData(page)
                .compose(RxSchedulers.<TuoDanBean>ioMain());
    }

    public static Observable<MyBlindBean> getMyBlindList(int page, int uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMyBlindData(page, uid)
                .compose(RxSchedulers.<MyBlindBean>ioMain());
    }

    public static Observable<BlindBean> sendBlind(HashMap<String, Object> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .sendBlind(params)
                .compose(RxSchedulers.<BlindBean>ioMain());
    }


}
