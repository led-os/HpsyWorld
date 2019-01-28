package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class ChatQuestion {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"p_id":29,"uid":10008,"problem":"现在在哪里工作？","answer":"我在苏州","is_answer":0},{"p_id":30,"uid":10008,"problem":"打算什么时候结婚生孩子？","answer":"感情到位随时结果生娃！","is_answer":0}]
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
         * p_id : 29
         * uid : 10008
         * problem : 现在在哪里工作？
         * answer : 我在苏州
         * is_answer : 0
         */

        private int p_id;
        private int uid;
        private String problem;
        private String answer;
        private int is_answer;

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public int getIs_answer() {
            return is_answer;
        }

        public void setIs_answer(int is_answer) {
            this.is_answer = is_answer;
        }
    }
}
