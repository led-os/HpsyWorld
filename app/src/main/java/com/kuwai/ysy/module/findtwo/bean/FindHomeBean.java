package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class FindHomeBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"banner":[{"img":"http://192.168.0.124/public/static/img/image/201901/11/f73abf5fbce55a807aa64d8500abca1f.jpg","link":"http://api.yushuiyuan.cn/h5/invitation-app.html","type":0}],"appointment":[{"r_id":5,"sincerity":6,"name":"游戏","other":"","consumption_type":0,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"age":"1"},{"r_id":4,"sincerity":6,"name":"游戏","other":"","consumption_type":0,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"age":"1"},{"r_id":3,"sincerity":1,"name":"美食","other":"","consumption_type":0,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"age":"1"},{"r_id":2,"sincerity":4,"name":"旅行","other":"","consumption_type":0,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"age":"1"}],"activity":[{"uid":10029,"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","aid":7,"title":"爱在姑苏相亲交友联谊活动","text":"于千万人之中，\n于千万年之中，\n遇见你所要遇见的人，\n时间无涯的荒野里，\n没有早一步，也没有晚一步，\n刚巧赶上了，那也没有别的话可说，\n唯有轻轻地问一声：\u201c咦，你也在这里？\u201d\n爱情，在姑苏古城，不期而遇。\n\n活动对象：\n  1、22-40岁独身男女。\n  2、欢迎企业、事业单位HR部门组织公司内部单身员工参加）\n  3、请暂分居两地，没打算和另外一半分开的莫参加。\n  4、人数：100人以内（根据先后报名人数的男女比例进行预留席位，尽量做到男女比例基本均匀）\n\n主办单位:苏州鱼水缘\n承办单位:苏州酷外文化传媒有限公司\n媒体支持:中国航拍网","attach":"http://192.168.0.124/public/static/img/image/201901/20/34b29bcadf7fa74da23e078c6a0ee374.jpg","enrolment":100,"status":1,"sum":2,"is_collect":0,"heat":1}]}
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
        private List<AppointmentBean> appointment;
        private List<ActivityBean> activity;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
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
             * img : http://192.168.0.124/public/static/img/image/201901/11/f73abf5fbce55a807aa64d8500abca1f.jpg
             * link : http://api.yushuiyuan.cn/h5/invitation-app.html
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

        public static class AppointmentBean {
            /**
             * r_id : 5
             * sincerity : 6
             * name : 游戏
             * other :
             * consumption_type : 0
             * nickname : ysy_1_2019
             * avatar : http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg
             * uid : 10029
             * gender : 2
             * age : 1
             */

            private int r_id;
            private int sincerity;
            private String name;
            private String other;
            private int consumption_type;
            private String nickname;
            private String avatar;
            private int uid;
            private int gender;
            private String age;

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

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }

        public static class ActivityBean {
            /**
             * uid : 10029
             * nickname : ysy_1_2019
             * avatar : http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg
             * aid : 7
             * title : 爱在姑苏相亲交友联谊活动
             * text : 于千万人之中，
             于千万年之中，
             遇见你所要遇见的人，
             时间无涯的荒野里，
             没有早一步，也没有晚一步，
             刚巧赶上了，那也没有别的话可说，
             唯有轻轻地问一声：“咦，你也在这里？”
             爱情，在姑苏古城，不期而遇。

             活动对象：
               1、22-40岁独身男女。
               2、欢迎企业、事业单位HR部门组织公司内部单身员工参加）
               3、请暂分居两地，没打算和另外一半分开的莫参加。
               4、人数：100人以内（根据先后报名人数的男女比例进行预留席位，尽量做到男女比例基本均匀）

             主办单位:苏州鱼水缘
             承办单位:苏州酷外文化传媒有限公司
             媒体支持:中国航拍网
             * attach : http://192.168.0.124/public/static/img/image/201901/20/34b29bcadf7fa74da23e078c6a0ee374.jpg
             * enrolment : 100
             * status : 1
             * sum : 2
             * is_collect : 0
             * heat : 1
             */

            private int uid;
            private String nickname;
            private String avatar;
            private int aid;
            private String title;
            private String text;
            private String attach;
            private int enrolment;
            private int status;
            private int sum;
            private int is_collect;
            private float heat;

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

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
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

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public float getHeat() {
                return heat;
            }

            public void setHeat(float heat) {
                this.heat = heat;
            }
        }
    }
}
