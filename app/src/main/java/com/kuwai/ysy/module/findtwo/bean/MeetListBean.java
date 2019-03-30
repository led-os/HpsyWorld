package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class MeetListBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"uid":10044,"nickname":"七娃","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201901/18/b0ab0387d1dbbfc495f260133abf006e.jpg","gender":1,"age":"25","r_id":6,"r_img":["http://test.yushuiyuan.cn/public/static/img/image/201903/22/4908329507ef753a6ecdd0d9d26e9c4a.jpg","http://test.yushuiyuan.cn/public/static/img/image/201903/22/4373c8183fe833288fec0df7d38f7af9.jpg"],"sincerity":1,"name":"美食","girl_friend":1,"consumption_type":1,"is_shuttle":2,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1554076800,"other":"","message":"","address":"木渎镇金枫路216号","film_name":"","cover":"","address_name":"TT国际1","distance":0},{"uid":10029,"nickname":"ysy_1_2019","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","gender":2,"age":"1","r_id":5,"r_img":[],"sincerity":6,"name":"游戏","girl_friend":2,"consumption_type":0,"is_shuttle":0,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"刺激战场","game_area":"微信区","online_time":"晚上","release_time":0,"other":"","message":"嘿嘿嘿","address":"","film_name":"惊奇队长","cover":"http://test.yushuiyuan.cn/public/static/img/image/201903/20/p2548870813.jpgbdefiory29.jpg","address_name":"","distance":0},{"uid":10029,"nickname":"ysy_1_2019","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","gender":2,"age":"1","r_id":4,"r_img":["http://test.yushuiyuan.cn/public/static/img/image/201903/19/645b7cab8cb4ed29009517257fae5f32.jpg"],"sincerity":6,"name":"游戏","girl_friend":1,"consumption_type":0,"is_shuttle":0,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"王者荣耀","game_area":"QQ区","online_time":"白天","release_time":0,"other":"","message":"哈哈哈","address":"","film_name":"","cover":"","address_name":"","distance":0},{"uid":10029,"nickname":"ysy_1_2019","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","gender":2,"age":"1","r_id":3,"r_img":["http://test.yushuiyuan.cn/public/static/img/image/201901/20/be70b746390b7ae0d42dc3859e650610.png"],"sincerity":1,"name":"美食","girl_friend":0,"consumption_type":0,"is_shuttle":0,"is_carry":0,"return_time":0,"trip_mode":"","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1552961624,"other":"","message":"xxx","address":"国瑞地产xxx","film_name":"","cover":"","address_name":"国瑞地产","distance":0},{"uid":10029,"nickname":"ysy_1_2019","avatar":"http://test.yushuiyuan.cn/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","gender":2,"age":"1","r_id":2,"r_img":["http://test.yushuiyuan.cn/public/static/img/image/201901/20/be70b746390b7ae0d42dc3859e650610.png"],"sincerity":4,"name":"旅行","girl_friend":0,"consumption_type":0,"is_shuttle":0,"is_carry":0,"return_time":1552961653,"trip_mode":"自驾","motion_name":"","game_theme":"","game_area":"","online_time":"","release_time":1552961624,"other":"","message":"","address":"","film_name":"","cover":"","address_name":"","distance":0}]
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
         * uid : 10044
         * nickname : 七娃
         * avatar : http://test.yushuiyuan.cn/public/static/img/avatar/201901/18/b0ab0387d1dbbfc495f260133abf006e.jpg
         * gender : 1
         * age : 25
         * r_id : 6
         * r_img : ["http://test.yushuiyuan.cn/public/static/img/image/201903/22/4908329507ef753a6ecdd0d9d26e9c4a.jpg","http://test.yushuiyuan.cn/public/static/img/image/201903/22/4373c8183fe833288fec0df7d38f7af9.jpg"]
         * sincerity : 1
         * name : 美食
         * girl_friend : 1
         * consumption_type : 1
         * is_shuttle : 2
         * is_carry : 0
         * return_time : 0
         * trip_mode :
         * motion_name :
         * game_theme :
         * game_area :
         * online_time :
         * release_time : 1554076800
         * other :
         * message :
         * address : 木渎镇金枫路216号
         * film_name :
         * cover :
         * address_name : TT国际1
         * distance : 0
         */

        private int uid;
        private String nickname;
        private String avatar;
        private int gender;
        private String age;
        private String r_id;
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
        private String address;
        private String film_name;
        private String cover;
        private String address_name;
        private float distance;
        private List<String> r_img;
        private String lastarea;

        public String getLastarea() {
            return lastarea == null ? "" : lastarea;
        }

        public void setLastarea(String lastarea) {
            this.lastarea = lastarea == null ? "" : lastarea;
        }

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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public List<String> getR_img() {
            return r_img;
        }

        public void setR_img(List<String> r_img) {
            this.r_img = r_img;
        }
    }
}
