package com.kuwai.ysy.module.home.bean;

import com.kuwai.ysy.app.C;

public class VideoBean {

    private String url;
    private boolean isPlay = false;
    private int type = C.DY_FILM;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public VideoBean(String url) {
        this.url = url;
    }

    public VideoBean(String url, int type) {
        this.url = url;
        this.type = type;
    }
}
