package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class RedMySendBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"avatar":"http://localhost/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","money":"1.00","message":"zzz","arr":[{"avatar":"http://localhost/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","update_time":1545357059,"money":"1.00"}]}
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
         * avatar : http://localhost/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * nickname : 张三
         * money : 1.00
         * message : zzz
         * arr : [{"avatar":"http://localhost/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","update_time":1545357059,"money":"1.00"}]
         */

        private String avatar;
        private String nickname;
        private String money;
        private String message;
        private List<ArrBean> arr;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * avatar : http://localhost/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
             * nickname : 张三
             * update_time : 1545357059
             * money : 1.00
             */

            private String avatar;
            private String nickname;
            private int update_time;
            private String money;

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

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
