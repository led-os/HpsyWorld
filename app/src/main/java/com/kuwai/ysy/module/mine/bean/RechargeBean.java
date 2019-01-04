package com.kuwai.ysy.module.mine.bean;

public class RechargeBean {

    private String num;
    private double price;
    private boolean isCheck = false;

    public String getNum() {
        return num == null ? "" : num;
    }

    public void setNum(String num) {
        this.num = num == null ? "" : num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public RechargeBean(String num, double price) {
        this.num = num;
        this.price = price;
    }
}
