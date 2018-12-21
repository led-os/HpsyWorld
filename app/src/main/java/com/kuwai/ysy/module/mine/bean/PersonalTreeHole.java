package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class PersonalTreeHole {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"t_id":6,"title":"测试","text":"哈哈哈哈啊哈哈哈哈哈","views":7,"create_time":1544608872},{"t_id":5,"title":"发一个","text":"好的","views":8,"create_time":1543482106},{"t_id":4,"title":"我发布了一个树洞","text":"猜猜我是谁","views":6,"create_time":1543481754},{"t_id":2,"title":"第二个树洞","text":"树洞树洞树洞","views":34,"create_time":1542609770},{"t_id":1,"title":"这是我的树洞标题","text":"树洞树洞树洞","views":71,"create_time":1542609770}]
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
         * t_id : 6
         * title : 测试
         * text : 哈哈哈哈啊哈哈哈哈哈
         * views : 7
         * create_time : 1544608872
         */

        private int t_id;
        private String title;
        private String text;
        private int views;
        private int create_time;

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

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
