package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class BlindListAdapter extends BaseQuickAdapter<TuoDanBean.DataBean, BaseViewHolder> {


    public BlindListAdapter() {
        super(R.layout.item_blind);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuoDanBean.DataBean item) {
        helper.setText(R.id.tv_nick, item.getTitle());
        helper.setText(R.id.tv_text, item.getText());
        SuperButton stateBtn = helper.getView(R.id.btn_state);
        helper.setText(R.id.tv_num, String.valueOf(item.getEnrolment()));
        if (Double.parseDouble(item.getRegistration_fee()) > 0) {
            helper.getView(R.id.tv_fee).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_fee).setVisibility(View.GONE);
        }
        GlideUtil.loadRetangle(mContext, item.getAttach(), ((ImageView) helper.getView(R.id.img_pic)));

        switch (item.getStatus()) {
            case 1:
                stateBtn.setShapeSolidColor(mContext.getResources().getColor(R.color.bg_orange)).setUseShape();
                helper.setText(R.id.btn_state, "进行中");
                break;
            case 3:
                stateBtn.setShapeSolidColor(mContext.getResources().getColor(R.color.gray_7b)).setUseShape();
                helper.setText(R.id.btn_state, "已结束");
                break;
        }

    }

}
