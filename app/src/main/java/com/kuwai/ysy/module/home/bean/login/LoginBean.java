package com.kuwai.ysy.module.home.bean.login;

public class LoginBean {


    /**
     * code : 200
     * msg : 登录成功
     * data : {"uid":1,"nickname":"张三","avatar":"http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","rongyun_token":"JVWU8lWKIaWQPDd2SAgEu3E1zqv3fUuCD78xqUOChyW60iuZOWe4/7s9pN4pU7+LS1f+ULq9uQ2moZSGwcSI/g==","phone":"17717512301","integral_grade":1,"age":"25","gender":1,"amount":"81.98","integral_sum":1757,"is_vip":1}
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
         * uid : 1
         * nickname : 张三
         * avatar : http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * rongyun_token : JVWU8lWKIaWQPDd2SAgEu3E1zqv3fUuCD78xqUOChyW60iuZOWe4/7s9pN4pU7+LS1f+ULq9uQ2moZSGwcSI/g==
         * phone : 17717512301
         * integral_grade : 1
         * age : 25
         * gender : 1
         * amount : 81.98
         * integral_sum : 1757
         * is_vip : 1
         */

        private int uid;
        private String nickname;
        private String avatar;
        private String rongyun_token;
        private String phone;
        private int integral_grade;
        private String age;
        private int gender;
        private String amount;
        private int integral_sum;
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

        public String getRongyun_token() {
            return rongyun_token;
        }

        public void setRongyun_token(String rongyun_token) {
            this.rongyun_token = rongyun_token;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getIntegral_grade() {
            return integral_grade;
        }

        public void setIntegral_grade(int integral_grade) {
            this.integral_grade = integral_grade;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getIntegral_sum() {
            return integral_sum;
        }

        public void setIntegral_sum(int integral_sum) {
            this.integral_sum = integral_sum;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
