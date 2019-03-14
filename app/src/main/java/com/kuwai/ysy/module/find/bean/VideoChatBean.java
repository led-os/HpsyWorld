package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class VideoChatBean {
    /**
     * code : 200
     * msg : 获取成功
     * data : {"sum":8,"arr":[{"uid":10041,"gender":2,"nickname":"托尼老师","avatar":"http://192.168.0.124/public/static/img/avatar/201901/18/100bcdelrx047.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10040,"gender":1,"nickname":"无问西东","avatar":"http://192.168.0.124/public/static/img/avatar/201901/18/132bglpswxz46.jpg","lastcity":"","lastarea":"","distance":25.3},{"uid":10007,"gender":1,"nickname":"帅哥","avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/04b028cbaa15a2df631e7c8db9b1c595.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10034,"gender":2,"nickname":"dear胡胡","avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/85f9066c37370d2fcacca446797de8f5.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10015,"gender":1,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/normalheadimage.png","lastcity":"","lastarea":"","distance":12859.3},{"uid":10006,"gender":2,"nickname":"SanDy","avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/581aa1d554b832b92765e0b31b8ad1fd.JPEG","lastcity":"","lastarea":"","distance":9.5},{"uid":10005,"gender":1,"nickname":"五娃","avatar":"http://192.168.0.124/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","lastcity":"","lastarea":"","distance":9.5}]}
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
         * sum : 8
         * arr : [{"uid":10041,"gender":2,"nickname":"托尼老师","avatar":"http://192.168.0.124/public/static/img/avatar/201901/18/100bcdelrx047.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10040,"gender":1,"nickname":"无问西东","avatar":"http://192.168.0.124/public/static/img/avatar/201901/18/132bglpswxz46.jpg","lastcity":"","lastarea":"","distance":25.3},{"uid":10007,"gender":1,"nickname":"帅哥","avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/04b028cbaa15a2df631e7c8db9b1c595.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10034,"gender":2,"nickname":"dear胡胡","avatar":"http://192.168.0.124/public/static/img/avatar/201901/19/85f9066c37370d2fcacca446797de8f5.jpg","lastcity":"","lastarea":"","distance":9.5},{"uid":10015,"gender":1,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/normalheadimage.png","lastcity":"","lastarea":"","distance":12859.3},{"uid":10006,"gender":2,"nickname":"SanDy","avatar":"http://192.168.0.124/public/static/img/avatar/201901/15/581aa1d554b832b92765e0b31b8ad1fd.JPEG","lastcity":"","lastarea":"","distance":9.5},{"uid":10005,"gender":1,"nickname":"五娃","avatar":"http://192.168.0.124/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","lastcity":"","lastarea":"","distance":9.5}]
         */

        private int sum;
        private List<ArrBean> arr;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * uid : 10041
             * gender : 2
             * nickname : 托尼老师
             * avatar : http://192.168.0.124/public/static/img/avatar/201901/18/100bcdelrx047.jpg
             * lastcity :
             * lastarea :
             * distance : 9.5
             */

            private int uid;
            private int gender;
            private String nickname;
            private String avatar;
            private String lastcity;
            private String lastarea;
            private double distance;

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

            public String getLastcity() {
                return lastcity;
            }

            public void setLastcity(String lastcity) {
                this.lastcity = lastcity;
            }

            public String getLastarea() {
                return lastarea;
            }

            public void setLastarea(String lastarea) {
                this.lastarea = lastarea;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }
        }
    }
}
