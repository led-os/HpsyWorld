package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class TodayIntegral {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"integral":{"integral_sum":52,"grade":1,"today_integral":"10"},"today":[{"name":"登录","create_time":1543542722,"type":"-","integral":10}]}
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
         * integral : {"integral_sum":52,"grade":1,"today_integral":"10"}
         * today : [{"name":"登录","create_time":1543542722,"type":"-","integral":10}]
         */

        private IntegralBean integral;
        private List<TodayBean> today;

        public IntegralBean getIntegral() {
            return integral;
        }

        public void setIntegral(IntegralBean integral) {
            this.integral = integral;
        }

        public List<TodayBean> getToday() {
            return today;
        }

        public void setToday(List<TodayBean> today) {
            this.today = today;
        }

        public static class IntegralBean {
            /**
             * integral_sum : 52
             * grade : 1
             * today_integral : 10
             */

            private int integral_sum;
            private int grade;
            private String today_integral;

            public int getIntegral_sum() {
                return integral_sum;
            }

            public void setIntegral_sum(int integral_sum) {
                this.integral_sum = integral_sum;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getToday_integral() {
                return today_integral;
            }

            public void setToday_integral(String today_integral) {
                this.today_integral = today_integral;
            }
        }

        public static class TodayBean {
            /**
             * name : 登录
             * create_time : 1543542722
             * type : -
             * integral : 10
             */

            private String name;
            private int create_time;
            private String type;
            private int integral;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }
        }
    }
}
