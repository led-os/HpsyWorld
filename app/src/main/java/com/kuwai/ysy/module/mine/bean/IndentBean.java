package com.kuwai.ysy.module.mine.bean;

public class IndentBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"examine":0,"create_time":1554191307,"update_time":1554191307}
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
         * examine : 0
         * create_time : 1554191307
         * update_time : 1554191307
         */

        private int examine;
        private int create_time;
        private int update_time;

        public int getExamine() {
            return examine;
        }

        public void setExamine(int examine) {
            this.examine = examine;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}
