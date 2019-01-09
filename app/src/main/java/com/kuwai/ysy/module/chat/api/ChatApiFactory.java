package com.kuwai.ysy.module.chat.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.bean.RedMySendBean;
import com.kuwai.ysy.module.chat.bean.RedRecordBean;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.chat.bean.UserInfoBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.net.ApiClient;
import com.kuwai.ysy.rong.bean.RedBean;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.Map;

import io.reactivex.Observable;


public class ChatApiFactory {

    public static Observable<MyFriends> getFriends(Map<String,String> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getFriends(map)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

    public static Observable<MyFriends> getNewFriends(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getNewFriends(uid)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

    public static Observable<UserInfoBean> getUserInfo(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getUserInfo(uid)
                .compose(RxSchedulers.<UserInfoBean>ioMain());
    }

    public static Observable<SimpleResponse> agreeNewFriends(String uid, String other_id, int state) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .agreeNewFriends(uid,other_id,state)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> addFriends(String uid, String other_id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .addFriends(uid,other_id)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<MyFriends> getTuiFriends(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getTuiFriends(uid,page)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

    public static Observable<MyFriends> searchFriends(String search_criteria, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .searchFriends(search_criteria,page)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

    public static Observable<MyFriends> getNearFriends(String uid, int page,String longitude,String latitude) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getNearFriends(uid,page,longitude,latitude)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

    public static Observable<SimpleResponse> setRemark(String uid, String otherId,String remark) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .setRemark(uid,otherId,remark)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> setBlack(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .setBlack(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> cancelBlack(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .cancelBlack(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> cancelPing(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .cancelPing(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<RedBean> sendRed(Map<String,Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .sendRed(param)
                .compose(RxSchedulers.<RedBean>ioMain());
    }

    public static Observable<SimpleResponse> receiveRed(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .receiveRed(uid,otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<RedMySendBean> redDetailMine(String uid, String rid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .redDetailMine(uid,rid)
                .compose(RxSchedulers.<RedMySendBean>ioMain());
    }

    public static Observable<ReceiveBean> redDetailOther(String otherId, String rid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .redDetailOther(otherId,rid)
                .compose(RxSchedulers.<ReceiveBean>ioMain());
    }

    public static Observable<RedRecordBean> redList(String uid, String otherId, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .redList(uid,otherId,page)
                .compose(RxSchedulers.<RedRecordBean>ioMain());
    }

    public static Observable<NoticeBean> getSystemNotice(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getSystemNotice(uid,page)
                .compose(RxSchedulers.<NoticeBean>ioMain());
    }

    public static Observable<NoticeBean> getDateNotice(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getDateNotice(uid)
                .compose(RxSchedulers.<NoticeBean>ioMain());
    }

    public static Observable<StsBean> getSts() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getSts()
                .compose(RxSchedulers.<StsBean>ioMain());
    }

    public static Observable<SimpleResponse> rewardPe(Map<String,Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .rewardPe(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }
}
