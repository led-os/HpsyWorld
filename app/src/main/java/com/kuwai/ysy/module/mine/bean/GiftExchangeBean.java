package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class GiftExchangeBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"g_g_id":"1","type":"1","g_nums":"1","address":{"a_id":6,"name":"张三","tel":"110","area":["江苏","苏州市","平江区"],"detailed_address":"平江路"}}
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
         * g_g_id : 1
         * type : 1
         * g_nums : 1
         * address : {"a_id":6,"name":"张三","tel":"110","area":["江苏","苏州市","平江区"],"detailed_address":"平江路"}
         */

        private String g_g_id;
        private String type;
        private String g_nums;
        private AddressBean address;

        public String getG_g_id() {
            return g_g_id;
        }

        public void setG_g_id(String g_g_id) {
            this.g_g_id = g_g_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getG_nums() {
            return g_nums;
        }

        public void setG_nums(String g_nums) {
            this.g_nums = g_nums;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * a_id : 6
             * name : 张三
             * tel : 110
             * area : ["江苏","苏州市","平江区"]
             * detailed_address : 平江路
             */

            private int a_id;
            private String name;
            private String tel;
            private String detailed_address;
            private List<String> area;

            public int getA_id() {
                return a_id;
            }

            public void setA_id(int a_id) {
                this.a_id = a_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getDetailed_address() {
                return detailed_address;
            }

            public void setDetailed_address(String detailed_address) {
                this.detailed_address = detailed_address;
            }

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }
        }
    }
}
