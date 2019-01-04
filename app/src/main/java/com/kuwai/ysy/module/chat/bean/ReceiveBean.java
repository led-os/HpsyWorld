package com.kuwai.ysy.module.chat.bean;

public class ReceiveBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"精灵","update_time":1545976994,"money":"36.00","message":"咯"}
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
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * nickname : 精灵
         * update_time : 1545976994
         * money : 36.00
         * message : 咯
         */

        private String avatar;
        private String nickname;
        private int update_time;
        private String money;
        private String message;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
