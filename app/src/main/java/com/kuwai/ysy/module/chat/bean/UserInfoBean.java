package com.kuwai.ysy.module.chat.bean;

public class UserInfoBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"uid":1,"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","sig":"你去哪我跟哪，反正我没地方去","is_vip":1,"vip_grade":3,"payment":0}
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
         * uid : 1
         * nickname : 无所谓
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * sig : 你去哪我跟哪，反正我没地方去
         * is_vip : 1
         * vip_grade : 3
         * payment : 0
         */

        private int uid;
        private String nickname;
        private String avatar;
        private String sig;
        private int is_vip;
        private int vip_grade;
        private int payment;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getVip_grade() {
            return vip_grade;
        }

        public void setVip_grade(int vip_grade) {
            this.vip_grade = vip_grade;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
        }
    }
}
