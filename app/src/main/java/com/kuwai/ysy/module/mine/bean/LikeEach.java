package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class LikeEach {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"uid":105,"nickname":"qq888","avatar":"http://192.168.1.88/public/static/img/avatar/201901/02/534b1d9e110ac69d5eb6dad71ffce689.jpg","age":"1997-03-03","gender":1,"height":170,"education":null,"annual_income":"5W-8W","is_vip":1},{"uid":143,"nickname":"扉扉","avatar":"http://192.168.1.88/public/static/img/avatar/201901/12/79d3c58ea4733c69cbc55ef3075c86c1.jpg","age":"1994-11-15","gender":2,"height":165,"education":null,"annual_income":"15W-20W","is_vip":1}]
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
         * uid : 105
         * nickname : qq888
         * avatar : http://192.168.1.88/public/static/img/avatar/201901/02/534b1d9e110ac69d5eb6dad71ffce689.jpg
         * age : 1997-03-03
         * gender : 1
         * height : 170
         * education : null
         * annual_income : 5W-8W
         * is_vip : 1
         */

        private int uid;
        private String nickname;
        private String avatar;
        private String age;
        private int gender;
        private int height;
        private Object education;
        private String annual_income;
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

        public Object getEducation() {
            return education;
        }

        public void setEducation(Object education) {
            this.education = education;
        }

        public String getAnnual_income() {
            return annual_income;
        }

        public void setAnnual_income(String annual_income) {
            this.annual_income = annual_income;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
