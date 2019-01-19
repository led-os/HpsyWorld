package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class FootCity {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"f_id":2,"region_name":"南通市"},{"f_id":1,"region_name":"苏州市"}]
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
         * f_id : 2
         * region_name : 南通市
         */

        private int f_id;
        private String region_name;
        public boolean canEdit;

        public int getF_id() {
            return f_id;
        }

        public void setF_id(int f_id) {
            this.f_id = f_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }
    }
}
