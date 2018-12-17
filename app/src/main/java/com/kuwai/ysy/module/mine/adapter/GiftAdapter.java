package com.kuwai.ysy.module.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class GiftAdapter extends BaseQuickAdapter<GiftAcceptBean.DataBean.GiftBean, BaseViewHolder> {


    public GiftAdapter() {
        super(R.layout.item_gift);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftAcceptBean.DataBean.GiftBean item) {

        GlideUtil.load(mContext,item.getGirft_img_url(), (ImageView) helper.getView(R.id.iv_gift));
        helper.setText(R.id.tv_name,item.getGirft_name());
        helper.setText(R.id.tv_num,String.valueOf(item.getG_nums()));

    }

}
