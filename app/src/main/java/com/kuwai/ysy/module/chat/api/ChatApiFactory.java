package com.kuwai.ysy.module.chat.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.Map;

import io.reactivex.Observable;


public class ChatApiFactory {

    public static Observable<MyFriends> getFriends(Map<String,String> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(ChatService.class)
                .getFriends(map)
                .compose(RxSchedulers.<MyFriends>ioMain());
    }

}
