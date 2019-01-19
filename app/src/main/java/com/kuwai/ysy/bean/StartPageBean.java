package com.kuwai.ysy.bean;

public class StartPageBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"img_url":"http://api.yushuiyuan.cn/public/static/img/image/201901/15/5fedc21cd931bbb409ad5be363f5f65b.jpg","img_width":1125,"img_height":2436,"start_time":1547806894,"end_time":1552904494,"show_time":2}
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
         * img_url : http://api.yushuiyuan.cn/public/static/img/image/201901/15/5fedc21cd931bbb409ad5be363f5f65b.jpg
         * img_width : 1125
         * img_height : 2436
         * start_time : 1547806894
         * end_time : 1552904494
         * show_time : 2
         */

        private String img_url;
        private int img_width;
        private int img_height;
        private int start_time;
        private int end_time;
        private int show_time;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getImg_width() {
            return img_width;
        }

        public void setImg_width(int img_width) {
            this.img_width = img_width;
        }

        public int getImg_height() {
            return img_height;
        }

        public void setImg_height(int img_height) {
            this.img_height = img_height;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getShow_time() {
            return show_time;
        }

        public void setShow_time(int show_time) {
            this.show_time = show_time;
        }
    }
}
