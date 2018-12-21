package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class EarlierBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"l_ld":1,"uid":2,"nickname":"3","avatar":"http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":18,"gender":1,"is_vip":1}]
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
         * l_ld : 1
         * uid : 2
         * nickname : 3
         * avatar : http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * age : 18
         * gender : 1
         * is_vip : 1
         */

        private int l_ld;
        private int uid;
        private String nickname;
        private String avatar;
        private int age;
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
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
    }
}
