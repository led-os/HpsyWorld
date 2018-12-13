package com.kuwai.ysy.module.home.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;

public class HomeApiFactory {

    public static Observable<HomeBean> getTeamList(String num) {
        return ApiClient.get(C.BaseURL.TEST_URL)
                .create(HomeService.class)
                .getFirstHomeData(num)
                .compose(RxSchedulers.<HomeBean>ioMain());
    }
}
