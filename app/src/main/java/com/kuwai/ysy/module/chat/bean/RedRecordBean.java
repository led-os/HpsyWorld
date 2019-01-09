package com.kuwai.ysy.module.chat.bean;

import java.util.List;

public class RedRecordBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/71eca8f667543c7fdb598483ecb4fd22.JPEG","nickname":"精灵","money_sum":"3.00","number":2,"arr":[{"money":"1.00","update_time":1546738700,"status":1},{"money":"2.00","update_time":1546494733,"status":1}]}
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
         * avatar : http://192.168.1.88/public/static/img/avatar/201901/06/71eca8f667543c7fdb598483ecb4fd22.JPEG
         * nickname : 精灵
         * money_sum : 3.00
         * number : 2
         * arr : [{"money":"1.00","update_time":1546738700,"status":1},{"money":"2.00","update_time":1546494733,"status":1}]
         */

        private String avatar;
        private String nickname;
        private String money_sum;
        private int number;
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

        public String getMoney_sum() {
            return money_sum;
        }

        public void setMoney_sum(String money_sum) {
            this.money_sum = money_sum;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * money : 1.00
             * update_time : 1546738700
             * status : 1
             */

            private String money;
            private int update_time;
            private int status;
            private String nickname;

            public String getNickname() {
                return nickname == null ? "" : nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname == null ? "" : nickname;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
