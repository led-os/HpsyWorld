package com.kuwai.ysy.module.home.bean.main;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPicBean {


    /**
     * code : 200
     * ,msg : 获取成功
     * data : [{"v_id":1396,"video_id":"0","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/cdd85ee3fc77077eda3511b4d84151c3.jpg","img_width":"544.00","img_height":"960.00","create_time":1547521123},{"v_id":1395,"video_id":"0","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/5fedc21cd931bbb409ad5be363f5f65b.jpg","img_width":"544.00","img_height":"960.00","create_time":1547521123},{"v_id":1393,"video_id":"0","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/593eadfe1307b86b35709e2eafc86f8a.jpg","img_width":"540.00","img_height":"960.00","create_time":1547521122},{"v_id":1394,"video_id":"0","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/b8253eaf4b5e9900253b44c23c6fc2dd.jpg","img_width":"1080.00","img_height":"1920.00","create_time":1547521122},{"v_id":2067,"video_id":"dddc0f54e9ab4cceaeb6bff3f06dc219","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/85c42e306c4c78b147db81ad230879a5.jpg","img_width":"540.00","img_height":"960.00","create_time":1547521032},{"v_id":2066,"video_id":"3418d7ef8e72412faa37833174c9bde6","img":"http://test.yushuiyuan.cn/public/static/img/image/201901/15/c4e11160ea05d0c6ac4284be7fbcb00a.jpg","img_width":"544.00","img_height":"960.00","create_time":1547521010}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * v_id : 1396
         * video_id : 0
         * img : http://test.yushuiyuan.cn/public/static/img/image/201901/15/cdd85ee3fc77077eda3511b4d84151c3.jpg
         * img_width : 544.00
         * img_height : 960.00
         * create_time : 1547521123
         */

        private int v_id;
        private String video_id;
        private String img;
        private String img_width;
        private String img_height;
        private int create_time;

        public int getV_id() {
            return v_id;
        }

        public void setV_id(int v_id) {
            this.v_id = v_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg_width() {
            return img_width;
        }

        public void setImg_width(String img_width) {
            this.img_width = img_width;
        }

        public String getImg_height() {
            return img_height;
        }

        public void setImg_height(String img_height) {
            this.img_height = img_height;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
