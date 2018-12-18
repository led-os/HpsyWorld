package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class DyCommentListBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"张三","uid":2,"gender":1,"d_c_id":3,"text":"动态写的非常的好","attachment":"0","good":0,"comment":0,"update_time":1542956989,"sub":[{"nickname":"ee","d_c_sid":4,"uid":10,"other_uid":3,"text":"动态写的非常的好","update_time":1542597350,"other_nickname":"李四"}],"wahtcommentgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"李四","uid":3,"gender":1,"d_c_id":2,"text":"写的真好","attachment":"0","good":0,"comment":0,"update_time":1542956989,"sub":[{"nickname":"张三","d_c_sid":3,"uid":2,"other_uid":3,"text":"动态写的非常的好","update_time":1542597350,"other_nickname":"李四"},{"nickname":"张三","d_c_sid":2,"uid":2,"other_uid":3,"text":"动态写的非常的好","update_time":1542597320,"other_nickname":"李四"}],"wahtcommentgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"11110","uid":1,"gender":1,"d_c_id":1,"text":"动态真low","attachment":"0","good":0,"comment":0,"update_time":1542956989,"sub":[],"wahtcommentgood":0,"is_vip":1}]
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
         * nickname : 张三
         * uid : 2
         * gender : 1
         * d_c_id : 3
         * text : 动态写的非常的好
         * attachment : 0
         * good : 0
         * comment : 0
         * update_time : 1542956989
         * sub : [{"nickname":"ee","d_c_sid":4,"uid":10,"other_uid":3,"text":"动态写的非常的好","update_time":1542597350,"other_nickname":"李四"}]
         * wahtcommentgood : 0
         * is_vip : 1
         */

        private String avatar;
        private String nickname;
        private int uid;
        private int gender;
        private int d_c_id;
        private String text;
        private String attachment;
        private int good;
        private int comment;
        private int update_time;
        private int wahtcommentgood;
        private int is_vip;
        private List<SubBean> sub;
        private int t_c_id;

        public int getT_c_id() {
            return t_c_id;
        }

        public void setT_c_id(int t_c_id) {
            this.t_c_id = t_c_id;
        }

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

        public int getD_c_id() {
            return d_c_id;
        }

        public void setD_c_id(int d_c_id) {
            this.d_c_id = d_c_id;
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

        public List<SubBean> getSub() {
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * nickname : ee
             * d_c_sid : 4
             * uid : 10
             * other_uid : 3
             * text : 动态写的非常的好
             * update_time : 1542597350
             * other_nickname : 李四
             */

            private String nickname;
            private int d_c_sid;
            private int uid;
            private int other_uid;
            private String text;
            private int update_time;
            private String other_nickname;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getD_c_sid() {
                return d_c_sid;
            }

            public void setD_c_sid(int d_c_sid) {
                this.d_c_sid = d_c_sid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getOther_uid() {
                return other_uid;
            }

            public void setOther_uid(int other_uid) {
                this.other_uid = other_uid;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getOther_nickname() {
                return other_nickname;
            }

            public void setOther_nickname(String other_nickname) {
                this.other_nickname = other_nickname;
            }
        }
    }
}
