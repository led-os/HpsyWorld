package com.kuwai.ysy.utils;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.rayhahah.rbase.utils.useful.SPManager;

import static com.kuwai.ysy.app.C.MSG_LOGIN;

public class SPUtils {

    public static void savaLogin(LoginBean loginBean){
        SPManager.get().putString("uid", String.valueOf(loginBean.getData().getUid()));
        SPManager.get().putString("nickname", loginBean.getData().getNickname());
        SPManager.get().putString("phone_", loginBean.getData().getPhone());
        SPManager.get().putString("city_", loginBean.getData().getCity());
        SPManager.get().putString("ident_", String.valueOf(loginBean.getData().getIdent()));
        SPManager.get().putString("icon", loginBean.getData().getAvatar());
        SPManager.get().putString("grade_", String.valueOf(loginBean.getData().getVip_grade()));
        SPManager.get().putString("sex_", String.valueOf(loginBean.getData().getGender()));
        SPManager.get().putString("isvip_", String.valueOf(loginBean.getData().getIs_vip()));
        SPManager.get().putString(C.HAS_THIRD_PASS, String.valueOf(loginBean.getData().getPayment()));
        SPManager.get().putString("rongyun_token", loginBean.getData().getRongyun_token());
        SPManager.get().putString("token", loginBean.getData().getToken());
        SPManager.get().putString("sign_", loginBean.getData().getSig());
        EventBusUtil.sendEvent(new MessageEvent(MSG_LOGIN));
    }
}
