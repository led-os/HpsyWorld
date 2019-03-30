package com.kuwai.ysy.module.findtwo.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.bean.SearchCityBean;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.FindHomeBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailOther;
import com.kuwai.ysy.module.findtwo.bean.MeetListBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYaoBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYingBean;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class Appoint2ApiFactory {

    public static Observable<MovieBean> getAllMovie(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getAllMovie(uid)
                .compose(RxSchedulers.<MovieBean>ioMain());
    }

    public static Observable<FindHomeBean> getFindHome(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getFindHome(param)
                .compose(RxSchedulers.<FindHomeBean>ioMain());
    }

    public static Observable<SimpleResponse> publishMeet(Map<String, RequestBody> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .publishMeet(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<MeetListBean> appointList(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .appointList(param)
                .compose(RxSchedulers.<MeetListBean>ioMain());
    }

    public static Observable<SportBean> getAllSport() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getAllSport()
                .compose(RxSchedulers.<SportBean>ioMain());
    }

    public static Observable<Theme2Bean> getAllTheme() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getAllTheme()
                .compose(RxSchedulers.<Theme2Bean>ioMain());
    }

    public static Observable<MeetYaoBean> getYaoList(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getYaoList(param)
                .compose(RxSchedulers.<MeetYaoBean>ioMain());
    }

    public static Observable<MeetYingBean> getYingList(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getYingList(param)
                .compose(RxSchedulers.<MeetYingBean>ioMain());
    }

    public static Observable<MeetDetailOther> getMeetDetailOther(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getMeetDetailOther(param)
                .compose(RxSchedulers.<MeetDetailOther>ioMain());
    }

    public static Observable<MeetDetailBean> getMeetDetail(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getMeetDetail(param)
                .compose(RxSchedulers.<MeetDetailBean>ioMain());
    }

    public static Observable<SimpleResponse> inviteApply(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .inviteApply(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> cancelApply(String uid, String r_id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .cancelApply(uid, r_id)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> agreeApply(String uid, String r_d_id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .agreeApply(uid, r_d_id)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteDate(String uid, String r_d_id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .deleteDate(uid, r_d_id)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<CloseBean> getClose(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .getClose(uid, page)
                .compose(RxSchedulers.<CloseBean>ioMain());
    }

    public static Observable<SimpleResponse> addChatRoom(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .addChatRoom(uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteChatRoom(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .deleteChatRoom(uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> canChat(String uid,String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .canChat(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> likeTwo(String uid,String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(Appoint2Service.class)
                .likeTwo(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }
}
