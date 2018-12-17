package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class GiftBoxBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"girft_name":"我们约会吧","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"400"},{"girft_name":"锁定你","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"300"},{"girft_name":"烈焰红唇","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"200"},{"girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"99"},{"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"23"},{"girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","g_nums":"13"}]
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
         * girft_name : 我们约会吧
         * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * g_nums : 400
         */

        private String girft_name;
        private String girft_img_url;
        private String g_nums;

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

        public String getG_nums() {
            return g_nums;
        }

        public void setG_nums(String g_nums) {
            this.g_nums = g_nums;
        }
    }
}
