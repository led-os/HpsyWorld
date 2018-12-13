package com.kuwai.ysy.module.chat.bean;

public class PhotoBook {
    private String name;        //联系人姓名
    private String telPhone;    //电话号码


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public PhotoBook() {

    }

    public PhotoBook(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }
}
