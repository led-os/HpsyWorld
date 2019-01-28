package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.widget.AmountRoundView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class GiftAddSumAdapter extends BaseQuickAdapter<GiftPopBean.DataBean, BaseViewHolder> {


    public GiftAddSumAdapter(List list) {
        super(R.layout.item_gift_sum, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GiftPopBean.DataBean item) {
        SuperButton roundView = helper.getView(R.id.gift_name);
        roundView.setText(item.getGirft_name());
        GlideUtil.loadRetangle(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));
        helper.setText(R.id.tv_num, "x" + item.num);
        helper.setText(R.id.tv_sum, Double.parseDouble(item.getPrice()) * item.num + "桃花币");

    }

}
