package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.widget.AmountRoundView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class GiftAddAdapter extends BaseQuickAdapter<GiftPopBean.DataBean, BaseViewHolder> {


    public GiftAddAdapter(List list) {
        super(R.layout.item_gift_bu, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GiftPopBean.DataBean item) {
        AmountRoundView roundView = helper.getView(R.id.amount);
        GlideUtil.load(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));
        helper.setText(R.id.tv_name, item.getGirft_name());
        helper.setText(R.id.tv_money, item.getPrice() + "桃花币");
        roundView.setOnAmountChangeListener(new AmountRoundView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                item.num = amount;
            }
        });

    }

}
