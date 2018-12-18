package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class CreditBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"is_real":0,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":0}]
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
         * is_real : 0
         * is_phone : 1
         * is_education : 0
         * is_house : 0
         * is_vehicle : 0
         * is_avatar : 0
         */

        private int is_real;
        private int is_phone;
        private int is_education;
        private int is_house;
        private int is_vehicle;
        private int is_avatar;

        public int getIs_real() {
            return is_real;
        }

        public void setIs_real(int is_real) {
            this.is_real = is_real;
        }

        public int getIs_phone() {
            return is_phone;
        }

        public void setIs_phone(int is_phone) {
            this.is_phone = is_phone;
        }

        public int getIs_education() {
            return is_education;
        }

        public void setIs_education(int is_education) {
            this.is_education = is_education;
        }

        public int getIs_house() {
            return is_house;
        }

        public void setIs_house(int is_house) {
            this.is_house = is_house;
        }

        public int getIs_vehicle() {
            return is_vehicle;
        }

        public void setIs_vehicle(int is_vehicle) {
            this.is_vehicle = is_vehicle;
        }

        public int getIs_avatar() {
            return is_avatar;
        }

        public void setIs_avatar(int is_avatar) {
            this.is_avatar = is_avatar;
        }
    }
}
