package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class CloseBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/19/082ec0e6baad17840abcd83ad19e233d.jpg","nickname":"忘川","uid":10045,"constellation":"1996-01-01","education":"大专","gender":2,"sig":"","height":165,"weight":0,"age":"23","intimate_sum":"2.00","v_id":3,"online":0,"ident":0},{"avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","nickname":"ysy_1_2019","uid":10029,"constellation":"2018-01-01","education":"大专","gender":2,"sig":"","height":165,"weight":0,"age":"1","intimate_sum":"2.00","v_id":0,"online":0,"ident":0}]
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
         * avatar : http://test.yushuiyuan.cn/public/static/img/avatar/201901/19/082ec0e6baad17840abcd83ad19e233d.jpg
         * nickname : 忘川
         * uid : 10045
         * constellation : 1996-01-01
         * education : 大专
         * gender : 2
         * sig :
         * height : 165
         * weight : 0
         * age : 23
         * intimate_sum : 2.00
         * v_id : 3
         * online : 0
         * ident : 0
         */

        private String avatar;
        private String nickname;
        private int uid;
        private String constellation;
        private String education;
        private int gender;
        private String sig;
        private int height;
        private int weight;
        private String age;
        private double intimate_sum;
        private int v_id;
        private int online;
        private int ident;

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

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getIntimate_sum() {
            return intimate_sum;
        }

        public void setIntimate_sum(double intimate_sum) {
            this.intimate_sum = intimate_sum;
        }

        public int getV_id() {
            return v_id;
        }

        public void setV_id(int v_id) {
            this.v_id = v_id;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public int getIdent() {
            return ident;
        }

        public void setIdent(int ident) {
            this.ident = ident;
        }
    }
}
