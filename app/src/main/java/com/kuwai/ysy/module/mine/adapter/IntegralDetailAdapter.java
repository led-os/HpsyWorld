package com.kuwai.ysy.module.mine.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;

public class IntegralDetailAdapter extends BaseQuickAdapter<IntegralDetailBean.DataBean, BaseViewHolder> {


    public IntegralDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralDetailBean.DataBean item) {

        helper.setText(R.id.tv_title, item.getName());
        //helper.setText(R.id.tv_time, DateTimeUitl.getIntegralTime(String.valueOf(item.getCreate_time())));

        if ("+".equals(item.getType())) {
            helper.setTextColor(R.id.tv_num, Color.parseColor("#ff282828"));
        } else {
            helper.setTextColor(R.id.tv_num, mContext.getResources().getColor(R.color.balck_28));
        }
        helper.setText(R.id.tv_num, item.getType() + String.valueOf(item.getIntegral()));

    }
}
