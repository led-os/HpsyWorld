package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class SportBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"motion_name":"健身"},{"motion_name":"羽毛球"},{"motion_name":"网球"},{"motion_name":"游泳"},{"motion_name":"保龄球"},{"motion_name":"高尔夫"},{"motion_name":"瑜伽"},{"motion_name":"桌球"},{"motion_name":"舞蹈"},{"motion_name":"乒乓球"},{"motion_name":"射箭"},{"motion_name":"骑马"}]
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
         * motion_name : 健身
         */

        private String motion_name;

        public String getMotion_name() {
            return motion_name;
        }

        public void setMotion_name(String motion_name) {
            this.motion_name = motion_name;
        }
    }
}
