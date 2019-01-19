package com.kuwai.ysy.module.mine.bean;

public class NoticeSetBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"be_liked":1,"be_visited":1,"system_notification":1}
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
         * be_liked : 1
         * be_visited : 1
         * system_notification : 1
         */

        private int be_liked;
        private int be_visited;
        private int system_notification;

        public int getBe_liked() {
            return be_liked;
        }

        public void setBe_liked(int be_liked) {
            this.be_liked = be_liked;
        }

        public int getBe_visited() {
            return be_visited;
        }

        public void setBe_visited(int be_visited) {
            this.be_visited = be_visited;
        }

        public int getSystem_notification() {
            return system_notification;
        }

        public void setSystem_notification(int system_notification) {
            this.system_notification = system_notification;
        }
    }
}
