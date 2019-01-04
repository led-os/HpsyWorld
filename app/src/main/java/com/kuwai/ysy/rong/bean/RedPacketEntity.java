package com.kuwai.ysy.rong.bean;


import android.net.Uri;

public class RedPacketEntity {
    public String name;
    public Uri avatar;
    public String remark;

    public RedPacketEntity(String name, Uri avatar, String remark) {
        this.name = name;
        this.avatar = avatar;
        this.remark = remark;
    }
}
