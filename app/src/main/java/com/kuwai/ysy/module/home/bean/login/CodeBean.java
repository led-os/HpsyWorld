package com.kuwai.ysy.module.home.bean.login;

public class CodeBean {


    /**
     * code : 200
     * data : {"phone":"13585968596","msgTxt":311124,"limtTime":20,"type":"A"}
     * msg : success
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * phone : 13585968596
         * msgTxt : 311124
         * limtTime : 20
         * type : A
         */

        private String phone;
        private int msgTxt;
        private int limtTime;
        private String type;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getMsgTxt() {
            return msgTxt;
        }

        public void setMsgTxt(int msgTxt) {
            this.msgTxt = msgTxt;
        }

        public int getLimtTime() {
            return limtTime;
        }

        public void setLimtTime(int limtTime) {
            this.limtTime = limtTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
