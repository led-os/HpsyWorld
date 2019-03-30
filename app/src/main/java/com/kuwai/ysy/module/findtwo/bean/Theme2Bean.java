package com.kuwai.ysy.module.findtwo.bean;

import java.util.List;

public class Theme2Bean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"other":"逛街购物"},{"other":"摄影"},{"other":"看海"},{"other":"钓鱼"},{"other":"开车兜风"},{"other":"看车展"},{"other":"聊人生理想"},{"other":"看日出日落"},{"other":"拍创意照片"},{"other":"互诉秘密"},{"other":"讨论某一话题"},{"other":"草地上看书"}]
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
         * other : 逛街购物
         */

        private String other;

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}
