package com.kuwai.ysy.module.findtwo.api;

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

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface Appoint2Service {

    //获取所有电影
    @FormUrlEncoded
    @POST("HomePage/getVideoList")
    Observable<MovieBean> getAllMovie(@Field("city") String uid);


    //获取发现首页
    @FormUrlEncoded
    @POST("HomePage/find_two")
    Observable<FindHomeBean> getFindHome(@FieldMap Map<String, Object> map);

    //发布约会
    @Multipart
    @POST("HomePage/Appointment_two")
    Observable<SimpleResponse> publishMeet(@PartMap Map<String, RequestBody> map);

    //约会列表
    @FormUrlEncoded
    @POST("HomePage/AppointmentList_two")
    //@Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Observable<MeetListBean> appointList(@FieldMap Map<String, Object> map);


    @GET("HomePage/getMotionList")
    Observable<SportBean> getAllSport();

    @GET("HomePage/getReleaseOtherList")
    Observable<Theme2Bean> getAllTheme();

    //我的邀约
    @FormUrlEncoded
    @POST("HomePage/MyAppointment_two")
    Observable<MeetYaoBean> getYaoList(@FieldMap Map<String, Object> map);

    //我的应约
    @FormUrlEncoded
    @POST("HomePage/Byappointment_two")
    Observable<MeetYingBean> getYingList(@FieldMap Map<String, Object> map);

    //他人约会详情
    @FormUrlEncoded
    @POST("HomePage/OtherAppointmentDetails_two")
    Observable<MeetDetailOther> getMeetDetailOther(@FieldMap Map<String, Object> map);

    //自己约会详情
    @FormUrlEncoded
    @POST("HomePage/MyAppointmentDetails_two")
    Observable<MeetDetailBean> getMeetDetail(@FieldMap Map<String, Object> map);

    //申请应约
    @FormUrlEncoded
    @POST("HomePage/invitation_two")
    Observable<SimpleResponse> inviteApply(@FieldMap Map<String, Object> map);

    //取消应约（只有发布方未同意时方可取消应约）
    @FormUrlEncoded
    @POST("HomePage/CancelTheContract")
    Observable<SimpleResponse> cancelApply(@Field("uid") String uid, @Field("r_id") String r_id);

    //同意应约
    @FormUrlEncoded
    @POST("HomePage/agreeInvi_two")
    Observable<SimpleResponse> agreeApply(@Field("uid") String uid, @Field("r_d_id") String r_d_id);

    //删除约会
    @FormUrlEncoded
    @POST("HomePage/DeleteAppointment_two")
    Observable<SimpleResponse> deleteDate(@Field("uid") String uid, @Field("r_id") String r_id);

    //亲密关系
    @FormUrlEncoded
    @POST("HomePage/MyIntimateList")
    Observable<CloseBean> getClose(@Field("uid") String uid, @Field("page") int page);

    //添加视频聊天室
    @FormUrlEncoded
    @POST("HomePage/addVideoChatRoom")
    Observable<SimpleResponse> addChatRoom(@Field("uid") String uid);

    //删除视频聊天室
    @FormUrlEncoded
    @POST("HomePage/delVideoChatRoom")
    Observable<SimpleResponse> deleteChatRoom(@Field("uid") String uid);

    //判断是否可以聊天
    @FormUrlEncoded
    @POST("HomePage/WhatVideoChatRoom")
    Observable<SimpleResponse> canChat(@Field("uid") String uid, @Field("other_uid") String other_uid);

    //喜欢（每天一次）
    @FormUrlEncoded
    @POST("HomePage/UserLike_two")
    Observable<SimpleResponse> likeTwo(@Field("uid") String uid, @Field("other_uid") String other_uid);
}
