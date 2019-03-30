package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class MovieBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"film_name":"比悲伤更悲伤的故事","score":5,"cover":"http://localhost/public/static/img/image/201903/20/p2549523952.jpgagjtxyz028.jpg","year":"2018","genres":"爱情","director":"林孝谦","stardom":"张书豪/刘以豪/陈意涵"},{"film_name":"惊奇队长","score":7,"cover":"http://localhost/public/static/img/image/201903/20/p2548870813.jpgalmprtvw06.jpg","year":"2019","genres":"动作/科幻/冒险","director":"瑞安·弗雷克/安娜·波顿","stardom":"塞缪尔·杰克逊/裘德·洛/布丽·拉尔森"},{"film_name":"绿皮书","score":8.9,"cover":"http://localhost/public/static/img/image/201903/20/p2549177902.jpgdnqrsuxy35.jpg","year":"2018","genres":"剧情/喜剧/传记","director":"彼得·法雷里","stardom":"琳达·卡德里尼/马赫沙拉·阿里/维果·莫腾森"},{"film_name":"我的英雄学院：两位英雄","score":7.3,"cover":"http://localhost/public/static/img/image/201903/20/p2548783889.jpgacijkoyz16.jpg","year":"2018","genres":"动作/动画","director":"长崎健司","stardom":"志田未来/三宅健太/山下大辉"},{"film_name":"流浪地球","score":8,"cover":"http://localhost/public/static/img/image/201903/20/p2545472803.jpgadgmosvwx8.jpg","year":"2019","genres":"科幻/灾难","director":"郭帆","stardom":"李光洁/吴京/屈楚萧"},{"film_name":"夏目友人帐","score":8,"cover":"http://localhost/public/static/img/image/201903/20/p2546745948.jpgacekmoq012.jpg","year":"2018","genres":"剧情/动画/奇幻","director":"伊藤秀树/大森贵弘","stardom":"高良健吾/井上和彦/神谷浩史"},{"film_name":"阿丽塔：战斗天使","score":7.6,"cover":"http://localhost/public/static/img/image/201903/20/p2544987866.jpgdkpuxy1578.jpg","year":"2019","genres":"动作/科幻/冒险","director":"罗伯特·罗德里格兹","stardom":"基恩·约翰逊/克里斯托弗·沃尔兹/罗莎·萨拉查"},{"film_name":"驯龙高手3","score":7.5,"cover":"http://localhost/public/static/img/image/201903/20/p2546335362.jpgdiklnouv79.jpg","year":"2019","genres":"动画/奇幻/冒险","director":"迪恩·德布洛斯","stardom":"F·默里·亚伯拉罕/亚美莉卡·费雷拉/杰伊·巴鲁切尔"},{"film_name":"老师·好","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2548766232.jpgcghmoz1249.jpg","year":"2019","genres":"剧情","director":"张栾","stardom":"王广源/汤梦佳/于谦"},{"film_name":"地久天长","score":7.8,"cover":"http://localhost/public/static/img/image/201903/20/p2550208359.jpgcdmnpryz18.jpg","year":"2019","genres":"剧情/家庭","director":"王小帅","stardom":"齐溪/咏梅/王景春"},{"film_name":"过春天","score":8,"cover":"http://localhost/public/static/img/image/201903/20/p2549537782.jpgehksuxz459.jpg","year":"2018","genres":"剧情","director":"白雪","stardom":"汤加文/孙阳/黄尧"},{"film_name":"阳台上","score":6.1,"cover":"http://localhost/public/static/img/image/201903/20/p2546828235.jpgdehqvyz128.jpg","year":"2019","genres":"剧情","director":"张猛","stardom":"曹瑞/王锵/周冬雨"},{"film_name":"少女宿舍","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2550454042.jpgefhinpqyz3.jpg","year":"2019","genres":"悬疑/惊悚","director":"曹尚水/杨盼盼","stardom":"田启文/曹越/陈淋"},{"film_name":"把哥哥退货可以吗？","score":5.4,"cover":"http://localhost/public/static/img/image/201903/20/p2550530988.jpgbcdhlpqx17.jpg","year":"2018","genres":"喜剧/爱情","director":"维塔亚·东俞扬","stardom":"尼查坤·抔勒威查固/尤拉萨雅·斯帕邦德/桑尼·苏瓦美塔农"},{"film_name":"飞驰人生","score":7,"cover":"http://localhost/public/static/img/image/201903/20/p2542973862.jpgabhlprsuy7.jpg","year":"2019","genres":"喜剧","director":"韩寒","stardom":"尹正/黄景瑜/沈腾"},{"film_name":"江海渔童之巨龟奇缘","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2549234757.jpgajnz134569.jpg","year":"2019","genres":"动画","director":"张立衍","stardom":"郭政建/张磊/杨光普照"},{"film_name":"古井凶灵","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2547985203.jpgbefnuwxz15.jpg","year":"2019","genres":"悬疑/惊悚","director":"王辰六","stardom":"李易芸/陈美林/程韦然"},{"film_name":"爱无痕","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2550632216.jpgacijnpqvw6.jpg","year":"2019","genres":"剧情","director":"张大圣/康亮","stardom":"何云龙"},{"film_name":"老公去哪了","score":0,"cover":"http://localhost/public/static/img/image/201903/20/p2548167457.jpgnvwxy45678.jpg","year":"2019","genres":"喜剧/爱情/家庭","director":"李思易","stardom":"沈梦辰/黄小蕾/黄俊鹏"}]
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
         * film_name : 比悲伤更悲伤的故事
         * score : 5
         * cover : http://localhost/public/static/img/image/201903/20/p2549523952.jpgagjtxyz028.jpg
         * year : 2018
         * genres : 爱情
         * director : 林孝谦
         * stardom : 张书豪/刘以豪/陈意涵
         */

        private String film_name;
        private float score;
        private String cover;
        private String year;
        private String f_id;
        private String genres;
        private String director;
        private String stardom;

        public String getF_id() {
            return f_id == null ? "" : f_id;
        }

        public void setF_id(String f_id) {
            this.f_id = f_id == null ? "" : f_id;
        }

        public String getFilm_name() {
            return film_name;
        }

        public void setFilm_name(String film_name) {
            this.film_name = film_name;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getGenres() {
            return genres;
        }

        public void setGenres(String genres) {
            this.genres = genres;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getStardom() {
            return stardom;
        }

        public void setStardom(String stardom) {
            this.stardom = stardom;
        }
    }
}
