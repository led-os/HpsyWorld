package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class NoticeBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"nid":5,"title":"相亲","content":"相相亲相亲相亲相亲相亲相亲亲","create_time":1546050795,"class_id":3,"type":"相亲","img":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","url":"http://api.yushuiyuan.cn/h5/notice-default.html"},{"nid":4,"title":"测算测算","content":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","create_time":1546050794,"class_id":2,"type":"测试","img":"public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","url":"http://api.yushuiyuan.cn/h5/notice-default.html"},{"nid":3,"title":"您有一条物流信息","content":"订单编号：SN0030007476562200","create_time":1546050793,"class_id":1,"type":"物流","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","url":"http://api.yushuiyuan.cn/h5/notice-express.html"},{"nid":2,"title":"您有一条物流信息","content":"订单编号：71468565730715","create_time":1546050792,"class_id":1,"type":"物流","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","url":"http://api.yushuiyuan.cn/h5/notice-express.html"},{"nid":1,"title":"您有一条物流信息","content":"订单编号：75119346942748","create_time":1546050791,"class_id":1,"type":"物流","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","url":"http://api.yushuiyuan.cn/h5/notice-express.html"}]
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
         * nid : 5
         * title : 相亲
         * content : 相相亲相亲相亲相亲相亲相亲亲
         * create_time : 1546050795
         * class_id : 3
         * type : 相亲
         * img : public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * url : http://api.yushuiyuan.cn/h5/notice-default.html
         */

        private int nid;
        private String title;
        private String content;
        private int create_time;
        private int class_id;
        private String type;
        private String img;
        private String url;

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
