package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class CityMeetBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"r_id":6,"release_time":1541829029,"other":"喝酒","consumption_type":1,"gift":"0","earnest_money":0,"Message":null,"s_id":100,"name":"其它","uid":1,"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"},{"r_id":5,"release_time":1541829029,"other":null,"consumption_type":1,"gift":"0","earnest_money":0,"Message":null,"s_id":100,"name":"其它","uid":1,"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"},{"r_id":4,"release_time":1541829029,"other":null,"consumption_type":1,"gift":"0","earnest_money":0,"Message":null,"s_id":1,"name":"美食","uid":1,"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"},{"r_id":3,"release_time":1541829029,"other":null,"consumption_type":2,"gift":"0","earnest_money":0,"Message":"哈哈哈","s_id":1,"name":"美食","uid":3,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"},{"r_id":2,"release_time":1541829029,"other":null,"consumption_type":1,"gift":"0","earnest_money":0,"Message":"来玩呀","s_id":2,"name":"电影","uid":2,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"},{"r_id":1,"release_time":1541829029,"other":null,"consumption_type":0,"gift":"{\"1\": \"2\", \"3\": \"4\", \"5\": \"6\"}","earnest_money":10,"Message":"一起约会吧","s_id":1,"name":"美食","uid":1,"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"region_name":"吴中区"}]
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
         * r_id : 6
         * release_time : 1541829029
         * other : 喝酒
         * consumption_type : 1
         * gift : 0
         * earnest_money : 0
         * Message : null
         * s_id : 100
         * name : 其它
         * uid : 1
         * nickname : 我是一号
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * gender : 1
         * age : 18
         * region_name : 吴中区
         */

        private int r_id;
        private int release_time;
        private String other;
        private int consumption_type;
        private String gift;
        private int earnest_money;
        private Object Message;
        private int s_id;
        private String name;
        private int uid;
        private String nickname;
        private String avatar;
        private int gender;
        private int age;
        private String region_name;

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getRelease_time() {
            return release_time;
        }

        public void setRelease_time(int release_time) {
            this.release_time = release_time;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public int getConsumption_type() {
            return consumption_type;
        }

        public void setConsumption_type(int consumption_type) {
            this.consumption_type = consumption_type;
        }

        public String getGift() {
            return gift == null ? "":gift;
        }

        public void setGift(String gift) {
            this.gift = gift;
        }

        public int getEarnest_money() {
            return earnest_money;
        }

        public void setEarnest_money(int earnest_money) {
            this.earnest_money = earnest_money;
        }

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object Message) {
            this.Message = Message;
        }

        public int getS_id() {
            return s_id;
        }

        public void setS_id(int s_id) {
            this.s_id = s_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }
    }
}
