package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class AllRewardBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":1,"create_time":1545810420,"girft_img_url":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","type":2,"t_id":10,"is_vip":1},{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":5,"create_time":1545184433,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":1},{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":5,"create_time":1545184300,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":1},{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":5,"create_time":1545184215,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":1},{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":3,"create_time":1543387823,"girft_img_url":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","type":2,"t_id":2,"is_vip":1},{"nickname":"无所谓","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","uid":1,"gender":1,"g_nums":10,"create_time":1543387781,"girft_img_url":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","type":1,"t_id":2,"is_vip":1},{"nickname":"222","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":2,"gender":2,"g_nums":5,"create_time":1542604694,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":0},{"nickname":"赵六","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":5,"gender":1,"g_nums":2,"create_time":1542604694,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":0},{"nickname":"王五","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":4,"gender":1,"g_nums":2,"create_time":1542604694,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":0},{"nickname":"李四","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","uid":3,"gender":1,"g_nums":2,"create_time":1542604694,"girft_img_url":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","type":1,"t_id":2,"is_vip":0}]
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
         * nickname : 无所谓
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * uid : 1
         * gender : 1
         * g_nums : 1
         * create_time : 1545810420
         * girft_img_url : http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png
         * type : 2
         * t_id : 10
         * is_vip : 1
         */

        private String nickname;
        private String avatar;
        private int uid;
        private int gender;
        private int g_nums;
        private int create_time;
        private String girft_img_url;
        private int type;
        private int t_id;
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

        public int getG_nums() {
            return g_nums;
        }

        public void setG_nums(int g_nums) {
            this.g_nums = g_nums;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getGirft_img_url() {
            return girft_img_url;
        }

        public void setGirft_img_url(String girft_img_url) {
            this.girft_img_url = girft_img_url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
