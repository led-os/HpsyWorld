package com.kuwai.ysy.module.home.api;

import com.kuwai.ysy.bean.ResponseWithData;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.bean.main.NearPerBean;
import com.kuwai.ysy.module.home.bean.main.PersonPicBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface HomeService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    Observable<HomeBean> getFirstHomeData(@Query("num") String num);

    @FormUrlEncoded
    @POST("User/login")
    Observable<LoginBean> login(@FieldMap Map<String, String> num);

    @FormUrlEncoded
    @POST("Sms/mobilePhoneCheckCode")
    Observable<CodeBean> getCode(@Field("phone") String phone, @Field("type") String type);

    @FormUrlEncoded
    @POST("Cesuan/varilication")
    Observable<ResponseWithData> getAes(@Field("uid") String phone, @Field("type") String type);

    @FormUrlEncoded
    @POST("User/resetPassword")
    Observable<SimpleResponse> resetPassword(@Field("phone") String phone, @Field("pass_word") String pass_word, @Field("check_code") String check_code);

    @FormUrlEncoded
    @POST("User/ThirdPartyRegistration")
    Observable<LoginBean> codeAuth(@FieldMap Map<String, String> num);

    @Multipart
    @POST("User/register")
    Observable<LoginBean> regist(@PartMap Map<String, RequestBody> num);

    @FormUrlEncoded
    @POST("HomePage/HomePageList")
    Observable<HomeVideoBean> getHomeData(@FieldMap Map<String, Object> num);

    @FormUrlEncoded
    @POST("HomePage/HomePageList_two")
    Observable<HomeCardBean> getHomeCardData(@FieldMap Map<String, Object> num);

    @FormUrlEncoded
    @POST("HomePage/HomePage_nearby")
    Observable<NearPerBean> getNearPer(@FieldMap Map<String, Object> num);

    @FormUrlEncoded
    @POST("HomePage/photo")
    Observable<PersonPicBean> getPic(@Field("uid") String uid);
}
