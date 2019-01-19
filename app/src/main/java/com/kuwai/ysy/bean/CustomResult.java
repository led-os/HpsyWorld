/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kuwai.ysy.bean;

import java.io.Serializable;

/**
 * 自定义版本检查的结果
 *
 * @author xuexiang
 * @since 2018/7/11 上午1:03
 */
public class CustomResult implements Serializable {
    /**
     * code : 200
     * data : {"app_version":"1.0","app_content":"全部内容","app_url":"www.baidu.com","app_size":"31000.00","is_force":true}
     * msg : success
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * app_version : 1.0
         * app_content : 全部内容
         * app_url : www.baidu.com
         * app_size : 31000.00
         * is_force : true
         */

        private String app_version;
        private String app_content;
        private String app_url;
        private Long app_size;
        private boolean is_force;

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getApp_content() {
            return app_content;
        }

        public void setApp_content(String app_content) {
            this.app_content = app_content;
        }

        public String getApp_url() {
            return app_url;
        }

        public void setApp_url(String app_url) {
            this.app_url = app_url;
        }

        public Long getApp_size() {
            return app_size;
        }

        public void setApp_size(Long app_size) {
            this.app_size = app_size;
        }

        public boolean isIs_force() {
            return is_force;
        }

        public void setIs_force(boolean is_force) {
            this.is_force = is_force;
        }
    }


//    public boolean hasUpdate;
//
//    public boolean isIgnorable;
//
//    public int versionCode;
//
//    public String versionName;
//
//    public String updateLog;
//
//    public String apkUrl;
//
//    public long apkSize;


}
