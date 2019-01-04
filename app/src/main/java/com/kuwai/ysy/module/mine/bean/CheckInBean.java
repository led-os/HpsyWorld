package com.kuwai.ysy.module.mine.bean;

import java.util.List;

public class CheckInBean {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"integral":{"integral_sum":2787,"integral_exchange":1887,"sign_times":1},"sign_in":[{"create_time":1545635757}],"daily_tasks":[{"sum":1,"name":"登录","integral":"10","Total":1},{"sum":1,"name":"签到","integral":"10","Total":1},{"integral":"10","sum":0,"name":"邀请ta传图、视频","Total":5},{"sum":0,"name":"停留时长大于30分钟","integral":"20","Total":1},{"sum":0,"name":"喜欢一个用户","integral":"5","Total":10},{"sum":0,"name":"动态、树洞发布","integral":"10","Total":5},{"sum":0,"name":"动态树洞内容点赞","integral":"5","Total":5},{"sum":0,"name":"动态树洞内容评论","integral":"5","Total":5},{"sum":0,"name":"分享","integral":"20","Total":10},{"sum":0,"name":"主动搭讪一个用户","integral":"5","Total":10},{"sum":0,"name":"发出/收到红包","integral":"10","Total":10}]}
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
         * integral : {"integral_sum":2787,"integral_exchange":1887,"sign_times":1}
         * sign_in : [{"create_time":1545635757}]
         * daily_tasks : [{"sum":1,"name":"登录","integral":"10","Total":1},{"sum":1,"name":"签到","integral":"10","Total":1},{"integral":"10","sum":0,"name":"邀请ta传图、视频","Total":5},{"sum":0,"name":"停留时长大于30分钟","integral":"20","Total":1},{"sum":0,"name":"喜欢一个用户","integral":"5","Total":10},{"sum":0,"name":"动态、树洞发布","integral":"10","Total":5},{"sum":0,"name":"动态树洞内容点赞","integral":"5","Total":5},{"sum":0,"name":"动态树洞内容评论","integral":"5","Total":5},{"sum":0,"name":"分享","integral":"20","Total":10},{"sum":0,"name":"主动搭讪一个用户","integral":"5","Total":10},{"sum":0,"name":"发出/收到红包","integral":"10","Total":10}]
         */

        private IntegralBean integral;
        private List<SignInBean> sign_in;
        private List<DailyTasksBean> daily_tasks;

        public IntegralBean getIntegral() {
            return integral;
        }

        public void setIntegral(IntegralBean integral) {
            this.integral = integral;
        }

        public List<SignInBean> getSign_in() {
            return sign_in;
        }

        public void setSign_in(List<SignInBean> sign_in) {
            this.sign_in = sign_in;
        }

        public List<DailyTasksBean> getDaily_tasks() {
            return daily_tasks;
        }

        public void setDaily_tasks(List<DailyTasksBean> daily_tasks) {
            this.daily_tasks = daily_tasks;
        }

        public static class IntegralBean {
            /**
             * integral_sum : 2787
             * integral_exchange : 1887
             * sign_times : 1
             */

            private int integral_sum;
            private int integral_exchange;
            private int sign_times;

            public int getIntegral_sum() {
                return integral_sum;
            }

            public void setIntegral_sum(int integral_sum) {
                this.integral_sum = integral_sum;
            }

            public int getIntegral_exchange() {
                return integral_exchange;
            }

            public void setIntegral_exchange(int integral_exchange) {
                this.integral_exchange = integral_exchange;
            }

            public int getSign_times() {
                return sign_times;
            }

            public void setSign_times(int sign_times) {
                this.sign_times = sign_times;
            }
        }

        public static class SignInBean {
            /**
             * create_time : 1545635757
             */

            private long create_time;

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }
        }

        public static class DailyTasksBean {
            /**
             * sum : 1
             * name : 登录
             * integral : 10
             * Total : 1
             */

            private int sum;
            private String name;
            private String integral;
            private int Total;

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public int getTotal() {
                return Total;
            }

            public void setTotal(int Total) {
                this.Total = Total;
            }
        }
    }
}
