package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.RechargeBean;


public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {


    public RechargeAdapter() {
        super(R.layout.item_voucher_center);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        TextView num = helper.getView(R.id.tv_num);
        TextView price = helper.getView(R.id.tv_price);
        helper.setText(R.id.tv_num, item.getNum());
        helper.setText(R.id.tv_price, "售价：" + item.getPrice() + "元");
        LinearLayout mParentLay = helper.getView(R.id.lay_parent);

        if (item.isCheck()) {
            num.setTextColor(0xffffffff);
            price.setTextColor(0xffffffff);
            mParentLay.setBackgroundResource(R.drawable.voucher_money_select);

        } else {
            num.setTextColor(0xff282828);
            price.setTextColor(0x99282828);
            mParentLay.setBackgroundResource(R.drawable.voucher_money_shape);
        }
    }
}
