package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class GiftWithdrawalsBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"nickname":"张三","uid":1,"avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","arr":[{"g_id":27,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"2","g_nums":3,"create_time":1543387823,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":26,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"2","g_nums":10,"create_time":1543387781,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":2,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":4,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":1,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":3,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":5,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":5,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1}]},{"nickname":"李四","uid":3,"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","arr":[{"g_id":12,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"2","g_nums":90,"create_time":1543387521,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3}]}]
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
         * nickname : 张三
         * uid : 1
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg
         * arr : [{"g_id":27,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"2","g_nums":3,"create_time":1543387823,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":26,"type":"1","girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"2","g_nums":10,"create_time":1543387781,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":2,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":4,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":1,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":3,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":2,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1},{"g_id":5,"type":"1","girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","exchange_goods":0,"price":"1","g_nums":5,"create_time":1542604694,"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg","uid":1}]
         */

        private String nickname;
        private int uid;
        private String avatar;
        private List<ArrBean> arr;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * g_id : 27
             * type : 1
             * girft_name : 热辣似火
             * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * exchange_goods : 0
             * price : 2
             * g_nums : 3
             * create_time : 1543387823
             * nickname : 张三
             * avatar : http://192.168.1.88/public/static/img/avatar/201812/18/8f957d13c4a4341aef6300f9c87faf76.jpg
             * uid : 1
             */

            private int g_id;
            private String type;
            private String girft_name;
            private String girft_img_url;
            private int exchange_goods;
            private String price;
            private int g_nums;
            private int create_time;
            private String nickname;
            private String avatar;
            private int uid;

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

            public int getExchange_goods() {
                return exchange_goods;
            }

            public void setExchange_goods(int exchange_goods) {
                this.exchange_goods = exchange_goods;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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
        }
    }
}
