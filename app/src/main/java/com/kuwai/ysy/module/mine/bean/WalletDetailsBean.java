package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class WalletDetailsBean  {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"type":"-","number":"6.00","reason":"约会礼物","create_time":1545214180},{"type":"-","number":"15.00","reason":"用户打赏","create_time":1545185006},{"type":"-","number":"25.00","reason":"帖子打赏","create_time":1545184433},{"type":"-","number":"25.00","reason":"帖子打赏","create_time":1545184300},{"type":"-","number":"1.00","reason":"查看素颜消费","create_time":1545133831}]
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
         * type : -
         * number : 6.00
         * reason : 约会礼物
         * create_time : 1545214180
         */

        private String type;
        private String number;
        private String reason;
        private int create_time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
