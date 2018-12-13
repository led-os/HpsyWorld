package com.kuwai.ysy.module.home.api;

import com.kuwai.ysy.module.home.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    Observable<HomeBean> getFirstHomeData(@Query("num") String num);
}
