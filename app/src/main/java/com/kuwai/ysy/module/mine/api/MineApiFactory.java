package com.kuwai.ysy.module.mine.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.module.mine.bean.ESUser;
import com.kuwai.ysy.net.ApiClient;
import com.kuwai.ysy.utils.HuPuHelper;
import com.kuwai.ysy.utils.security.MD5;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class MineApiFactory {

    public static Observable<ResponseBody> loginHupu(String userName, String password) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("client", C.DEVICE_ID);
        params.put("client", "864444036940802");
        params.put("username", userName);
        params.put("password", MD5.getMD5(password));
        params.put("crt", System.currentTimeMillis() + "");
        params.put("night", "0");
        params.put("channel", "miui");
        params.put("android_id", "864c5bdabcd5586a");
        params.put("time_zone", "Asia/Shanghai");
        String sign = HuPuHelper.getRequestSign(params);
        params.put("sign", sign);
//        params.put("sign", "66e7cf600416efdef3062296442516f5");
        return null;
    }

}
