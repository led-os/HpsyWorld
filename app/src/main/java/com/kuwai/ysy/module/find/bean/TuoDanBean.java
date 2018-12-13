package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class TuoDanBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"aid":2,"title":"双十二走起来","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","enrolment":2000,"registration_fee":"99.00","status":1,"text":"五六七八"},{"aid":1,"title":"一起来约会","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","enrolment":1000,"registration_fee":"0.00","status":1,"text":"一二三"}]
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
         * aid : 2
         * title : 双十二走起来
         * attach : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * enrolment : 2000
         * registration_fee : 99.00
         * status : 1
         * text : 五六七八
         */

        private int aid;
        private String title;
        private String attach;
        private int enrolment;
        private String registration_fee;
        private int status;
        private String text;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public int getEnrolment() {
            return enrolment;
        }

        public void setEnrolment(int enrolment) {
            this.enrolment = enrolment;
        }

        public String getRegistration_fee() {
            return registration_fee;
        }

        public void setRegistration_fee(String registration_fee) {
            this.registration_fee = registration_fee;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
