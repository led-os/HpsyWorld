package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class PersolHomePageBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"info":{"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","attach":["http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png"],"uid":1,"gender":1,"city":"江苏-苏州市","age":"25","birthday":"1993-11-11","sig":"自由自在","marriage":"未婚","children":"无","car_buying":"未购车","buy_house":"已购房","height":45,"annual_income":"3W以下","occupation":"IT/计算机","education":"大专以下","religion":"无","is_real":0,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":0,"video_id":123456,"video_attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","grade":1,"is_vip":1},"selection":{"love_address":"江苏-苏州市"},"footprints":[{"f_id":1,"region_name":"苏州市"},{"f_id":2,"region_name":"南通市"}],"gift":[{"g_id":5,"girft_name":"锁定你","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"56402","nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"is_vip":1},{"g_id":3,"girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"52002","nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":1},{"g_id":4,"girft_name":"烈焰红唇","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39802","nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"is_vip":1},{"g_id":6,"girft_name":"我们约会吧","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39600","nickname":"aa","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":6,"gender":1,"is_vip":1},{"g_id":26,"girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"26","nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"gender":1,"is_vip":1},{"g_id":1,"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"17","nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":1}],"view_face":1}
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
         * info : {"nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","attach":["http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png"],"uid":1,"gender":1,"city":"江苏-苏州市","age":"25","birthday":"1993-11-11","sig":"自由自在","marriage":"未婚","children":"无","car_buying":"未购车","buy_house":"已购房","height":45,"annual_income":"3W以下","occupation":"IT/计算机","education":"大专以下","religion":"无","is_real":0,"is_phone":1,"is_education":0,"is_house":0,"is_vehicle":0,"is_avatar":0,"video_id":123456,"video_attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","grade":1,"is_vip":1}
         * selection : {"love_address":"江苏-苏州市"}
         * footprints : [{"f_id":1,"region_name":"苏州市"},{"f_id":2,"region_name":"南通市"}]
         * gift : [{"g_id":5,"girft_name":"锁定你","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"56402","nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"is_vip":1},{"g_id":3,"girft_name":"求交往","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"52002","nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"is_vip":1},{"g_id":4,"girft_name":"烈焰红唇","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39802","nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"is_vip":1},{"g_id":6,"girft_name":"我们约会吧","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"39600","nickname":"aa","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":6,"gender":1,"is_vip":1},{"g_id":26,"girft_name":"热辣似火","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"26","nickname":"张三","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":1,"gender":1,"is_vip":1},{"g_id":1,"girft_name":"甜蜜暗恋","girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","price":"17","nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"is_vip":1}]
         * view_face : 1
         */

        private InfoBean info;
        private SelectionBean selection;
        private int view_face;
        private List<FootprintsBean> footprints;
        private List<GiftBean> gift;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public SelectionBean getSelection() {
            return selection;
        }

        public void setSelection(SelectionBean selection) {
            this.selection = selection;
        }

        public int getView_face() {
            return view_face;
        }

        public void setView_face(int view_face) {
            this.view_face = view_face;
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
             * nickname : 张三
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * attach : ["http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png"]
             * uid : 1
             * gender : 1
             * city : 江苏-苏州市
             * age : 25
             * birthday : 1993-11-11
             * sig : 自由自在
             * marriage : 未婚
             * children : 无
             * car_buying : 未购车
             * buy_house : 已购房
             * height : 45
             * annual_income : 3W以下
             * occupation : IT/计算机
             * education : 大专以下
             * religion : 无
             * is_real : 0
             * is_phone : 1
             * is_education : 0
             * is_house : 0
             * is_vehicle : 0
             * is_avatar : 0
             * video_id : 123456
             * video_attach : http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png
             * grade : 1
             * is_vip : 1
             */

            private String nickname;
            private String avatar;
            private int uid;
            private int gender;
            private String city;
            private String age;
            private String birthday;
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
            private int video_id;
            private String video_attach;
            private int grade;
            private int is_vip;
            private List<String> attach;

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

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
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

            public int getVideo_id() {
                return video_id;
            }

            public void setVideo_id(int video_id) {
                this.video_id = video_id;
            }

            public String getVideo_attach() {
                return video_attach;
            }

            public void setVideo_attach(String video_attach) {
                this.video_attach = video_attach;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public List<String> getAttach() {
                return attach;
            }

            public void setAttach(List<String> attach) {
                this.attach = attach;
            }
        }

        public static class SelectionBean {
            /**
             * love_address : 江苏-苏州市
             */

            private String love_address;

            public String getLove_address() {
                return love_address;
            }

            public void setLove_address(String love_address) {
                this.love_address = love_address;
            }
        }

        public static class FootprintsBean {
            /**
             * f_id : 1
             * region_name : 苏州市
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
             * g_id : 5
             * girft_name : 锁定你
             * girft_img_url : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * price : 56402
             * nickname : 赵六
             * avatar : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * uid : 5
             * gender : 1
             * is_vip : 1
             */

            private int g_id;
            private String girft_name;
            private String girft_img_url;
            private String price;
            private String nickname;
            private String avatar;
            private int uid;
            private int gender;
            private int is_vip;

            public int getG_id() {
                return g_id;
            }

            public void setG_id(int g_id) {
                this.g_id = g_id;
            }

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }
        }
    }
}
