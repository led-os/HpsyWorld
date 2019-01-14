package com.kuwai.ysy.module.mine.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;

public class LikeBean implements MultiItemEntity {


    /**
     * l_ld : 15
     * uid : 2
     * nickname : 222
     * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
     * age : 1993-11-11
     * gender : 2
     * is_vip : 0
     */

    private int l_ld;
    private int uid;
    private String nickname;
    private String avatar;
    private String age;
    private int gender;
    private int is_vip;

    public int getL_ld() {
        return l_ld;
    }

    public void setL_ld(int l_ld) {
        this.l_ld = l_ld;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }
}
