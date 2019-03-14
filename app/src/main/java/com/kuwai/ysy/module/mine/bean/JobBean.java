package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class JobBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [{"name":"IT/计算机"},{"name":"律师/法务"},{"name":"在校学生"},{"name":"外企企业"},{"name":"医疗/护理"},{"name":"航天服务业"},{"name":"互联网/通信"},{"name":"文化/广告/传媒"},{"name":"营销/市场/策划"},{"name":"生产/工艺/制造"},{"name":"财会/审计/统计"},{"name":"公务员/国家干部"},{"name":"金融/银行/投资/保险"},{"name":"人力资源/行政/后勤"},{"name":"公关/商务/贸易/采购"},{"name":"商业/服装业/个体经营"},{"name":"教育/培训/科研/管理咨询"},{"name":"政府机关/党群组织/事业单位企业/军人"},{"name":"自由职业"},{"name":"IT/计算机"},{"name":"其它"}]
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
         * name : IT/计算机
         */

        private String name;
        private String o_id;

        public String getO_id() {
            return o_id == null ? "" : o_id;
        }

        public void setO_id(String o_id) {
            this.o_id = o_id == null ? "" : o_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
