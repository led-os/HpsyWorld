package com.kuwai.ysy.listener;

import android.webkit.JavascriptInterface;


public class YuyueCall extends Object{

    private H5CallBack callBack = null;
    public interface H5CallBack{
        void joinactivitysucess();
    }

    public void setCallback(H5CallBack h5CallBack){
        callBack = h5CallBack;
    }

    @JavascriptInterface
    public void joinactivitysucess() {
        callBack.joinactivitysucess();
    }
}
