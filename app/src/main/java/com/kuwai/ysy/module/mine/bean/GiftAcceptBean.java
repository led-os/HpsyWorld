package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class GiftAcceptBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"sum":11,"gift":[{"g_id":27,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"2","exchange_goods":0,"g_nums":3,"create_time":1543387823,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/14/d362409c5a6ad0114dd156d120e736ff.png","uid":1,"gender":1,"is_vip":1},{"g_id":26,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"2","exchange_goods":0,"g_nums":10,"create_time":1543387781,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/14/d362409c5a6ad0114dd156d120e736ff.png","uid":1,"gender":1,"is_vip":1},{"g_id":5,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":5,"create_time":1542604694,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":0},{"g_id":3,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"is_vip":0},{"g_id":1,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":0},{"g_id":2,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":0},{"g_id":4,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"is_vip":0},{"g_id":3,"type":"2","girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"520","exchange_goods":0,"g_nums":99,"create_time":1542077201,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":0}]}
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
         * sum : 11
         * gift : [{"g_id":27,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"2","exchange_goods":0,"g_nums":3,"create_time":1543387823,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/14/d362409c5a6ad0114dd156d120e736ff.png","uid":1,"gender":1,"is_vip":1},{"g_id":26,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"2","exchange_goods":0,"g_nums":10,"create_time":1543387781,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/14/d362409c5a6ad0114dd156d120e736ff.png","uid":1,"gender":1,"is_vip":1},{"g_id":5,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":5,"create_time":1542604694,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":0},{"g_id":3,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"is_vip":0},{"g_id":1,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":0},{"g_id":2,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":0},{"g_id":4,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"1","exchange_goods":0,"g_nums":2,"create_time":1542604694,"nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"is_vip":0},{"g_id":3,"type":"2","girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"520","exchange_goods":0,"g_nums":99,"create_time":1542077201,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":0}]
         */

        private int sum;
        private List<GiftBean> gift;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<GiftBean> getGift() {
            return gift;
        }

        public void setGift(List<GiftBean> gift) {
            this.gift = gift;
        }

        public static class GiftBean {
            /**
             * g_id : 27
             * type : 1
             * girft_name : 热辣似火
             * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * price : 2
             * exchange_goods : 0
             * g_nums : 3
             * create_time : 1543387823
             * nickname : 张三
             * avatar : http://192.168.1.88/public/static/img/avatar/201812/14/d362409c5a6ad0114dd156d120e736ff.png
             * uid : 1
             * gender : 1
             * is_vip : 1
             */

            private int g_id;
            private String type;
            private String girft_name;
            private String girft_img_url;
            private String price;
            private int exchange_goods;
            private int g_nums;
            private int create_time;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public int getExchange_goods() {
                return exchange_goods;
            }

            public void setExchange_goods(int exchange_goods) {
                this.exchange_goods = exchange_goods;
            }

            public int getG_nums() {
                return g_nums;
            }

            public void setG_nums(int g_nums) {
                this.g_nums = g_nums;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
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
}
