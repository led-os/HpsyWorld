package com.kuwai.ysy.module.find.bean;

import java.util.List;

public class SearchCityBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : [[{"region_id":114,"region_name":"苏州市","parent_id":11,"region_type":2,"area":[{"region_id":1280,"region_name":"沧浪区","parent_id":114},{"region_id":1281,"region_name":"平江区","parent_id":114},{"region_id":1282,"region_name":"金阊区","parent_id":114},{"region_id":1283,"region_name":"虎丘区","parent_id":114},{"region_id":1284,"region_name":"吴中区","parent_id":114},{"region_id":1285,"region_name":"相城区","parent_id":114},{"region_id":1286,"region_name":"姑苏区","parent_id":114},{"region_id":1287,"region_name":"常熟市","parent_id":114},{"region_id":1288,"region_name":"张家港市","parent_id":114},{"region_id":1289,"region_name":"昆山市","parent_id":114},{"region_id":1290,"region_name":"吴江区","parent_id":114},{"region_id":1291,"region_name":"太仓市","parent_id":114},{"region_id":1292,"region_name":"新区","parent_id":114},{"region_id":1293,"region_name":"园区","parent_id":114},{"region_id":1294,"region_name":"其它区","parent_id":114}]}]]
     */

    private int code;
    private String msg;
    private List<List<DataBean>> data;

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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * region_id : 114
         * region_name : 苏州市
         * parent_id : 11
         * region_type : 2
         * area : [{"region_id":1280,"region_name":"沧浪区","parent_id":114},{"region_id":1281,"region_name":"平江区","parent_id":114},{"region_id":1282,"region_name":"金阊区","parent_id":114},{"region_id":1283,"region_name":"虎丘区","parent_id":114},{"region_id":1284,"region_name":"吴中区","parent_id":114},{"region_id":1285,"region_name":"相城区","parent_id":114},{"region_id":1286,"region_name":"姑苏区","parent_id":114},{"region_id":1287,"region_name":"常熟市","parent_id":114},{"region_id":1288,"region_name":"张家港市","parent_id":114},{"region_id":1289,"region_name":"昆山市","parent_id":114},{"region_id":1290,"region_name":"吴江区","parent_id":114},{"region_id":1291,"region_name":"太仓市","parent_id":114},{"region_id":1292,"region_name":"新区","parent_id":114},{"region_id":1293,"region_name":"园区","parent_id":114},{"region_id":1294,"region_name":"其它区","parent_id":114}]
         */

        private int region_id;
        private String region_name;
        private int parent_id;
        private int region_type;
        private List<AreaBean> area;

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getRegion_type() {
            return region_type;
        }

        public void setRegion_type(int region_type) {
            this.region_type = region_type;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            /**
             * region_id : 1280
             * region_name : 沧浪区
             * parent_id : 114
             */

            private int region_id;
            private String region_name;
            private int parent_id;

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }
        }
    }
}
