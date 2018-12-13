package com.kuwai.ysy.module.mine.bean.vip;

public class VipBannerBean {

    private String title;
    private int bg;
    private int img;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public VipBannerBean(String title, int bg, int img) {
        this.title = title;
        this.bg = bg;
        this.img = img;
    }
}
