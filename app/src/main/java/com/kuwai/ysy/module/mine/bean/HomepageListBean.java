package com.kuwai.ysy.module.mine.bean;

import java.io.Serializable;
import java.util.List;

public class HomepageListBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"plain":[{"field":"nickname","name":"昵称","is_edit":1,"inputtext":0,"dtype":0,"data":"吵架"},{"field":"uid","name":"ID","is_edit":0,"inputtext":0,"dtype":0,"data":10047},{"field":"gender","name":"性别","is_edit":0,"inputtext":0,"dtype":0,"data":"男"},{"field":"age","name":"年龄","is_edit":0,"inputtext":0,"dtype":0,"data":"28"},{"field":"height","name":"身高","is_edit":1,"inputtext":0,"dtype":0,"data":165},{"field":"education","name":"学历","is_edit":1,"inputtext":0,"dtype":0,"data":"大专"},{"field":"birthday","name":"生日","is_edit":1,"inputtext":0,"dtype":0,"data":"1991-01-01"},{"field":"weight","name":"体重","is_edit":1,"inputtext":0,"dtype":0,"data":0}],"senior":[{"field":"marriage","name":"感情状况","is_edit":1,"inputtext":0,"dtype":0,"data":"未婚"},{"field":"children","name":"有无子女","is_edit":1,"inputtext":0,"dtype":0,"data":"否"},{"field":"car_buying","name":"购车情况","is_edit":1,"inputtext":0,"dtype":0,"data":"未购车"},{"field":"buy_house","name":"购房情况","is_edit":1,"inputtext":0,"dtype":0,"data":"未购房"},{"field":"annual_income","name":"年收入","is_edit":0,"inputtext":0,"dtype":0,"data":"8W-10W"},{"field":"occupation","name":"职业","is_edit":1,"inputtext":0,"dtype":0,"data":"其它"},{"field":"religion","name":"宗教信仰","is_edit":1,"inputtext":0,"dtype":0,"data":"无"},{"field":"advantages","name":"擅长","is_edit":1,"inputtext":3,"dtype":3,"data":""},{"field":"love_view","name":"对爱情的看法","is_edit":1,"inputtext":1,"dtype":1,"data":""},{"field":"nature_view","name":"对性的看法","is_edit":1,"inputtext":0,"dtype":0,"data":""},{"field":"round","name":"最满意的地方","is_edit":1,"inputtext":0,"dtype":0,"data":""},{"field":"is_children","name":"是否想要小孩","is_edit":1,"inputtext":0,"dtype":0,"data":"是"},{"field":"appointment","name":"偏爱的约会方式","is_edit":1,"inputtext":0,"dtype":0,"data":""},{"field":"wedding","name":"期待的婚礼形式","is_edit":1,"inputtext":3,"dtype":3,"data":""}],"selection":[{"s_id":29,"text":"无所谓"},{"s_id":30,"text":"7"}]}
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
        private List<PlainBean> plain;
        private List<PlainBean> senior;
        private List<SelectionBean> selection;

        public List<PlainBean> getPlain() {
            return plain;
        }

        public void setPlain(List<PlainBean> plain) {
            this.plain = plain;
        }

        public List<PlainBean> getSenior() {
            return senior;
        }

        public void setSenior(List<PlainBean> senior) {
            this.senior = senior;
        }

        public List<SelectionBean> getSelection() {
            return selection;
        }

        public void setSelection(List<SelectionBean> selection) {
            this.selection = selection;
        }

        public static class PlainBean implements Serializable{
            /**
             * field : nickname
             * name : 昵称
             * is_edit : 1
             * inputtext : 0
             * dtype : 0
             * data : 吵架
             */

            private String field;
            private String name;
            private int is_edit;
            private int inputtext;
            private int dtype;
            private String data;

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIs_edit() {
                return is_edit;
            }

            public void setIs_edit(int is_edit) {
                this.is_edit = is_edit;
            }

            public int getInputtext() {
                return inputtext;
            }

            public void setInputtext(int inputtext) {
                this.inputtext = inputtext;
            }

            public int getDtype() {
                return dtype;
            }

            public void setDtype(int dtype) {
                this.dtype = dtype;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }

        public static class SelectionBean {
            /**
             * s_id : 29
             * text : 无所谓
             */

            private int s_id;
            private String text;

            public int getS_id() {
                return s_id;
            }

            public void setS_id(int s_id) {
                this.s_id = s_id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
