package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class MyApplyAdapter extends BaseQuickAdapter<MyBlindBean.DataBean, BaseViewHolder> {


    public MyApplyAdapter() {
        super(R.layout.item_my_apply);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBlindBean.DataBean item) {
        helper.setText(R.id.tv_nick, item.getTitle());
        helper.setText(R.id.tv_type, "地点：" + item.getArea());
        helper.setText(R.id.tv_time, "时间：" + DateTimeUitl.getTime(String.valueOf(item.getStart_time())));
        GlideUtil.loadRetangle(mContext, item.getAttach(), ((ImageView) helper.getView(R.id.img_pic)));
        switch (item.getStatus()){
            case 1:
                helper.setText(R.id.tv_time_state,"进行中");
                break;
            case 3:
                helper.setText(R.id.tv_time_state,"已结束");
                break;
        }

        //helper.addOnClickListener(R.id.rl_my_activity);
    }

}
