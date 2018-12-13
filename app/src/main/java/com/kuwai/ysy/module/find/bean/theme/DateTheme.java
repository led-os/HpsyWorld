package com.kuwai.ysy.module.find.bean.theme;

import java.util.List;

public class DateTheme {


    /**
     * code : 200
     * msg : 查询成功
     * data : {"sincerity":[{"s_id":1,"name":"美食","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":2,"name":"电影","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":3,"name":"郊游","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":4,"name":"旅行","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":5,"name":"唱歌","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":6,"name":"陶艺","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":7,"name":"密室逃脱","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":8,"name":"游乐场","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"s_id":100,"name":"其它","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}],"custom":[{"s_id":103,"name":"喝酒","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","count":2}],"custom_hot":[{"s_id":103,"name":"喝酒","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","count":2},{"s_id":104,"name":"吃饭","img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","count":1}]}
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
        private List<DataBean.SincerityBean> sincerity;
        private List<DataBean.CustomBean> custom;
        private List<DataBean.CustomHotBean> custom_hot;

        public List<DataBean.SincerityBean> getSincerity() {
            return sincerity;
        }

        public void setSincerity(List<DataBean.SincerityBean> sincerity) {
            this.sincerity = sincerity;
        }

        public List<DataBean.CustomBean> getCustom() {
            return custom;
        }

        public void setCustom(List<DataBean.CustomBean> custom) {
            this.custom = custom;
        }

        public List<DataBean.CustomHotBean> getCustom_hot() {
            return custom_hot;
        }

        public void setCustom_hot(List<DataBean.CustomHotBean> custom_hot) {
            this.custom_hot = custom_hot;
        }

        public static class SincerityBean {
            /**
             * s_id : 1
             * name : 美食
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private int s_id;
            private String name;
            private String img;
            public boolean isCheck;

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class CustomBean {
            /**
             * s_id : 103
             * name : 喝酒
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private int s_id;
            private String name;
            private String img;
            public boolean isCheck;

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class CustomHotBean {
            /**
             * s_id : 103
             * name : 喝酒
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * count : 2
             */

            private int s_id;
            private String name;
            private String img;
            private int count;
            public boolean isCheck;

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
