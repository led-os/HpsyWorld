package com.kuwai.ysy.module.circle.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyLikeListBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.circle.bean.HoleCommentListBean;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;

public class CircleApiFactory {

    public static Observable<ArrayList<CategoryBean>> getCircleList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getCircleData()
                .compose(RxSchedulers.<ArrayList<CategoryBean>>ioMain());
    }

    public static Observable<DyMainListBean> getDyMainListData(int page, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyMainListData(page, uid)
                .compose(RxSchedulers.<DyMainListBean>ioMain());
    }

    public static Observable<DyMainListBean> getDyMyFriendListData(int page, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyMyFriendListData(page, uid)
                .compose(RxSchedulers.<DyMainListBean>ioMain());
    }

    public static Observable<DyDetailBean> getDyDetailData(String d_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyDetailData(d_id, uid)
                .compose(RxSchedulers.<DyDetailBean>ioMain());
    }

    public static Observable<SimpleResponse> publishDy(HashMap<String, String> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .publishDy(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteDy(String d_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteDy(d_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<DyLikeListBean> getDyLikeListData(String d_id, String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyLikeListData(d_id, uid, page)
                .compose(RxSchedulers.<DyLikeListBean>ioMain());
    }

    public static Observable<DyRewardlistBean> getDyRewardListData(String d_id, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyRewardListData(d_id, page)
                .compose(RxSchedulers.<DyRewardlistBean>ioMain());
    }

    public static Observable<DyCommentListBean> getDyCommentListData(String d_id, String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyCommentListData(d_id, uid, page)
                .compose(RxSchedulers.<DyCommentListBean>ioMain());
    }

    public static Observable<SimpleResponse> dyLikeOrNot(String d_id, String uid, String other_id, int status) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .dyLikeOrNot(d_id, uid, other_id, status)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> dyComment(String d_id, String uid, String text) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .dyComment(d_id, uid, text)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> dySecComment(String d_clid, String uid, String text, int other_uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .dySecComment(d_clid, uid, text, other_uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteDyComment(String t_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteDyComment(t_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteDySecComment(String t_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteDySecComment(t_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> dyLikeOrNot(String d_id, String uid, int status, int d_clid, int other_uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .dyLikeOrNot(d_id, uid, status, d_clid, other_uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> publishHole(HashMap<String, String> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .publishHole(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<HoleMainListBean> getHoleMainListData(int page, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getHoleMainListData(page, uid)
                .compose(RxSchedulers.<HoleMainListBean>ioMain());
    }

    public static Observable<HoleDetailBean> getHoleDetailData(String t_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getHoleDetailData(t_id, uid)
                .compose(RxSchedulers.<HoleDetailBean>ioMain());
    }

    public static Observable<DyRewardlistBean> getHoleDetaiZanlData(String t_id, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getDyHoleListData(t_id, page)
                .compose(RxSchedulers.<DyRewardlistBean>ioMain());
    }

    public static Observable<SimpleResponse> openAnonyChat(String d_id, String uid, int status) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .openAnonyChat(d_id, uid, status)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> openComment(String d_id, String uid, int status) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .openComment(d_id, uid, status)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteHole(String d_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteHole(d_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteHoleComment(String d_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteHoleComment(d_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteHoleSecComment(String d_id, String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .deleteHoleSecComment(d_id, uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> holeComment(String d_id, String uid, String text) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .holeComment(d_id, uid, text)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> holeSecComment(String d_id, String uid, String other_id, String text) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .holeSecComment(d_id, uid, other_id, text)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<HoleCommentListBean> getHoleCommentListData(String d_id, String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .getHoleCommentListData(d_id, uid, page)
                .compose(RxSchedulers.<HoleCommentListBean>ioMain());
    }

    public static Observable<SimpleResponse> holeLikeOrNot(HashMap<String, String> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(CircleService.class)
                .holeLikeOrNot(params)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

}