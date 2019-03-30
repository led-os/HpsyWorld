package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class MeetYaoBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"r_id":27,"sincerity":4,"name":"旅行","girl_friend":0,"consumption_type":0,"is_shuttle":1,"is_carry":0,"return_time":1553472000,"trip_mode":"往返双飞","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553385600,"other":"","message":"","address_name":"东华度假村","status":0,"film_name":"","cover":"","sign":[{"avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","nickname":"五娃","age":"35","gender":1}],"sign_sum":2},{"r_id":26,"sincerity":2,"name":"电影","girl_friend":1,"consumption_type":0,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553385600,"other":"","message":"","address_name":"任意电影院","status":4,"film_name":"夜伴歌声","cover":"http://test.yushuiyuan.cn/public/static/img/image/201903/22/p2548652745.jpgbcrswxy234.jpg","sign":[{"avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/18/1328b247ef35cec182d288adb1c14771404.jpg","nickname":"峰","age":"0","gender":1}],"sign_sum":1},{"r_id":24,"sincerity":7,"name":"游乐","girl_friend":1,"consumption_type":1,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553299200,"other":"","message":"","address_name":"南浜人家棋牌室","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":23,"sincerity":3,"name":"运动","girl_friend":0,"consumption_type":0,"is_shuttle":1,"is_carry":1,"return_time":0,"trip_mode":"","motion_name":"舞蹈","game_theme":"","game_area":"","online_time":"","release_time":1553299200,"other":"","message":"","address_name":"珠江路121号嘉业阳光假日A区53幢","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":22,"sincerity":6,"name":"游戏","girl_friend":1,"consumption_type":0,"is_shuttle":0,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"全军出击","game_area":"QQ区","online_time":"晚上","release_time":0,"other":"","message":"","address_name":"","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":19,"sincerity":100,"name":"其它","girl_friend":1,"consumption_type":0,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1554076800,"other":"开车兜风","message":"","address_name":"木渎镇金枫路216号东创科技园","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":18,"sincerity":7,"name":"游乐","girl_friend":1,"consumption_type":2,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553212800,"other":"","message":"","address_name":"木渎金山南路288号影视城3号1楼","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":17,"sincerity":5,"name":"唱歌","girl_friend":1,"consumption_type":1,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553299200,"other":"","message":"","address_name":"渔洋街与金山南路交叉口东南150米","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":16,"sincerity":4,"name":"旅行","girl_friend":1,"consumption_type":2,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1553299200,"other":"","message":"","address_name":"银泉山庄大酒店附近","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0},{"r_id":10,"sincerity":3,"name":"运动","girl_friend":1,"consumption_type":0,"is_shuttle":1,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"高尔夫","game_theme":"","game_area":"","online_time":"","release_time":1553299200,"other":"","message":"","address_name":"广特青少年足球俱乐部","status":0,"film_name":"","cover":"","sign":[],"sign_sum":0}]
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
         * r_id : 27
         * sincerity : 4
         * name : 旅行
         * girl_friend : 0
         * consumption_type : 0
         * is_shuttle : 1
         * is_carry : 0
         * return_time : 1553472000
         * trip_mode : 往返双飞
         * motion_name :
         * game_theme :
         * game_area :
         * online_time :
         * release_time : 1553385600
         * other :
         * message :
         * address_name : 东华度假村
         * status : 0
         * film_name :
         * cover :
         * sign : [{"avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg","nickname":"五娃","age":"35","gender":1}]
         * sign_sum : 2
         */

        private int r_id;
        private int sincerity;
        private String name;
        private int girl_friend;
        private int consumption_type;
        private int is_shuttle;
        private int is_carry;
        private int return_time;
        private String trip_mode;
        private String motion_name;
        private String game_theme;
        private String game_area;
        private String online_time;
        private int release_time;
        private String other;
        private String message;
        private String address_name;
        private int status;
        private String film_name;
        private String cover;
        private int sign_sum;
        private List<SignBean> sign;

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getSincerity() {
            return sincerity;
        }

        public void setSincerity(int sincerity) {
            this.sincerity = sincerity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGirl_friend() {
            return girl_friend;
        }

        public void setGirl_friend(int girl_friend) {
            this.girl_friend = girl_friend;
        }

        public int getConsumption_type() {
            return consumption_type;
        }

        public void setConsumption_type(int consumption_type) {
            this.consumption_type = consumption_type;
        }

        public int getIs_shuttle() {
            return is_shuttle;
        }

        public void setIs_shuttle(int is_shuttle) {
            this.is_shuttle = is_shuttle;
        }

        public int getIs_carry() {
            return is_carry;
        }

        public void setIs_carry(int is_carry) {
            this.is_carry = is_carry;
        }

        public int getReturn_time() {
            return return_time;
        }

        public void setReturn_time(int return_time) {
            this.return_time = return_time;
        }

        public String getTrip_mode() {
            return trip_mode;
        }

        public void setTrip_mode(String trip_mode) {
            this.trip_mode = trip_mode;
        }

        public String getMotion_name() {
            return motion_name;
        }

        public void setMotion_name(String motion_name) {
            this.motion_name = motion_name;
        }

        public String getGame_theme() {
            return game_theme;
        }

        public void setGame_theme(String game_theme) {
            this.game_theme = game_theme;
        }

        public String getGame_area() {
            return game_area;
        }

        public void setGame_area(String game_area) {
            this.game_area = game_area;
        }

        public String getOnline_time() {
            return online_time;
        }

        public void setOnline_time(String online_time) {
            this.online_time = online_time;
        }

        public int getRelease_time() {
            return release_time;
        }

        public void setRelease_time(int release_time) {
            this.release_time = release_time;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFilm_name() {
            return film_name;
        }

        public void setFilm_name(String film_name) {
            this.film_name = film_name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getSign_sum() {
            return sign_sum;
        }

        public void setSign_sum(int sign_sum) {
            this.sign_sum = sign_sum;
        }

        public List<SignBean> getSign() {
            return sign;
        }

        public void setSign(List<SignBean> sign) {
            this.sign = sign;
        }

        public static class SignBean {
            /**
             * avatar : http://test.yushuiyuan.cn/public/static/img/avatar/201901/16/f339cad8173d3a08b7ef233ee5e1a11d.jpg
             * nickname : 五娃
             * age : 35
             * gender : 1
             */

            private String avatar;
            private String nickname;
            private String age;
            private int gender;

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

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }
        }
    }
}
