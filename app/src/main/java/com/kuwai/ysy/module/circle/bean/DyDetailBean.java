package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class DyDetailBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"11110","uid":1,"age":18,"gender":1,"height":null,"education":null,"annual_income":null,"d_id":2,"text":"动态2","attach":"0","video_id":"","type":0,"good":0,"comment":0,"address":"-","update_time":1542334782,"reward":0,"whatgood":1}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * nickname : 11110
         * uid : 1
         * age : 18
         * gender : 1
         * height : null
         * education : null
         * annual_income : null
         * d_id : 2
         * text : 动态2
         * attach : 0
         * video_id :
         * type : 0
         * good : 0
         * comment : 0
         * address : -
         * update_time : 1542334782
         * reward : 0
         * whatgood : 1
         */

        private String avatar;
        private String nickname;
        private int uid;
        private int age;
        private int gender;
        private String height;
        private String education;
        private String annual_income;
        private int d_id;
        private String text;
        private List<String> attach;
        private String video_id;
        private int type;
        private int good;
        private int comment;
        private String address;
        private int update_time;
        private int reward;
        private int whatgood;

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

        public Object getHeight() {
            return height;
        }

        public void setHeight(String height) {
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

        public List<String> getAttach() {
            return attach;
        }

        public void setAttach(List<String> attach) {
            this.attach = attach;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public int getWhatgood() {
            return whatgood;
        }

        public void setWhatgood(int whatgood) {
            this.whatgood = whatgood;
        }
    }
}
