package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class VideoRecordBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"vl_id":10047,"avatar":"http://test.yushuiyuan.cn//public/static/img/avatar/201902/25/9af3d133aa8b8725bf7825eff4fa9734.png","nickname":"吵架","end_time":0,"create_time":1554269502,"intimate":0}]
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
         * vl_id : 10047
         * avatar : http://test.yushuiyuan.cn//public/static/img/avatar/201902/25/9af3d133aa8b8725bf7825eff4fa9734.png
         * nickname : 吵架
         * end_time : 0
         * create_time : 1554269502
         * intimate : 0
         */

        private int vl_id;
        private String avatar;
        private String nickname;
        private int end_time;
        private int create_time;
        private int intimate;

        public int getVl_id() {
            return vl_id;
        }

        public void setVl_id(int vl_id) {
            this.vl_id = vl_id;
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

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIntimate() {
            return intimate;
        }

        public void setIntimate(int intimate) {
            this.intimate = intimate;
        }
    }
}
