package com.kuwai.ysy.module.mine.bean.user;

public class UserInfo {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"is_real":0,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":0,"integral_sum":1697,"grade":1,"amount":"82.00","Authentication_sum":5,"is_vip":1}
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
         * nickname : 我是一号
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * uid : 1
         * is_real : 0
         * is_phone : 1
         * is_education : 0
         * is_house : 0
         * is_vehicle : 0
         * is_avatar : 0
         * integral_sum : 1697
         * grade : 1
         * amount : 82.00
         * Authentication_sum : 5
         * is_vip : 1
         */

        private String nickname;
        private String avatar;
        private int uid;
        private int is_real;
        private int is_phone;
        private int is_education;
        private int is_house;
        private int is_vehicle;
        private int is_avatar;
        private int integral_sum;
        private int grade;
        private String amount;
        private int Authentication_sum;
        private int is_vip;

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

        public int getIs_real() {
            return is_real;
        }

        public void setIs_real(int is_real) {
            this.is_real = is_real;
        }

        public int getIs_phone() {
            return is_phone;
        }

        public void setIs_phone(int is_phone) {
            this.is_phone = is_phone;
        }

        public int getIs_education() {
            return is_education;
        }

        public void setIs_education(int is_education) {
            this.is_education = is_education;
        }

        public int getIs_house() {
            return is_house;
        }

        public void setIs_house(int is_house) {
            this.is_house = is_house;
        }

        public int getIs_vehicle() {
            return is_vehicle;
        }

        public void setIs_vehicle(int is_vehicle) {
            this.is_vehicle = is_vehicle;
        }

        public int getIs_avatar() {
            return is_avatar;
        }

        public void setIs_avatar(int is_avatar) {
            this.is_avatar = is_avatar;
        }

        public int getIntegral_sum() {
            return integral_sum;
        }

        public void setIntegral_sum(int integral_sum) {
            this.integral_sum = integral_sum;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getAuthentication_sum() {
            return Authentication_sum;
        }

        public void setAuthentication_sum(int Authentication_sum) {
            this.Authentication_sum = Authentication_sum;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
