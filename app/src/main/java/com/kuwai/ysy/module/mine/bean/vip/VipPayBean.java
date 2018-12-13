package com.kuwai.ysy.module.mine.bean.vip;

public class VipPayBean {

    private int vipType;
    private String type;
    private String price;
    private String des;

    private boolean isCheck;

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type == null ? "" : type;
    }

    public String getPrice() {
        return price == null ? "" : price;
    }

    public void setPrice(String price) {
        this.price = price == null ? "" : price;
    }

    public String getDes() {
        return des == null ? "" : des;
    }

    public void setDes(String des) {
        this.des = des == null ? "" : des;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public VipPayBean(String type, String price, String des, boolean isCheck) {
        this.type = type;
        this.price = price;
        this.des = des;
        this.isCheck = isCheck;
    }

    public VipPayBean(int vipType, String type, String price, String des, boolean isCheck) {
        this.vipType = vipType;
        this.type = type;
        this.price = price;
        this.des = des;
        this.isCheck = isCheck;
    }
}
