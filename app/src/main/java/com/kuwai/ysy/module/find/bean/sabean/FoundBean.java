package com.kuwai.ysy.module.find.bean.sabean;

import java.util.List;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class FoundBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"banner":[{"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","link":"www.baidu.com","type":0},{"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","link":"www.mi.com","type":0},{"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","link":"www.mi.com","type":0}],"news":[{"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}],"appointment":[{"r_id":7,"name":"电影","other":"","consumption_type":2,"uid":1,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"distance":""},{"r_id":6,"name":"其它","other":"喝酒","consumption_type":1,"uid":1,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"distance":""},{"r_id":5,"name":"美食","other":null,"consumption_type":1,"uid":1,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"distance":""},{"r_id":4,"name":"美食","other":null,"consumption_type":1,"uid":1,"nickname":"11110","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"distance":""},{"r_id":3,"name":"美食","other":null,"consumption_type":2,"uid":1,"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","gender":1,"age":18,"distance":""}],"activity":[{"aid":2,"title":"双十二走起来","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","enrolment":2000,"status":1},{"aid":1,"title":"一起来约会","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","enrolment":1000,"status":1}]}
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
        private List<BannerBean> banner;
        private List<NewsBean> news;
        private List<AppointmentBean> appointment;
        private List<ActivityBean> activity;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<AppointmentBean> getAppointment() {
            return appointment;
        }

        public void setAppointment(List<AppointmentBean> appointment) {
            this.appointment = appointment;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class BannerBean {
            /**
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * link : www.baidu.com
             * type : 0
             */

            private String img;
            private String link;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class NewsBean {
            /**
             * nickname : 11110
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private String nickname;
            private String avatar;

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
        }

        public static class AppointmentBean {
            /**
             * r_id : 7
             * name : 电影
             * other :
             * consumption_type : 2
             * uid : 1
             * nickname : 11110
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * gender : 1
             * age : 18
             * distance :
             */

            private int r_id;
            private String name;
            private String other;
            private int consumption_type;
            private int uid;
            private String nickname;
            private String avatar;
            private int gender;
            private int age;
            private String distance;

            public int getR_id() {
                return r_id;
            }

            public void setR_id(int r_id) {
                this.r_id = r_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOther() {
                return other;
            }

            public void setOther(String other) {
                this.other = other;
            }

            public int getConsumption_type() {
                return consumption_type;
            }

            public void setConsumption_type(int consumption_type) {
                this.consumption_type = consumption_type;
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

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }

        public static class ActivityBean {
            /**
             * aid : 2
             * title : 双十二走起来
             * attach : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * enrolment : 2000
             * status : 1
             */

            private int aid;
            private String title;
            private String attach;
            private int enrolment;
            private int status;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAttach() {
                return attach;
            }

            public void setAttach(String attach) {
                this.attach = attach;
            }

            public int getEnrolment() {
                return enrolment;
            }

            public void setEnrolment(int enrolment) {
                this.enrolment = enrolment;
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
