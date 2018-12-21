package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class PersonalDyBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":45,"text":"","attach":["http://192.168.1.88/public/static/img/image/201812/20/6ccf4f5ff75f9c61797c6864f4f96bd2.jpg","http://192.168.1.88/public/static/img/image/201812/20/89f0f288290ede2266920f71c2c33d6b.jpg","http://192.168.1.88/public/static/img/image/201812/20/7606db413684db615971244df3c62c07.jpg","http://192.168.1.88/public/static/img/image/201812/20/b7f702141b09482aa413a513508b509c.jpg","http://192.168.1.88/public/static/img/image/201812/20/168c92828f298ed21bd1b612c445871f.jpg","http://192.168.1.88/public/static/img/image/201812/20/6dea55437e6275c677a4da298d67af2d.jpg","http://192.168.1.88/public/static/img/image/201812/20/de8b817750736af3015237c068a87b0d.jpg","http://192.168.1.88/public/static/img/image/201812/20/5ba9cd5fafd1f1c5c244dc2863a60916.jpg"],"video_id":"","type":1,"good":0,"comment":0,"address":"-","update_time":1545308379,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":43,"text":"嘿嘿","attach":["http://192.168.1.88/public/static/img/image/201812/20/bbcd908b8c7ea532d2a2c0f022eb6a7f.JPEG","http://192.168.1.88/public/static/img/image/201812/20/a96d1f45b3c5449cfeccb3758c3bdb58.jpg","http://192.168.1.88/public/static/img/image/201812/20/0a3563c9c5c0692bf086653b3d3d437a.jpg","http://192.168.1.88/public/static/img/image/201812/20/e9879fb8cacc2c2950ba4757c76ea771.png","http://192.168.1.88/public/static/img/image/201812/20/be493f80f52a89047d424105d7a51469.JPEG","http://192.168.1.88/public/static/img/image/201812/20/28431bddbdc43a80e0535da7037c2b25.JPEG","http://192.168.1.88/public/static/img/image/201812/20/910a389d8b8d513fc368baae17ff0020.jpg","http://192.168.1.88/public/static/img/image/201812/20/1a4393f7f2231ebfad4d549f86e7245e.jpg","http://192.168.1.88/public/static/img/image/201812/20/1927e156494196fd3abd4e7edd61a9cf.jpg"],"video_id":"","type":1,"good":0,"comment":0,"address":"-","update_time":1545305597,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":35,"text":"动态测试","attach":[],"video_id":"","type":0,"good":0,"comment":0,"address":"-","update_time":1545204083,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":30,"text":"测试弹框","attach":["http://192.168.1.88/public/static/img/image/201812/17/da1ca91f4aa05d84c256227df908ac2a.jpg"],"video_id":"","type":1,"good":1,"comment":13,"address":"-","update_time":1545111554,"reward":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"}],"reward_sum":4,"whatgood":1,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":29,"text":"撒哥拍的","attach":["http://192.168.1.88/public/static/img/image/201812/17/c24a19e892315626df0b8332ddd8f694.JPEG","http://192.168.1.88/public/static/img/image/201812/17/2c117f50d0234db359cb0e46b69b5850.JPEG","http://192.168.1.88/public/static/img/image/201812/17/6ca7b95bcc9fe45b2d555bccb2de69e3.JPEG","http://192.168.1.88/public/static/img/image/201812/17/85f20dfadc5e9d7fbfad6dcad5d3e135.JPEG","http://192.168.1.88/public/static/img/image/201812/17/945a889c335eee70e63c191eefe9d23f.png","http://192.168.1.88/public/static/img/image/201812/17/eb14133a1886800074c2a96c891ed5ef.JPEG","http://192.168.1.88/public/static/img/image/201812/17/bc8c6daee34d17b7e00407167ca3efc0.png","http://192.168.1.88/public/static/img/image/201812/17/aa11eccafcadf55261ad6d90d08a2cb2.JPEG","http://192.168.1.88/public/static/img/image/201812/17/66e8120a94c2e848cea60b672f44fae0.JPEG"],"video_id":"","type":1,"good":0,"comment":2,"address":"-","update_time":1545043338,"reward":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"}],"reward_sum":3,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":28,"text":"咯弄","attach":["http://192.168.1.88/public/static/img/image/201812/17/417ef91a3071f56e86603c698c17a6e0.png"],"video_id":"","type":1,"good":1,"comment":1,"address":"-","update_time":1545043225,"reward":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg"}],"reward_sum":1,"whatgood":1,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":22,"text":"把","attach":["http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png"],"video_id":"","type":1,"good":0,"comment":10,"address":"-","update_time":1544766032,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":20,"text":"不在了","attach":[],"video_id":"","type":0,"good":0,"comment":0,"address":"-","update_time":1544765503,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":19,"text":"你在哪呢","attach":[],"video_id":"","type":0,"good":0,"comment":0,"address":"-","update_time":1544759478,"reward":[],"reward_sum":0,"whatgood":0,"is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg","nickname":"张三","uid":1,"age":"25","gender":1,"height":45,"education":"大专以下","annual_income":1,"d_id":10,"text":"zhoufei","attach":["http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg","http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg","http://192.168.1.88/public/static/img/image/201811/08/bad6bd7eebf8f9917bd367a32d4412b4.png","http://192.168.1.88/public/static/img/image/201812/11/638f1309d526a963785918bc405063a0.jpg"],"video_id":"","type":1,"good":1,"comment":2,"address":"-","update_time":1542955746,"reward":[],"reward_sum":0,"whatgood":1,"is_vip":1}]
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
         * avatar : http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg
         * nickname : 张三
         * uid : 1
         * age : 25
         * gender : 1
         * height : 45
         * education : 大专以下
         * annual_income : 1
         * d_id : 45
         * text :
         * attach : ["http://192.168.1.88/public/static/img/image/201812/20/6ccf4f5ff75f9c61797c6864f4f96bd2.jpg","http://192.168.1.88/public/static/img/image/201812/20/89f0f288290ede2266920f71c2c33d6b.jpg","http://192.168.1.88/public/static/img/image/201812/20/7606db413684db615971244df3c62c07.jpg","http://192.168.1.88/public/static/img/image/201812/20/b7f702141b09482aa413a513508b509c.jpg","http://192.168.1.88/public/static/img/image/201812/20/168c92828f298ed21bd1b612c445871f.jpg","http://192.168.1.88/public/static/img/image/201812/20/6dea55437e6275c677a4da298d67af2d.jpg","http://192.168.1.88/public/static/img/image/201812/20/de8b817750736af3015237c068a87b0d.jpg","http://192.168.1.88/public/static/img/image/201812/20/5ba9cd5fafd1f1c5c244dc2863a60916.jpg"]
         * video_id :
         * type : 1
         * good : 0
         * comment : 0
         * address : -
         * update_time : 1545308379
         * reward : []
         * reward_sum : 0
         * whatgood : 0
         * is_vip : 1
         */

        private String avatar;
        private String nickname;
        private int uid;
        private String age;
        private int gender;
        private int height;
        private String education;
        private int annual_income;
        private int d_id;
        private String text;
        private String video_id;
        private int type;
        private int good;
        private int comment;
        private String address;
        private int update_time;
        private int reward_sum;
        private int whatgood;
        private int is_vip;
        private List<String> attach;
        private List<?> reward;

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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getAnnual_income() {
            return annual_income;
        }

        public void setAnnual_income(int annual_income) {
            this.annual_income = annual_income;
        }

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

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getReward_sum() {
            return reward_sum;
        }

        public void setReward_sum(int reward_sum) {
            this.reward_sum = reward_sum;
        }

        public int getWhatgood() {
            return whatgood;
        }

        public void setWhatgood(int whatgood) {
            this.whatgood = whatgood;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public List<String> getAttach() {
            return attach;
        }

        public void setAttach(List<String> attach) {
            this.attach = attach;
        }

        public List<?> getReward() {
            return reward;
        }

        public void setReward(List<?> reward) {
            this.reward = reward;
        }
    }
}
