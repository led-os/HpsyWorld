package com.kuwai.ysy.module.mine.bean;

public class MyWalletBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"amount":"0.00","forward_amount":"0.00"}
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
         * amount : 0.00
         * forward_amount : 0.00
         */

        private String amount;
        private String forward_amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getForward_amount() {
            return forward_amount;
        }

        public void setForward_amount(String forward_amount) {
            this.forward_amount = forward_amount;
        }
    }
}
