package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class MyAskBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"p_id":5,"problem":"喝了什么酒？","answer":"不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝"},{"p_id":10,"problem":"吃了没啊","answer":"没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢没呢"},{"p_id":12,"problem":"什么事情","answer":"什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么什么"}]
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
         * p_id : 5
         * problem : 喝了什么酒？
         * answer : 不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝不喝
         */

        private int p_id;
        private String problem;
        private String answer;

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
