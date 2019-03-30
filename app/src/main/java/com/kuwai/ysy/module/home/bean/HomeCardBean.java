package com.kuwai.ysy.module.home.bean;

import java.util.List;

public class HomeCardBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"uid":10005,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","nickname":"五娃","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/19/48db17a72fddbae66af8a8d0a1b75ff1.jpg","img_width":"1984.00","img_height":"1120.00"}],"gender":1,"age":"35","height":189,"education":"本科","annual_income":"8W-10W","sig":"爱情不是你想买，想买就能买","lastcity":"","constellation":"1984-01-01","weight":0},{"uid":10029,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/21/95d64938b8a82208794ac8894fbcdcd1.jpg","nickname":"ysy_1_2019","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/16/83ebf0c96ff6f0ddafd89845551d5586.jpeg","img_width":"1280.00","img_height":"800.00"}],"gender":2,"age":"1","height":165,"education":"大专","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"2018-01-01","weight":0},{"uid":10006,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/581aa1d554b832b92765e0b31b8ad1fd.JPEG","nickname":"SanDy","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/19/c4a03761326f89ceed1ba8b0b43b984d.JPEG","img_width":"1740.00","img_height":"2320.00"}],"gender":2,"age":"29","height":175,"education":"硕士","annual_income":"8W-10W","sig":"不知道下一站在哪","lastcity":"","constellation":"1990-01-01","weight":0},{"uid":10002,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/21/e2a4bdb57a1958f882bb2b5e6bde13b3.jpg","nickname":"要减肥的战斗机","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/19/b90ca765762433f0d624d861e52c3beb.png","img_width":"108.00","img_height":"108.00"}],"gender":1,"age":"31","height":170,"education":"本科","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"1987-12-24","weight":0},{"uid":10004,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/206456e9134526f81fdb242bf831d268.jpg","nickname":"扉扉","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/16/d3ff4278e755519d04d51803e69b4e1d.jpg","img_width":"828.00","img_height":"1222.00"}],"gender":2,"age":"24","height":165,"education":"本科","annual_income":"8W-10W","sig":"幸福就是：雨天能为你撑起一把小伞；幸福就是：牵你的小手与你共度夕阳；幸福就是：你永远开心快乐！","lastcity":"","constellation":"1994-11-15","weight":0},{"uid":10042,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/d5eaf1c4aba42ca2245908b16ad8aacf.jpg","nickname":"小冉","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/20/fb4542e52d2d8cec0fe704606092fd93.jpg","img_width":"670.00","img_height":"920.00"}],"gender":2,"age":"25","height":161,"education":"本科","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"1993-12-01","weight":0},{"uid":10016,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/83954e240a6e40b23e320fc430e30192.jpg","nickname":"小芳","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/15/0fef7fcdc57d2ecf76891991aaec6c8c.jpg","img_width":"700.00","img_height":"700.00"}],"gender":2,"age":"20","height":175,"education":"本科","annual_income":"8W-10W","sig":"真心找对象","lastcity":"","constellation":"1998-10-15","weight":0},{"uid":10003,"avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/f993ff34ee76648d4a82c7f5abe70ffc.jpg","nickname":"峰","attach":[{"img":"http://192.168.0.124/public/static/img/image/201901/15/6fd9bd368602cb669690c993e444c7c6.jpg","img_width":"640.00","img_height":"640.00"}],"gender":1,"age":"19","height":178,"education":"硕士","annual_income":"8W-10W","sig":"","lastcity":"","constellation":"2000-01-15","weight":0}]
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
         * uid : 10005
         * avatar : http://192.168.0.124/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg
         * nickname : 五娃
         * attach : [{"img":"http://192.168.0.124/public/static/img/image/201901/19/48db17a72fddbae66af8a8d0a1b75ff1.jpg","img_width":"1984.00","img_height":"1120.00"}]
         * gender : 1
         * age : 35
         * height : 189
         * education : 本科
         * annual_income : 8W-10W
         * sig : 爱情不是你想买，想买就能买
         * lastcity :
         * constellation : 1984-01-01
         * weight : 0
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
        private float distance;
        private List<AttachBean> attach;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
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

        public List<AttachBean> getAttach() {
            return attach;
        }

        public void setAttach(List<AttachBean> attach) {
            this.attach = attach;
        }

        public static class AttachBean {
            /**
             * img : http://192.168.0.124/public/static/img/image/201901/19/48db17a72fddbae66af8a8d0a1b75ff1.jpg
             * img_width : 1984.00
             * img_height : 1120.00
             */

            private String img;
            private String img_width;
            private String img_height;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImg_width() {
                return img_width;
            }

            public void setImg_width(String img_width) {
                this.img_width = img_width;
            }

            public String getImg_height() {
                return img_height;
            }

            public void setImg_height(String img_height) {
                this.img_height = img_height;
            }
        }
    }
}
