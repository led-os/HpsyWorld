package com.kuwai.ysy.module.mine.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.utils.BlurTransformation;
import com.rayhahah.rbase.utils.base.DateTimeUitl;


public class WalletDetailsAdapter extends BaseQuickAdapter<WalletDetailsBean.DataBean, BaseViewHolder> {


    public WalletDetailsAdapter() {
        super(R.layout.item_money_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletDetailsBean.DataBean item) {

        helper.setText(R.id.tv_status_text, item.getReason());

        helper.setText(R.id.tv_time, DateTimeUitl.getIntegralTime(String.valueOf(item.getCreate_time())));

        if ("-".equals(item.getType())) {
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.balck_28));
            helper.setText(R.id.tv_money, item.getType() + item.getNumber());
        } else {
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.filter_text_select));
            helper.setText(R.id.tv_money, item.getType() + item.getNumber());
        }

    }

}
