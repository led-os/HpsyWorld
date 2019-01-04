package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class WallBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"video":[{"v_id":4,"video_id":"383183e5ba874f7088d2074881c1869b","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"v_id":511,"video_id":"383183e5ba874f7088d2074881c1869b","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"v_id":1019,"video_id":"383183e5ba874f7088d2074881c1869b","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"v_id":1526,"video_id":"383183e5ba874f7088d2074881c1869b","attach":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}],"image":[{"i_id":5,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"i_id":6,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"i_id":7,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"},{"i_id":8,"img":"http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg"}]}
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
        private List<VideoBean> video;
        private List<ImageBean> image;

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public List<ImageBean> getImage() {
            return image;
        }

        public void setImage(List<ImageBean> image) {
            this.image = image;
        }

        public static class VideoBean {
            /**
             * v_id : 4
             * video_id : 383183e5ba874f7088d2074881c1869b
             * attach : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private int v_id;
            private String video_id;
            private String attach;

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

            public String getAttach() {
                return attach;
            }

            public void setAttach(String attach) {
                this.attach = attach;
            }
        }

        public static class ImageBean {
            /**
             * i_id : 5
             * img : http://192.168.1.88/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg
             */

            private int i_id;
            private String img;

            public int getI_id() {
                return i_id;
            }

            public void setI_id(int i_id) {
                this.i_id = i_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
