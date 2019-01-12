package com.kuwai.ysy.module.mine.bean.gift;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableGiftAdapter;

public class GiftChildBean implements MultiItemEntity {

    /**
     * avatar : http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg
     * nickname : 张三
     * uid : 1
     * gender : 1
     * v_id : 22
     * create_time : 1544238373
     * type : 1
     * d_id : 2
     * text : 动态2
     * is_vip : 1
     */

    private String avatar;
    private String nickname;
    private int uid;
    private int gender;
    private int v_id;
    private int create_time;
    private String type;
    private int d_id;
    private String text;
    private int is_vip;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    @Override
    public int getItemType() {
        return ExpandableGiftAdapter.TYPE_LEVEL_1;
    }
}

