package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class WheelBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"u_id":1,"name":"无"},{"u_id":2,"name":"有"}]
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
         * u_id : 1
         * name : 无
         */

        private int u_id;
        private String name;

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            //重写该方法，作为选择器显示的名称
            return name;
        }
    }
}
