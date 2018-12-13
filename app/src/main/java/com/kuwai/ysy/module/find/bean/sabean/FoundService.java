package com.kuwai.ysy.module.find.bean.sabean;

import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.module.home.bean.HomeBean;

import io.reactivex.Observable;
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






}
