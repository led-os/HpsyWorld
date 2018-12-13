package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class MyBlindBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"aid":2,"title":"双十二走起来","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","start_time":1542255984,"end_time":1542256984,"status":1,"area":"江苏-苏州市-吴中区","detailed_address":"木渎镇"},{"aid":1,"title":"一起来约会","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","start_time":1542245984,"end_time":1542246984,"status":1,"area":"江苏-苏州市-吴中区","detailed_address":"木渎镇"}]
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
         * start_time : 1542255984
         * end_time : 1542256984
         * status : 1
         * area : 江苏-苏州市-吴中区
         * detailed_address : 木渎镇
         */

        private int aid;
        private String title;
        private String attach;
        private int start_time;
        private int end_time;
        private int status;
        private String area;
        private String detailed_address;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }
    }
}
