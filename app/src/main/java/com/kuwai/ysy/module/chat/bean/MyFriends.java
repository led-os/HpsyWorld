package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class MyFriends {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"uid":2,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":"1993-11-11","gender":2,"height":45,"education":"大专以下","annual_income":2,"sig":"自由自在","is_vip":0},{"uid":3,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":"1993-11-11","gender":1,"height":45,"education":"大专以下","annual_income":3,"sig":"自由自在","is_vip":0}]
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
         * uid : 2
         * nickname : 222
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * age : 1993-11-11
         * gender : 2
         * height : 45
         * education : 大专以下
         * annual_income : 2
         * sig : 自由自在
         * is_vip : 0
         */

        private int uid;
        private String nickname;
        private String avatar;
        private String age;
        private int gender;
        private int height;
        private String education;
        private int annual_income;
        private String sig;
        private int is_vip;

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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getAnnual_income() {
            return annual_income;
        }

        public void setAnnual_income(int annual_income) {
            this.annual_income = annual_income;
        }

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
