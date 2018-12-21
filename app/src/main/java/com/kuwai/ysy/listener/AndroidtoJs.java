package com.kuwai.ysy.listener;

import android.webkit.JavascriptInterface;


public class AndroidtoJs extends Object{

    private H5CallBack callBack = null;
    public interface H5CallBack{
        void doInvite(String msg);
        void doLink(String msg);
    }

    public void setCallback(H5CallBack h5CallBack){
        callBack = h5CallBack;
    }

    @JavascriptInterface
    public void invitationimg(String msg) {
        callBack.doInvite(msg);
    }

    @JavascriptInterface
    public void invitationlink(String msg) {
       callBack.doLink(msg);
    }
}
