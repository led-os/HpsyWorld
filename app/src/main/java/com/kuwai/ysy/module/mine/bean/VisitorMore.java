package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class VisitorMore {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":34,"create_time":1544783728,"type":"1","d_id":22,"text":"把","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":40,"create_time":1545043019,"type":"1","d_id":22,"text":"把","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":42,"create_time":1545098467,"type":"1","d_id":19,"text":"你在哪呢","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":43,"create_time":1545098531,"type":"1","d_id":10,"text":"zhoufei","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":44,"create_time":1545115520,"type":"1","d_id":30,"text":"测试弹框","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":45,"create_time":1545115945,"type":"1","d_id":29,"text":"撒哥拍的","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":48,"create_time":1545127900,"type":"1","d_id":22,"text":"把","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":49,"create_time":1545135054,"type":"1","d_id":28,"text":"咯弄","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":57,"create_time":1545218478,"type":"1","d_id":35,"text":"动态测试","is_vip":1},{"avatar":"http://192.168.1.88/public/static/img/avatar/201901/06/7bff109982517cb642124318bf8bf1b2.jpg","nickname":"峰","uid":104,"gender":1,"v_id":67,"create_time":1545361640,"type":"1","d_id":45,"text":"","is_vip":1}]
     */

    private int code;
    private String msg;
    private List<TodayBean> data;

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

    public List<TodayBean> getData() {
        return data;
    }

    public void setData(List<TodayBean> data) {
        this.data = data;
    }
}
