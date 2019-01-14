package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class EarlierBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"l_ld":1,"uid":2,"nickname":"3","avatar":"http://localhost/public/static/img/avatar/201811/01/58d5a5f64f7abd42248da91be37228aa.jpg","age":18,"gender":1,"is_vip":1}]
     */

    private int code;
    private String msg;
    private List<LikeBean> data;

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

    public List<LikeBean> getData() {
        return data;
    }

    public void setData(List<LikeBean> data) {
        this.data = data;
    }
}
