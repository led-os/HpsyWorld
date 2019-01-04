package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class AllCommentBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"d_id":2,"text":"动态2","type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"无所谓","uid":1,"gender":1,"c_text":"一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十","create_time":1542003380,"is_vip":1},{"d_id":2,"text":"动态2","type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"李四","uid":3,"gender":1,"c_text":"一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十","create_time":1542003380,"is_vip":1},{"d_id":2,"text":"动态2","type":1,"attach":"http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"222","uid":2,"gender":2,"c_text":"一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十","create_time":1542596858,"is_vip":1},{"d_id":2,"text":"树洞树洞树洞","type":-1,"attach":"","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"222","uid":2,"gender":2,"c_text":"xxx","create_time":1542609770,"is_vip":1},{"d_id":2,"text":"树洞树洞树洞","type":-1,"attach":"","avatar":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","nickname":"赵六","uid":5,"gender":1,"c_text":"xxx","create_time":1542609770,"is_vip":1}]
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
         * d_id : 2
         * text : 动态2
         * type : 1
         * attach : http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * nickname : 无所谓
         * uid : 1
         * gender : 1
         * c_text : 一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十
         * create_time : 1542003380
         * is_vip : 1
         */

        private int d_id;
        private String text;
        private int type;
        private String attach;
        private String avatar;
        private String nickname;
        private int uid;
        private int gender;
        private String c_text;
        private int create_time;
        private int is_vip;

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
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

        public String getC_text() {
            return c_text;
        }

        public void setC_text(String c_text) {
            this.c_text = c_text;
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
