package com.kuwai.ysy.module.find.bean.sabean;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;

public class FoundApiFactory {

    public static Observable<FoundBean> getTeamList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getFirstHomeData()
                .compose(RxSchedulers.<FoundBean>ioMain());
    }

    public static Observable<ProvincesAndCityBean> getProvinceList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getLocalData()
                .compose(RxSchedulers.<ProvincesAndCityBean>ioMain());
    }

    public static Observable<LocalNextBean> getLocalNextList(int id) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(FoundService.class)
                .getNextData(id)
                .compose(RxSchedulers.<LocalNextBean>ioMain());
    }
}
