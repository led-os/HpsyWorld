package com.kuwai.ysy.module.mine.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;

import java.util.List;

public class LikeParentBean extends AbstractExpandableItem<LikeBean> implements MultiItemEntity {


    /**
     * code : 200
     * msg : 获取成功
     * data : {"sum":10,"today":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg","nickname":"张三","uid":1,"gender":1,"v_id":22,"create_time":1544238373,"type":"1","d_id":2,"text":"动态2","is_vip":1}],"earlier":[{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg","nickname":"张三","uid":1,"gender":1,"v_id":22,"create_time":1544238373,"type":"1","d_id":2,"text":"动态2","is_vip":1}]}
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

    @Override
    public int getLevel() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class DataBean {
        /**
         * sum : 10
         * today : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg","nickname":"张三","uid":1,"gender":1,"v_id":22,"create_time":1544238373,"type":"1","d_id":2,"text":"动态2","is_vip":1}]
         * earlier : [{"avatar":"http://192.168.1.88/public/static/img/avatar/201812/15/b4ae125fa05e84917c6f8cfd17e5003b.jpg","nickname":"张三","uid":1,"gender":1,"v_id":22,"create_time":1544238373,"type":"1","d_id":2,"text":"动态2","is_vip":1}]
         */

        private int sum;
        private List<LikeBean> today;
        private List<LikeBean> earlier;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<LikeBean> getToday() {
            return today;
        }

        public void setToday(List<LikeBean> today) {
            this.today = today;
        }

        public List<LikeBean> getEarlier() {
            return earlier;
        }

        public void setEarlier(List<LikeBean> earlier) {
            this.earlier = earlier;
        }

    }
}
