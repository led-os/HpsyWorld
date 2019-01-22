package com.kuwai.ysy.listener;

import android.webkit.JavascriptInterface;


public class InviteCall extends Object {

    private H5CallBack callBack = null;

    public interface H5CallBack {
        void sharetoother(String url,String title,String message);
    }

    public void setCallback(H5CallBack h5CallBack) {
        callBack = h5CallBack;
    }

    @JavascriptInterface
    public void sharetoother(String url,String title,String message) {
        callBack.sharetoother(url,title,message);
    }


}
