package com.kuwai.ysy.module.mine.api;


import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MineService {

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<ResponseBody> login(@FieldMap Map<String, String> params);

    //个人主页-列表
    @FormUrlEncoded
    @POST("My/PersonalList")
    Observable<UserInfo> getUsetInfo(@Field("uid") String uid);

    //个人主页-资料-本人查看
    @FormUrlEncoded
    @POST("My/PersonalInfo")
    Observable<UserInfo> getUsetInfoMine(@Field("uid") String uid);


}
