package com.kuwai.ysy.module.mine.adapter.vip;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;

import java.util.List;


public class HuangjinVipFeeAdapter extends BaseQuickAdapter<VipPayBean, BaseViewHolder> {


    public HuangjinVipFeeAdapter(List data) {
        super(R.layout.item_vip_money, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipPayBean item) {
        TextView unit = helper.getView(R.id.tv_unit);
        TextView price = helper.getView(R.id.money);
        helper.setText(R.id.type, item.getType());
        helper.setText(R.id.money, item.getPrice());
        helper.setText(R.id.des, item.getDes());
        LinearLayout parent = helper.getView(R.id.parent);
        switch (item.getVipType()) {
            case 1:
                unit.setTextColor(mContext.getResources().getColor(R.color.vip_huangjin));
                price.setTextColor(mContext.getResources().getColor(R.color.vip_huangjin));
                break;
            case 2:
                unit.setTextColor(mContext.getResources().getColor(R.color.vip_baijin));
                price.setTextColor(mContext.getResources().getColor(R.color.vip_baijin));
                break;
            case 3:
                unit.setTextColor(mContext.getResources().getColor(R.color.vip_zuanshi));
                price.setTextColor(mContext.getResources().getColor(R.color.vip_zuanshi));
                break;
            case 4:
                unit.setTextColor(mContext.getResources().getColor(R.color.vip_super));
                price.setTextColor(mContext.getResources().getColor(R.color.vip_super));
                break;
            default:
                unit.setTextColor(mContext.getResources().getColor(R.color.vip_huangjin));
                price.setTextColor(mContext.getResources().getColor(R.color.vip_huangjin));
                break;
        }
        if (item.isCheck()) {
            switch (item.getVipType()) {
                case 1:
                    parent.setBackgroundResource(R.drawable.shape_huangjin_select);
                    break;
                case 2:
                    parent.setBackgroundResource(R.drawable.shape_baijin_select);
                    break;
                case 3:
                    parent.setBackgroundResource(R.drawable.shape_zuanshi_select);
                    break;
                case 4:
                    parent.setBackgroundResource(R.drawable.shape_super_select);
                    break;
                default:
                    parent.setBackgroundResource(R.drawable.shape_huangjin_select);
                    break;
            }
        } else {
            parent.setBackgroundResource(R.drawable.shape_question);
        }
    }

}
