package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class TaGiftBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"g_id":5,"girft_name":"锁定你","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"56402","nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"is_vip":1},{"g_id":3,"girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"51482","nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":1},{"g_id":4,"girft_name":"烈焰红唇","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39802","nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"is_vip":1},{"g_id":6,"girft_name":"我们约会吧","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39600","nickname":"aa","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":6,"gender":1,"is_vip":1},{"g_id":26,"girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"26","nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg","uid":1,"gender":1,"is_vip":1},{"g_id":1,"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"17","nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":1}]
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
         * g_id : 5
         * girft_name : 锁定你
         * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * price : 56402
         * nickname : 赵六
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * uid : 5
         * gender : 1
         * is_vip : 1
         */

        private int g_id;
        private String girft_name;
        private String girft_img_url;
        private String price;
        private String nickname;
        private String avatar;
        private int uid;
        private int gender;
        private int is_vip;

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
