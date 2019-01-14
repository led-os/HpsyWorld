package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class NoticeDateBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"nickname":"峰","avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","uid":104,"gender":1,"age":"0","nid":5,"content":"茫茫人海中，希望你能够选中我。期待与你的约会","r_id":17,"create_time":1546753338,"type":1,"read":0,"is_vip":1,"online":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickname : 峰
         * avatar : http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg
         * uid : 104
         * gender : 1
         * age : 0
         * nid : 5
         * content : 茫茫人海中，希望你能够选中我。期待与你的约会
         * r_id : 17
         * create_time : 1546753338
         * type : 1
         * read : 0
         * is_vip : 1
         * online : 0
         */

        private String nickname;
        private String avatar;
        private int uid;
        private int gender;
        private String age;
        private int nid;
        private String content;
        private int r_id;
        private int create_time;
        private int type;
        private int read;
        private int is_vip;
        private int online;

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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }
    }
}
