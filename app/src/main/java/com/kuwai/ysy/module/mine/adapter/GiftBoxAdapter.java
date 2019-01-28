package com.kuwai.ysy.module.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class GiftBoxAdapter extends BaseQuickAdapter<GiftBoxBean.DataBean, BaseViewHolder> {


    public GiftBoxAdapter() {
        super(R.layout.item_gift);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftBoxBean.DataBean item) {

        GlideUtil.loadRetangle(mContext,item.getGirft_img_url(), (ImageView) helper.getView(R.id.iv_gift));
        helper.setText(R.id.tv_name,item.getGirft_name());
        helper.setText(R.id.tv_num,String.valueOf(item.getG_nums()));

    }

}
