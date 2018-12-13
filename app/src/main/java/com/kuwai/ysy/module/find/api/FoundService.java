package com.kuwai.ysy.module.find.api;

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

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoundService {

    /**
     * 获取发现首页
     */
    @POST("Appointment/find")
    Observable<FoundBean> getFirstHomeData();

    //获取省市的全部信息
    @GET("Currency/getProvincesAndCitiesInfoList")
    Observable<ProvincesAndCityBean> getLocalData();

    //获取下一级地区列表
    @GET("Currency/getRegionInfoList")
    Observable<LocalNextBean> getNextData(@Query("region_id") int num);

    //同城约会
    @POST("Appointment/AppointmentList")
    Observable<CityMeetBean> getMeetListData(@Query("page") int page, @Query("city") String city);

    //约会-申请方-申请应约
    @POST("Appointment/invitation")
    Observable<BlindBean> sendMeetApply(@Query("r_id") int rid, @Query("uid") int uid, @Query("text") String text);

    //约会-申请方-取消应约
    @POST("Appointment/CancelTheContract")
    Observable<BlindBean> sendCancelApply(@Query("r_d_id") int rid, @Query("uid") int uid);

    //约会-发布方-同意应约
    @POST("Appointment/agreeInvi")
    Observable<BlindBean> sendMeetAgree(@Query("r_d_id") int rid, @Query("status") int status);

    //约会主题列表&数量
    @POST("Appointment/SearchAppointmentSincerity")
    Observable<MeetThemeBean> getMeetFilterData();

    //约会详情页（对方看到）
    @POST("Appointment/OtherAppointmentDetails")
    Observable<CommisDetailBean> getCommisDetailData(@Query("r_id") int rid);

    //约会详情页（自己）
    @POST("Appointment/MyAppointmentDetails")
    Observable<MyCommisDetailBean> getMyCommisDetailData(@Query("r_id") int rid);

    //相亲活动列表
    @POST("Appointment/EnrollActivityList")
    Observable<TuoDanBean> getTuoDanData(@Query("page") int page);

    //我的相亲活动列表
    @POST("Appointment/MyEnrollActivityList")
    Observable<MyBlindBean> getMyBlindData(@Query("page") int page, @Query("uid") int uid);

    //发布约会
    @POST("Appointment/Appointment")
    @FormUrlEncoded
    Observable<BlindBean> sendBlind(@FieldMap HashMap<String, Object> params);
//    Observable<BlindBean> sendBlind(@Query("uid") int uid,@Query("sincerity") int sincerity,@Query("release_time") int time,
//                                      @Query("area") String area,@Query("address") String address,@Query("girl_friend") int sex,
//                                      @Query("consumption_type") int payfor,@Query("earnest_money") int money,@Query("gift") String gift,
//                                      @Query("Message") String message,@Query("other") String othertheme);


}
