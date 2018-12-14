package com.kuwai.ysy.module.mine.bean.vip;

public class GallaryBean {

    private String pic;
    private boolean isVip = false;
    private boolean hasPay = false;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public boolean isHasPay() {
        return hasPay;
    }

    public void setHasPay(boolean hasPay) {
        this.hasPay = hasPay;
    }

    public GallaryBean(String pic, boolean isVip, boolean hasPay) {
        this.pic = pic;
        this.isVip = isVip;
        this.hasPay = hasPay;
    }
}
