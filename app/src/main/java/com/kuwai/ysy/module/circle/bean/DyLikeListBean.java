package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class DyLikeListBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"is_good":1,"good":[{"d_id":2,"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"张三","uid":2,"gender":1,"create_time":1542334782,"is_vip":1}]}
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
         * is_good : 1
         * good : [{"d_id":2,"avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"张三","uid":2,"gender":1,"create_time":1542334782,"is_vip":1}]
         */

        private int is_good;
        private List<GoodBean> good;

        public int getIs_good() {
            return is_good;
        }

        public void setIs_good(int is_good) {
            this.is_good = is_good;
        }

        public List<GoodBean> getGood() {
            return good;
        }

        public void setGood(List<GoodBean> good) {
            this.good = good;
        }

        public static class GoodBean {
            /**
             * d_id : 2
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * nickname : 张三
             * uid : 2
             * gender : 1
             * create_time : 1542334782
             * is_vip : 1
             */

            private int d_id;
            private String avatar;
            private String nickname;
            private int uid;
            private int gender;
            private int create_time;
            private int is_vip;

            public int getD_id() {
                return d_id;
            }

            public void setD_id(int d_id) {
                this.d_id = d_id;
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
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
