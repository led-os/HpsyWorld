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
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.module.findtwo.bean.VideoRecordBean;
import com.kuwai.ysy.module.mine.bean.IndentBean;
import com.kuwai.ysy.module.mine.bean.PrivateBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class FoundApiFactory {

    public static Observable<FoundBean> getTeamList(Map<String, Object> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getFirstHomeData(map)
                .compose(RxSchedulers.<FoundBean>ioMain());
    }

    public static Observable<ProvincesAndCityBean> getProvinceList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getLocalData()
                .compose(RxSchedulers.<ProvincesAndCityBean>ioMain());
    }

    public static Observable<LocalNextBean> getLocalNextList(String name) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getNextData(name)
                .compose(RxSchedulers.<LocalNextBean>ioMain());
    }

    public static Observable<CityMeetBean> getCityMeetList(Map<String, Object> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMeetListData(params)
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

    public static Observable<SimpleResponse> userPing(String uid, int other) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .userPing(uid, other)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<TuoDanBean> getTuoDanList(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getTuoDanData(param)
                .compose(RxSchedulers.<TuoDanBean>ioMain());
    }

    public static Observable<MyBlindBean> getMyBlindList(int page, int uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getMyBlindData(page, uid)
                .compose(RxSchedulers.<MyBlindBean>ioMain());
    }

    public static Observable<BlindBean> sendBlind(HashMap<String, RequestBody> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .sendBlind(params)
                .compose(RxSchedulers.<BlindBean>ioMain());
    }

    public static Observable<VideoChatBean> getVideoList(HashMap<String, Object> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getVideoList(params)
                .compose(RxSchedulers.<VideoChatBean>ioMain());
    }

    public static Observable<SimpleResponse> closeIncrease(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .closeIncrease(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> finishVideoChat(String uid, String otherId,String time) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .finishVideoChat(uid,otherId,time)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<VideoRecordBean> getChatRecord(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getChatRecord(uid,page)
                .compose(RxSchedulers.<VideoRecordBean>ioMain());
    }

    public static Observable<SimpleResponse> removeChatRecord(String uid, int vid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .removeChatRecord(uid,vid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<PrivateBean> getPrivateList(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getPrivateList(uid)
                .compose(RxSchedulers.<PrivateBean>ioMain());
    }

    public static Observable<SimpleResponse> privateSet(String uid, int type,String settings) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .privateSet(uid,type,settings)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<IndentBean> getIdentime(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getIdentime(uid)
                .compose(RxSchedulers.<IndentBean>ioMain());
    }
}
