package com.kuwai.ysy.listener;

import android.webkit.JavascriptInterface;


public class ActivityDetailCall extends Object{

    private H5CallBack callBack = null;
    public interface H5CallBack{
        void viewmap(String lat,String lng,String place);
    }

    public void setCallback(H5CallBack h5CallBack){
        callBack = h5CallBack;
    }

    @JavascriptInterface
    public void viewmap(String lat,String lon,String add) {
        callBack.viewmap(lat,lon,add);
    }
}
