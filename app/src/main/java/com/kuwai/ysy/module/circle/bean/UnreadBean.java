package com.kuwai.ysy.module.circle.bean;

public class UnreadBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"gift":1,"likes":2,"comment":2}
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
         * gift : 1
         * likes : 2
         * comment : 2
         */

        private int gift;
        private int likes;
        private int comment;

        public int getGift() {
            return gift;
        }

        public void setGift(int gift) {
            this.gift = gift;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }
    }
}
