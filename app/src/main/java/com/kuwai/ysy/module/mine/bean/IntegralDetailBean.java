package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class IntegralDetailBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"name":"登录","create_time":1545010571,"type":"+","integral":10},{"name":"动态树洞内容点赞","create_time":1544786657,"type":"+","integral":5},{"name":"积分兑换","create_time":1544778131,"type":"-","integral":100},{"name":"签到","create_time":1544778018,"type":"+","integral":40},{"name":"动态树洞内容评论","create_time":1544771846,"type":"+","integral":5},{"name":"动态树洞内容评论","create_time":1544771711,"type":"+","integral":5},{"name":"动态树洞内容评论","create_time":1544771627,"type":"+","integral":5},{"name":"动态树洞内容评论","create_time":1544771607,"type":"+","integral":5},{"name":"动态树洞内容评论","create_time":1544771471,"type":"+","integral":5},{"name":"动态、树洞发布","create_time":1544766032,"type":"+","integral":10}]
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
         * name : 登录
         * create_time : 1545010571
         * type : +
         * integral : 10
         */

        private String name;
        private int create_time;
        private String type;
        private int integral;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }
}
