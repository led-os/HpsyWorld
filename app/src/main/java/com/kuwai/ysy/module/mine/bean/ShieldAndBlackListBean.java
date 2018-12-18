package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class ShieldAndBlackListBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"shield":[{"uid":2,"avatar":"http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"222","gender":1,"create_time":1542609770,"s_id":4,"is_vip":1}],"user_blacklist":[{"uid":2,"avatar":"http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"222","gender":1,"create_time":1542011959,"b_id":3,"is_vip":1}]}
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
        private List<ShieldBean> shield;
        private List<UserBlacklistBean> user_blacklist;

        public List<ShieldBean> getShield() {
            return shield;
        }

        public void setShield(List<ShieldBean> shield) {
            this.shield = shield;
        }

        public List<UserBlacklistBean> getUser_blacklist() {
            return user_blacklist;
        }

        public void setUser_blacklist(List<UserBlacklistBean> user_blacklist) {
            this.user_blacklist = user_blacklist;
        }

        public static class ShieldBean {
            /**
             * uid : 2
             * avatar : http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * nickname : 222
             * gender : 1
             * create_time : 1542609770
             * s_id : 4
             * is_vip : 1
             */

            private int uid;
            private String avatar;
            private String nickname;
            private int gender;
            private int create_time;
            private int s_id;
            private int is_vip;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }
        }

        public static class UserBlacklistBean {
            /**
             * uid : 2
             * avatar : http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * nickname : 222
             * gender : 1
             * create_time : 1542011959
             * b_id : 3
             * is_vip : 1
             */

            private int uid;
            private String avatar;
            private String nickname;
            private int gender;
            private int create_time;
            private int b_id;
            private int is_vip;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getB_id() {
                return b_id;
            }

            public void setB_id(int b_id) {
                this.b_id = b_id;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }
        }
    }
}
