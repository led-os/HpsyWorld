package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class GiftPopBean {

    /**
     * code : 200
     * msg : 成功
     * data : [{"g_id":2,"girft_name":"热辣似火","price":"2","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":1,"girft_name":"甜蜜暗恋","price":"1","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":3,"girft_name":"hi,你好","price":"5","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":4,"girft_name":"一心一意","price":"52","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":5,"girft_name":"缘分抱抱","price":"80","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":6,"girft_name":"我们约会吧","price":"99","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":7,"girft_name":"锁定你","price":"188","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":8,"girft_name":"烈焰红唇","price":"199","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":9,"girft_name":"求交往","price":"520","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":10,"girft_name":"爱恋情深","price":"666","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":11,"girft_name":"爱情跑车","price":"888","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":12,"girft_name":"捧在手心","price":"6","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":13,"girft_name":"小甜心","price":"18","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":14,"girft_name":"幸福常伴","price":"66","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":15,"girft_name":"优雅气质","price":"70","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":16,"girft_name":"一生一世","price":"1314","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":17,"girft_name":"追逐自由","price":"128","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":18,"girft_name":"动起来","price":"399","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":19,"girft_name":"踩你AJ","price":"420","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0},{"g_id":20,"girft_name":"乖乖听话","price":"688","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","girft_info":"xxx","exchange_goods":0}]
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
         * g_id : 2
         * girft_name : 热辣似火
         * price : 2
         * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * girft_info : xxx
         * exchange_goods : 0
         */

        private int g_id;
        private String girft_name;
        private String price;
        private String girft_img_url;
        private String girft_info;
        private int exchange_goods;
        public boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGirft_img_url() {
            return girft_img_url;
        }

        public void setGirft_img_url(String girft_img_url) {
            this.girft_img_url = girft_img_url;
        }

        public String getGirft_info() {
            return girft_info;
        }

        public void setGirft_info(String girft_info) {
            this.girft_info = girft_info;
        }

        public int getExchange_goods() {
            return exchange_goods;
        }

        public void setExchange_goods(int exchange_goods) {
            this.exchange_goods = exchange_goods;
        }
    }
}
