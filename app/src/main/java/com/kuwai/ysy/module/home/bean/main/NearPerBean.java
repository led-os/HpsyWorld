package com.kuwai.ysy.module.home.bean.main;

import java.util.List;

public class NearPerBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"uid":10003,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/f993ff34ee76648d4a82c7f5abe70ffc.jpg","nickname":"峰","gender":1,"age":"19","height":178,"education":"硕士","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"2000-01-15","weight":0,"login_time":1548318913,"online":0,"distance":0},{"uid":10004,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/206456e9134526f81fdb242bf831d268.jpg","nickname":"扉扉","gender":2,"age":"24","height":165,"education":"本科","annual_income":"8W-10W","sig":"幸福就是：雨天能为你撑起一把小伞；幸福就是：牵你的小手与你共度夕阳；幸福就是：你永远开心快乐！","lastcity":"","constellation":"1994-11-15","weight":0,"login_time":1548036115,"online":0,"distance":0},{"uid":10005,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","nickname":"五娃","gender":1,"age":"35","height":189,"education":"本科","annual_income":"8W-10W","sig":"爱情不是你想买，想买就能买","lastcity":"","constellation":"1984-01-01","weight":0,"login_time":1548147416,"online":0,"distance":0},{"uid":10006,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/581aa1d554b832b92765e0b31b8ad1fd.JPEG","nickname":"SanDy","gender":2,"age":"29","height":175,"education":"硕士","annual_income":"8W-10W","sig":"不知道下一站在哪","lastcity":"","constellation":"1990-01-01","weight":0,"login_time":1548036891,"online":0,"distance":0},{"uid":10007,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/04b028cbaa15a2df631e7c8db9b1c595.jpg","nickname":"帅哥","gender":1,"age":"29","height":178,"education":"硕士","annual_income":"8W-10W","sig":"可爱的你偷走我的情、盗走我的心，我决定告你上法庭，该判你什么罪呢？法官翻遍所有的犯罪记录和案例，最后陪审团一致通过：判你终生归我。","lastcity":"","constellation":"1990-01-15","weight":0,"login_time":1547630675,"online":0,"distance":0},{"uid":10029,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/21/95d64938b8a82208794ac8894fbcdcd1.jpg","nickname":"ysy_1_2019","gender":2,"age":"1","height":165,"education":"大专","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"2018-01-01","weight":0,"login_time":1551496179,"online":0,"distance":0},{"uid":10033,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/17/2995f383ca5ea9fe373b0868b830cb75.jpg","nickname":"生煎小羊","gender":2,"age":"22","height":171,"education":"海外留学","annual_income":"15W-20W","sig":"","lastcity":"","constellation":"1996-11-10","weight":0,"login_time":1548036039,"online":0,"distance":0},{"uid":10034,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/85f9066c37370d2fcacca446797de8f5.jpg","nickname":"dear胡胡","gender":2,"age":"27","height":160,"education":"大专","annual_income":"8W-10W","sig":"何以解忧？","lastcity":"","constellation":"1992-03-12","weight":0,"login_time":1547971063,"online":0,"distance":0},{"uid":10036,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/18/1328b247ef35cec182d288adb1c14771404.jpg","nickname":"峰","gender":1,"age":"0","height":178,"education":"硕士","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"2019-01-18","weight":0,"login_time":1552097230,"online":0,"distance":0},{"uid":10002,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/21/e2a4bdb57a1958f882bb2b5e6bde13b3.jpg","nickname":"要减肥的战斗机","gender":1,"age":"31","height":170,"education":"本科","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"1987-12-24","weight":0,"login_time":1548034524,"online":0,"distance":1}]
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
         * uid : 10003
         * avatar : http://192.168.0.124/public/static/img/avatar/201901/15/f993ff34ee76648d4a82c7f5abe70ffc.jpg
         * nickname : 峰
         * gender : 1
         * age : 19
         * height : 178
         * education : 硕士
         * annual_income : 8W-10W
         * sig :
         * lastcity :
         * constellation : 2000-01-15
         * weight : 0
         * login_time : 1548318913
         * online : 0
         * distance : 0
         */

        private int uid;
        private String avatar;
        private String nickname;
        private int gender;
        private String age;
        private int height;
        private String education;
        private String annual_income;
        private String sig;
        private String lastcity;
        private String constellation;
        private int weight;
        private int login_time;
        private int online;
        private double distance;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

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

        public String getAnnual_income() {
            return annual_income;
        }

        public void setAnnual_income(String annual_income) {
            this.annual_income = annual_income;
        }

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public String getLastcity() {
            return lastcity;
        }

        public void setLastcity(String lastcity) {
            this.lastcity = lastcity;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getLogin_time() {
            return login_time;
        }

        public void setLogin_time(int login_time) {
            this.login_time = login_time;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}
