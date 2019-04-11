package com.kuwai.ysy.module.home.bean;

public class AuthVideoBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"img":"http://localhost/public/static/img/image/201904/02/2a2912bdfdbad7eabbbaba9a4fc3b997.jpg","video_id":"52529848d13c4e31a73b4b2dbbac9384"}
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
         * img : http://localhost/public/static/img/image/201904/02/2a2912bdfdbad7eabbbaba9a4fc3b997.jpg
         * video_id : 52529848d13c4e31a73b4b2dbbac9384
         */

        private String img;
        private String video_id;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }
}
