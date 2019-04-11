package com.kuwai.ysy.module.mine.bean;

import java.io.Serializable;
import java.util.List;

public class PersolHome2PageBean implements Serializable {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"info":{"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"city":"上海-上海市","age":"1","sig":"","marriage":"未婚","children":"否","car_buying":"未购车","buy_house":"未购房","height":165,"annual_income":"8W-10W","occupation":"其它","education":"大专","religion":"无","is_real":2,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":1,"grade":1,"advantages":"0","love_view":"只想简简单单相爱到老","nature_view":"顺其自然","round":"0","birthday":"2018-01-01","weight":0,"login_time":1554798524,"lastcity":"苏州市","lastarea":"吴中区","intimate":"0.00","is_vip":0},"senior":[{"field":"marriage","name":"感情状况","is_see":0,"data":"未婚"},{"field":"children","name":"有无子女","is_see":0,"data":"否"},{"field":"car_buying","name":"购车情况","is_see":0,"data":"未购车"},{"field":"buy_house","name":"购房情况","is_see":0,"data":"未购房"},{"field":"annual_income","name":"年收入","is_see":0,"data":"8W-10W"},{"field":"job","name":"职业","is_see":0,"data":"其它"},{"field":"religion","name":"宗教信仰","is_see":0,"data":"无"},{"field":"advantages","name":"擅长","is_see":1,"data":"未填写"},{"field":"love_view","name":"对爱情的看法","is_see":1,"data":"只想简简单单相爱到老"},{"field":"nature_view","name":"对性的看法","is_see":1,"data":"顺其自然"},{"field":"round","name":"最满意的地方","is_see":1,"data":"未填写"},{"field":"is_children","name":"是否想要小孩","is_see":0,"data":"视情况而定"},{"field":"appointment","name":"偏爱的约会方式","is_see":0,"data":"吃饭，唱歌，看电影"},{"field":"wedding","name":"期待的婚礼形式","is_see":0,"data":"未填写"},{"field":"login_time","name":"最近活跃时间","is_see":1,"data":1547546941}],"selection":[{"s_id":2,"text":"啊嘎嘎"},{"s_id":4,"text":"昂大概"},{"s_id":5,"text":"奥迪嘎嘎"},{"s_id":6,"text":"agag"},{"s_id":7,"text":"阿迪嘎嘎"},{"s_id":8,"text":"444agag"},{"s_id":9,"text":"阿迪嘎嘎"},{"s_id":11,"text":"阿迪嘎嘎发"},{"s_id":12,"text":"444"},{"s_id":13,"text":"111"},{"s_id":14,"text":"222"},{"s_id":15,"text":"111"},{"s_id":16,"text":"222"},{"s_id":17,"text":"111"},{"s_id":18,"text":"222"},{"s_id":19,"text":"111"},{"s_id":20,"text":"222"},{"s_id":21,"text":"111"},{"s_id":22,"text":"222"},{"s_id":23,"text":"111"},{"s_id":24,"text":"222"},{"s_id":25,"text":"111"}],"footprints":[{"f_id":47,"region_name":"盐城市"},{"f_id":46,"region_name":"淮安市"},{"f_id":45,"region_name":"连云港市"},{"f_id":44,"region_name":"南通市"},{"f_id":42,"region_name":"苏州市"}],"gift":[{"girft_name":"捧在手心","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/a968710f27bf1188eed235c09c48a7de.png","g_nums":"1"},{"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/f0e38e9f8c4585928075859c2e3746d5.png","g_nums":"1"},{"girft_name":"热辣似火","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/5d99be5ff7fa4af8b1d8c4c319a90a6c.png","g_nums":"1"}],"gift_sum":3,"reminding_online":0,"love":0}
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
         * info : {"nickname":"ysy_1_2019","avatar":"http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg","uid":10029,"gender":2,"city":"上海-上海市","age":"1","sig":"","marriage":"未婚","children":"否","car_buying":"未购车","buy_house":"未购房","height":165,"annual_income":"8W-10W","occupation":"其它","education":"大专","religion":"无","is_real":2,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":1,"grade":1,"advantages":"0","love_view":"只想简简单单相爱到老","nature_view":"顺其自然","round":"0","birthday":"2018-01-01","weight":0,"login_time":1554798524,"lastcity":"苏州市","lastarea":"吴中区","intimate":"0.00","is_vip":0}
         * senior : [{"field":"marriage","name":"感情状况","is_see":0,"data":"未婚"},{"field":"children","name":"有无子女","is_see":0,"data":"否"},{"field":"car_buying","name":"购车情况","is_see":0,"data":"未购车"},{"field":"buy_house","name":"购房情况","is_see":0,"data":"未购房"},{"field":"annual_income","name":"年收入","is_see":0,"data":"8W-10W"},{"field":"job","name":"职业","is_see":0,"data":"其它"},{"field":"religion","name":"宗教信仰","is_see":0,"data":"无"},{"field":"advantages","name":"擅长","is_see":1,"data":"未填写"},{"field":"love_view","name":"对爱情的看法","is_see":1,"data":"只想简简单单相爱到老"},{"field":"nature_view","name":"对性的看法","is_see":1,"data":"顺其自然"},{"field":"round","name":"最满意的地方","is_see":1,"data":"未填写"},{"field":"is_children","name":"是否想要小孩","is_see":0,"data":"视情况而定"},{"field":"appointment","name":"偏爱的约会方式","is_see":0,"data":"吃饭，唱歌，看电影"},{"field":"wedding","name":"期待的婚礼形式","is_see":0,"data":"未填写"},{"field":"login_time","name":"最近活跃时间","is_see":1,"data":1547546941}]
         * selection : [{"s_id":2,"text":"啊嘎嘎"},{"s_id":4,"text":"昂大概"},{"s_id":5,"text":"奥迪嘎嘎"},{"s_id":6,"text":"agag"},{"s_id":7,"text":"阿迪嘎嘎"},{"s_id":8,"text":"444agag"},{"s_id":9,"text":"阿迪嘎嘎"},{"s_id":11,"text":"阿迪嘎嘎发"},{"s_id":12,"text":"444"},{"s_id":13,"text":"111"},{"s_id":14,"text":"222"},{"s_id":15,"text":"111"},{"s_id":16,"text":"222"},{"s_id":17,"text":"111"},{"s_id":18,"text":"222"},{"s_id":19,"text":"111"},{"s_id":20,"text":"222"},{"s_id":21,"text":"111"},{"s_id":22,"text":"222"},{"s_id":23,"text":"111"},{"s_id":24,"text":"222"},{"s_id":25,"text":"111"}]
         * footprints : [{"f_id":47,"region_name":"盐城市"},{"f_id":46,"region_name":"淮安市"},{"f_id":45,"region_name":"连云港市"},{"f_id":44,"region_name":"南通市"},{"f_id":42,"region_name":"苏州市"}]
         * gift : [{"girft_name":"捧在手心","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/a968710f27bf1188eed235c09c48a7de.png","g_nums":"1"},{"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/f0e38e9f8c4585928075859c2e3746d5.png","g_nums":"1"},{"girft_name":"热辣似火","girft_img_url":"http://192.168.0.124/public/static/img/image/201901/18/5d99be5ff7fa4af8b1d8c4c319a90a6c.png","g_nums":"1"}]
         * gift_sum : 3
         * reminding_online : 0
         * love : 0
         */

        private InfoBean info;
        private int gift_sum;
        private int reminding_online;
        private int love;
        private List<SeniorBean> senior;
        private List<SelectionBean> selection;
        private List<FootprintsBean> footprints;
        private List<GiftBean> gift;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public int getGift_sum() {
            return gift_sum;
        }

        public void setGift_sum(int gift_sum) {
            this.gift_sum = gift_sum;
        }

        public int getReminding_online() {
            return reminding_online;
        }

        public void setReminding_online(int reminding_online) {
            this.reminding_online = reminding_online;
        }

        public int getLove() {
            return love;
        }

        public void setLove(int love) {
            this.love = love;
        }

        public List<SeniorBean> getSenior() {
            return senior;
        }

        public void setSenior(List<SeniorBean> senior) {
            this.senior = senior;
        }

        public List<SelectionBean> getSelection() {
            return selection;
        }

        public void setSelection(List<SelectionBean> selection) {
            this.selection = selection;
        }

        public List<FootprintsBean> getFootprints() {
            return footprints;
        }

        public void setFootprints(List<FootprintsBean> footprints) {
            this.footprints = footprints;
        }

        public List<GiftBean> getGift() {
            return gift;
        }

        public void setGift(List<GiftBean> gift) {
            this.gift = gift;
        }

        public static class InfoBean {
            /**
             * nickname : ysy_1_2019
             * avatar : http://192.168.0.124/public/static/img/avatar/201903/13/4238b906f5041be581a9d693dc90c2c0.jpg
             * uid : 10029
             * gender : 2
             * city : 上海-上海市
             * age : 1
             * sig :
             * marriage : 未婚
             * children : 否
             * car_buying : 未购车
             * buy_house : 未购房
             * height : 165
             * annual_income : 8W-10W
             * occupation : 其它
             * education : 大专
             * religion : 无
             * is_real : 2
             * is_phone : 1
             * is_education : 0
             * is_house : 0
             * is_vehicle : 0
             * is_avatar : 1
             * grade : 1
             * advantages : 0
             * love_view : 只想简简单单相爱到老
             * nature_view : 顺其自然
             * round : 0
             * birthday : 2018-01-01
             * weight : 0
             * login_time : 1554798524
             * lastcity : 苏州市
             * lastarea : 吴中区
             * intimate : 0.00
             * is_vip : 0
             */

            private String nickname;
            private String avatar;
            private int uid;
            private int gender;
            private String city;
            private String age;
            private String sig;
            private String marriage;
            private String children;
            private String car_buying;
            private String buy_house;
            private int height;
            private String annual_income;
            private String occupation;
            private String education;
            private String religion;
            private int is_real;
            private int is_phone;
            private int is_education;
            private int is_house;
            private int is_vehicle;
            private int is_avatar;
            private int grade;
            private String advantages;
            private String love_view;
            private String nature_view;
            private String round;
            private String birthday;
            private int weight;
            private int login_time;
            private String lastcity;
            private String lastarea;
            private String intimate;
            private int is_vip;

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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSig() {
                return sig;
            }

            public void setSig(String sig) {
                this.sig = sig;
            }

            public String getMarriage() {
                return marriage;
            }

            public void setMarriage(String marriage) {
                this.marriage = marriage;
            }

            public String getChildren() {
                return children;
            }

            public void setChildren(String children) {
                this.children = children;
            }

            public String getCar_buying() {
                return car_buying;
            }

            public void setCar_buying(String car_buying) {
                this.car_buying = car_buying;
            }

            public String getBuy_house() {
                return buy_house;
            }

            public void setBuy_house(String buy_house) {
                this.buy_house = buy_house;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getAnnual_income() {
                return annual_income;
            }

            public void setAnnual_income(String annual_income) {
                this.annual_income = annual_income;
            }

            public String getOccupation() {
                return occupation;
            }

            public void setOccupation(String occupation) {
                this.occupation = occupation;
            }

            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;
            }

            public String getReligion() {
                return religion;
            }

            public void setReligion(String religion) {
                this.religion = religion;
            }

            public int getIs_real() {
                return is_real;
            }

            public void setIs_real(int is_real) {
                this.is_real = is_real;
            }

            public int getIs_phone() {
                return is_phone;
            }

            public void setIs_phone(int is_phone) {
                this.is_phone = is_phone;
            }

            public int getIs_education() {
                return is_education;
            }

            public void setIs_education(int is_education) {
                this.is_education = is_education;
            }

            public int getIs_house() {
                return is_house;
            }

            public void setIs_house(int is_house) {
                this.is_house = is_house;
            }

            public int getIs_vehicle() {
                return is_vehicle;
            }

            public void setIs_vehicle(int is_vehicle) {
                this.is_vehicle = is_vehicle;
            }

            public int getIs_avatar() {
                return is_avatar;
            }

            public void setIs_avatar(int is_avatar) {
                this.is_avatar = is_avatar;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getAdvantages() {
                return advantages;
            }

            public void setAdvantages(String advantages) {
                this.advantages = advantages;
            }

            public String getLove_view() {
                return love_view;
            }

            public void setLove_view(String love_view) {
                this.love_view = love_view;
            }

            public String getNature_view() {
                return nature_view;
            }

            public void setNature_view(String nature_view) {
                this.nature_view = nature_view;
            }

            public String getRound() {
                return round;
            }

            public void setRound(String round) {
                this.round = round;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public int getLogin_time() {
                return login_time;
            }

            public void setLogin_time(int login_time) {
                this.login_time = login_time;
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

            public String getIntimate() {
                return intimate;
            }

            public void setIntimate(String intimate) {
                this.intimate = intimate;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }
        }

        public static class SeniorBean {
            /**
             * field : marriage
             * name : 感情状况
             * is_see : 0
             * data : 未婚
             */

            private String field;
            private String name;
            private int is_see;
            private String data;

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIs_see() {
                return is_see;
            }

            public void setIs_see(int is_see) {
                this.is_see = is_see;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }

        public static class SelectionBean {
            /**
             * s_id : 2
             * text : 啊嘎嘎
             */

            private int s_id;
            private String text;

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class FootprintsBean {
            /**
             * f_id : 47
             * region_name : 盐城市
             */

            private int f_id;
            private String region_name;

            public int getF_id() {
                return f_id;
            }

            public void setF_id(int f_id) {
                this.f_id = f_id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }
        }

        public static class GiftBean {
            /**
             * girft_name : 捧在手心
             * girft_img_url : http://192.168.0.124/public/static/img/image/201901/18/a968710f27bf1188eed235c09c48a7de.png
             * g_nums : 1
             */

            private String girft_name;
            private String girft_img_url;
            private String g_nums;

            public String getGirft_name() {
                return girft_name;
            }

            public void setGirft_name(String girft_name) {
                this.girft_name = girft_name;
            }

            public String getGirft_img_url() {
                return girft_img_url;
            }

            public void setGirft_img_url(String girft_img_url) {
                this.girft_img_url = girft_img_url;
            }

            public String getG_nums() {
                return g_nums;
            }

            public void setG_nums(String g_nums) {
                this.g_nums = g_nums;
            }
        }
    }
}
