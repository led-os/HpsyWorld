package com.kuwai.ysy.module.find.bean.appointment;

import java.util.List;

public class MyCommis {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"r_id":7,"release_time":1541829029,"status":3,"name":"电影","s_id":2,"uid":1,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":18,"gender":1},{"r_id":3,"release_time":1541829029,"status":3,"name":"美食","s_id":1,"uid":3,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":18,"gender":1},{"r_id":2,"release_time":1541829029,"status":0,"name":"电影","s_id":2,"uid":2,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":18,"gender":1}]
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
         * release_time : 1541829029
         * status : 3
         * name : 电影
         * s_id : 2
         * uid : 1
         * nickname : 11110
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * age : 18
         * gender : 1
         */

        private int r_id;
        private int release_time;
        private int status;
        private String name;
        private int s_id;
        private int uid;
        private String nickname;
        private String avatar;
        private int age;
        private int gender;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
    }
}
