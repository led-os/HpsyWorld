package com.kuwai.ysy.module.find.bean.appointment;

import java.util.List;

public class MyAppointMent {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"r_id":7,"name":"电影","s_id":2,"release_time":1541829029,"other":"","consumption_type":2,"gift":null,"earnest_money":1,"Message":null,"status":3,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"age":18,"gender":1,"region_name":"吴中区","sign":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}],"sign_sum":1,"rest_time":-1},{"r_id":6,"name":"其它","s_id":100,"release_time":1541829029,"other":"喝酒","consumption_type":1,"gift":null,"earnest_money":0,"Message":null,"status":0,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"age":18,"gender":1,"region_name":"吴中区","sign":[],"sign_sum":0,"rest_time":-1},{"r_id":5,"name":"其它","s_id":100,"release_time":1541829029,"other":null,"consumption_type":1,"gift":null,"earnest_money":0,"Message":null,"status":0,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"age":18,"gender":1,"region_name":"吴中区","sign":[],"sign_sum":0,"rest_time":-1},{"r_id":4,"name":"美食","s_id":1,"release_time":1541829029,"other":null,"consumption_type":1,"gift":null,"earnest_money":0,"Message":null,"status":0,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"age":18,"gender":1,"region_name":"吴中区","sign":[],"sign_sum":0,"rest_time":-1},{"r_id":1,"name":"美食","s_id":1,"release_time":1541829029,"other":null,"consumption_type":0,"gift":"{\"1\": \"2\", \"3\": \"4\", \"5\": \"6\"}","earnest_money":10,"Message":"一起约会吧","status":4,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"age":18,"gender":1,"region_name":"吴中区","sign":{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},"sign_sum":"","rest_time":-1}]
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
         * r_id : 7
         * name : 电影
         * s_id : 2
         * release_time : 1541829029
         * other :
         * consumption_type : 2
         * gift : null
         * earnest_money : 1
         * Message : null
         * status : 3
         * nickname : 11110
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * uid : 1
         * age : 18
         * gender : 1
         * region_name : 吴中区
         * sign : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}]
         * sign_sum : 1
         * rest_time : -1
         */

        private int r_id;
        private String name;
        private int s_id;
        private int release_time;
        private String other;
        private int consumption_type;
        private Object gift;
        private int earnest_money;
        private Object Message;
        private int status;
        private String nickname;
        private String avatar;
        private int uid;
        private int age;
        private int gender;
        private String region_name;
        private int sign_sum;
        private int rest_time;
        private List<SignBean> sign;

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getS_id() {
            return s_id;
        }

        public void setS_id(int s_id) {
            this.s_id = s_id;
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

        public Object getGift() {
            return gift;
        }

        public void setGift(Object gift) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public int getSign_sum() {
            return sign_sum;
        }

        public void setSign_sum(int sign_sum) {
            this.sign_sum = sign_sum;
        }

        public int getRest_time() {
            return rest_time;
        }

        public void setRest_time(int rest_time) {
            this.rest_time = rest_time;
        }

        public List<SignBean> getSign() {
            return sign;
        }

        public void setSign(List<SignBean> sign) {
            this.sign = sign;
        }

        public static class SignBean {
            /**
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private String avatar;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
