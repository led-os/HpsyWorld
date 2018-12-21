package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class WithdrawalsRecordBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"money":"10.00","alipay_id":"18297638234","alipay_name":"肖哥","update_time":1544009393,"status":0}]
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
         * money : 10.00
         * alipay_id : 18297638234
         * alipay_name : 肖哥
         * update_time : 1544009393
         * status : 0
         */

        private String money;
        private String alipay_id;
        private String alipay_name;
        private int update_time;
        private int status;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAlipay_id() {
            return alipay_id;
        }

        public void setAlipay_id(String alipay_id) {
            this.alipay_id = alipay_id;
        }

        public String getAlipay_name() {
            return alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
