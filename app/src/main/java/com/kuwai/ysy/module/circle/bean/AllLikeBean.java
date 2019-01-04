package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class AllLikeBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"d_id":30,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201812/17/da1ca91f4aa05d84c256227df908ac2a.jpg","text":"测试弹框","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1545127436,"is_vip":1},{"d_id":29,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201812/17/c24a19e892315626df0b8332ddd8f694.JPEG","text":"撒哥拍的","avatar":"http://192.168.1.88/public/static/img/avatar/201901/02/534b1d9e110ac69d5eb6dad71ffce689.jpg","nickname":"qq888","uid":105,"gender":1,"create_time":1545976257,"is_vip":1},{"d_id":28,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201812/17/417ef91a3071f56e86603c698c17a6e0.png","text":"咯弄","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1545112804,"is_vip":1},{"d_id":22,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","text":"把","avatar":"http://192.168.1.88/public/static/img/avatar/201901/02/534b1d9e110ac69d5eb6dad71ffce689.jpg","nickname":"qq888","uid":105,"gender":1,"create_time":1545976385,"is_vip":1},{"d_id":13,"type":0,"attach":[],"text":"100000000000000009877","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1543474042,"is_vip":1},{"d_id":10,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","text":"zhoufei","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1544173283,"is_vip":1},{"d_id":9,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","text":"zhoufei","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1543822262,"is_vip":1},{"d_id":5,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","text":"试试","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"create_time":1543566253,"is_vip":1},{"d_id":2,"type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","text":"动态2","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"222","uid":2,"gender":2,"create_time":1542334782,"is_vip":0}]
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
         * d_id : 30
         * type : 1
         * attach : http://192.168.1.88/public/static/img/image/201812/17/da1ca91f4aa05d84c256227df908ac2a.jpg
         * text : 测试弹框
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * nickname : 无所谓
         * uid : 1
         * gender : 1
         * create_time : 1545127436
         * is_vip : 1
         */

        private int d_id;
        private int type;
        private String attach;
        private String text;
        private String avatar;
        private String nickname;
        private int uid;
        private int gender;
        private int create_time;
        private int is_vip;

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }
}
