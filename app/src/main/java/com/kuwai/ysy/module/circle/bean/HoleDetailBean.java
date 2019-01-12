package com.kuwai.ysy.module.circle.bean;

public class HoleDetailBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"t_id":1,"title":"这是我的树洞标题","text":"树洞树洞树洞","create_time":"2018-11-19 14:42:50","views":10,"comment":0,"reward":0,"open_comment":1,"anonymous_chat":0,"t_uid":1}
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
         * t_id : 1
         * title : 这是我的树洞标题
         * text : 树洞树洞树洞
         * create_time : 2018-11-19 14:42:50
         * views : 10
         * comment : 0
         * reward : 0
         * open_comment : 1
         * anonymous_chat : 0
         * t_uid : 1
         */

        private int t_id;
        private String title;
        private String text;
        private String create_time;
        private int views;
        private int comment;
        private int reward;
        private int open_comment;
        private int anonymous_chat;
        private int t_uid;
        private String chat_room;

        public String getChat_room() {
            return chat_room;
        }

        public void setChat_room(String chat_room) {
            this.chat_room = chat_room;
        }

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public int getOpen_comment() {
            return open_comment;
        }

        public void setOpen_comment(int open_comment) {
            this.open_comment = open_comment;
        }

        public int getAnonymous_chat() {
            return anonymous_chat;
        }

        public void setAnonymous_chat(int anonymous_chat) {
            this.anonymous_chat = anonymous_chat;
        }

        public int getT_uid() {
            return t_uid;
        }

        public void setT_uid(int t_uid) {
            this.t_uid = t_uid;
        }
    }
}
