package com.kuwai.ysy.module.find.api;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppointService {

    //获取我的邀约
    @FormUrlEncoded
    @POST("Appointment/MyAppointment")
    Observable<MyAppointMent> getMyAppoint(@Field("uid") String uid, @Field("page") int page, @Field("status") int status);

    //获取我的应约
    @FormUrlEncoded
    @POST("Appointment/Byappointment")
    Observable<MyCommis> getMyCommis(@Field("uid") String uid, @Field("page") int page, @Field("status") int status);

    //获取所有约会主题
    @FormUrlEncoded
    @POST("Appointment/AppointmentSincerityList")
    Observable<DateTheme> getAlltheme(@Field("uid") String uid);

    //获取所有礼物
    @GET("gift/giftList")
    Observable<GiftPopBean> getAllGifts();

    //删除自定义主题
    @FormUrlEncoded
    @POST("Appointment/DelAppointmentSincerityCustom")
    Observable<SimpleResponse> delCustomTheme(@Field("uid") String uid,@Field("s_id") int sid);



}
