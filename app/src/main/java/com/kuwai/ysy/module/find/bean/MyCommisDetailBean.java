package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class MyCommisDetailBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"r_id":1,"name":"美食","other":"","release_time":1541829029,"Message":"一起约会吧","earnest_money":10,"girl_friend":2,"consumption_type":0,"uid":1,"nickname":"我是一号","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":"25","region_name":"吴中区","sign":[{"r_d_id":2,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"age":"25","gender":1},{"r_d_id":1,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"age":"25","gender":1}],"gift":[{"girft_name":"甜蜜暗恋","girft_img_url":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":5},{"girft_name":"hi,你好","girft_img_url":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":1}]}
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
         * r_id : 1
         * name : 美食
         * other :
         * release_time : 1541829029
         * Message : 一起约会吧
         * earnest_money : 10
         * girl_friend : 2
         * consumption_type : 0
         * uid : 1
         * nickname : 我是一号
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * gender : 1
         * age : 25
         * region_name : 吴中区
         * sign : [{"r_d_id":2,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"age":"25","gender":1},{"r_d_id":1,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"age":"25","gender":1}]
         * gift : [{"girft_name":"甜蜜暗恋","girft_img_url":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":5},{"girft_name":"hi,你好","girft_img_url":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":1}]
         */

        private int r_id;
        private String name;
        private String other;
        private int release_time;
        private String Message;
        private int earnest_money;
        private int girl_friend;
        private int consumption_type;
        private int uid;
        private String nickname;
        private String avatar;
        private int gender;
        private String age;
        private String region_name;
        private List<SignBean> sign;
        private List<GiftBean> gift;

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

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public int getRelease_time() {
            return release_time;
        }

        public void setRelease_time(int release_time) {
            this.release_time = release_time;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public int getEarnest_money() {
            return earnest_money;
        }

        public void setEarnest_money(int earnest_money) {
            this.earnest_money = earnest_money;
        }

        public int getGirl_friend() {
            return girl_friend;
        }

        public void setGirl_friend(int girl_friend) {
            this.girl_friend = girl_friend;
        }

        public int getConsumption_type() {
            return consumption_type;
        }

        public void setConsumption_type(int consumption_type) {
            this.consumption_type = consumption_type;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public List<SignBean> getSign() {
            return sign;
        }

        public void setSign(List<SignBean> sign) {
            this.sign = sign;
        }

        public List<GiftBean> getGift() {
            return gift;
        }

        public void setGift(List<GiftBean> gift) {
            this.gift = gift;
        }

        public static class SignBean {
            /**
             * r_d_id : 2
             * nickname : 李四
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * uid : 3
             * age : 25
             * gender : 1
             */

            private int r_d_id;
            private String nickname;
            private String avatar;
            private int uid;
            private String age;
            private int gender;

            public int getR_d_id() {
                return r_d_id;
            }

            public void setR_d_id(int r_d_id) {
                this.r_d_id = r_d_id;
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
        }

        public static class GiftBean {
            /**
             * girft_name : 甜蜜暗恋
             * girft_img_url : public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * g_nums : 5
             */

            private String girft_name;
            private String girft_img_url;
            private int g_nums;

            public String getGirft_name() {
                return girft_name;
            }

            public void setGirft_name(String girft_name) {
                this.girft_name = girft_name;
            }

            public String getGirft_img_url() {
                return girft_img_url;
            }

            public void setGirft_img_url(String girft_img_url) {
                this.girft_img_url = girft_img_url;
            }

            public int getG_nums() {
                return g_nums;
            }

            public void setG_nums(int g_nums) {
                this.g_nums = g_nums;
            }
        }
    }
}
