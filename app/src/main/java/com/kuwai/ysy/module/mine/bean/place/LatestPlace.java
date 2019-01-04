package com.kuwai.ysy.module.mine.bean.place;

import java.util.List;

public class LatestPlace {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"f_id":4,"place":"金枫路","distance":0},{"f_id":2,"place":"木渎小学","distance":0}]
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
         * f_id : 4
         * place : 金枫路
         * distance : 0
         */

        private int f_id;
        private String place;
        private int distance;
        public boolean canEdit = false;

        public int getF_id() {
            return f_id;
        }

        public void setF_id(int f_id) {
            this.f_id = f_id;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
