package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class HoleCommentListBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"李四","uid":3,"gender":1,"t_c_id":3,"text":"xxx","attachment":"0","good":0,"comment":0,"update_time":1542959537,"sub":[],"wahtcommentgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"张三","uid":2,"gender":1,"t_c_id":2,"text":"xxx","attachment":"0","good":0,"comment":0,"update_time":1542959537,"sub":[],"wahtcommentgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"赵六","uid":5,"gender":1,"t_c_id":1,"text":"xxx","attachment":"0","good":0,"comment":0,"update_time":1542959537,"sub":[],"wahtcommentgood":0,"is_vip":1}]
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
         * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
         * nickname : 李四
         * uid : 3
         * gender : 1
         * t_c_id : 3
         * text : xxx
         * attachment : 0
         * good : 0
         * comment : 0
         * update_time : 1542959537
         * sub : []
         * wahtcommentgood : 0
         * is_vip : 1
         */

        private String avatar;
        private String nickname;
        private int uid;
        private int gender;
        private int t_c_id;
        private String text;
        private String attachment;
        private int good;
        private int comment;
        private int update_time;
        private int wahtcommentgood;
        private int is_vip;
        private List<?> sub;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getT_c_id() {
            return t_c_id;
        }

        public void setT_c_id(int t_c_id) {
            this.t_c_id = t_c_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getWahtcommentgood() {
            return wahtcommentgood;
        }

        public void setWahtcommentgood(int wahtcommentgood) {
            this.wahtcommentgood = wahtcommentgood;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public List<?> getSub() {
            return sub;
        }

        public void setSub(List<?> sub) {
            this.sub = sub;
        }
    }
}
