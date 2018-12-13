package com.kuwai.ysy.module.circle.bean;

import java.util.List;

public class HoleMainListBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"banner":[{"b_id":1,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","link":"www.baidu.com","type":1},{"b_id":2,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","link":"www.baidu.com","type":1}],"TreeHoleList":[{"t_id":3,"title":"第三个树洞","text":"树洞树洞树洞","create_time":"2018-11-19 14:42:50","views":100,"hot":1},{"t_id":1,"title":"这是我的树洞标题","text":"树洞树洞树洞","create_time":"2018-11-19 14:42:50","views":10,"hot":1},{"t_id":2,"title":"第二个树洞","text":"树洞树洞树洞","create_time":"2018-11-19 14:42:50","views":1,"hot":0}]}
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
        private List<TreeHoleListBean> TreeHoleList;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<TreeHoleListBean> getTreeHoleList() {
            return TreeHoleList;
        }

        public void setTreeHoleList(List<TreeHoleListBean> TreeHoleList) {
            this.TreeHoleList = TreeHoleList;
        }

        public static class BannerBean {
            /**
             * b_id : 1
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             * link : www.baidu.com
             * type : 1
             */

            private int b_id;
            private String img;
            private String link;
            private int type;

            public int getB_id() {
                return b_id;
            }

            public void setB_id(int b_id) {
                this.b_id = b_id;
            }

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

        public static class TreeHoleListBean {
            /**
             * t_id : 3
             * title : 第三个树洞
             * text : 树洞树洞树洞
             * create_time : 2018-11-19 14:42:50
             * views : 100
             * hot : 1
             */

            private int t_id;
            private String title;
            private String text;
            private String create_time;
            private int views;
            private int hot;

            public int getT_id() {
                return t_id;
            }

            public void setT_id(int t_id) {
                this.t_id = t_id;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }
        }
    }
}
