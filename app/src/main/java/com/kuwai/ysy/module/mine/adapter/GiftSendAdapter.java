package com.kuwai.ysy.module.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class GiftSendAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public GiftSendAdapter(List data) {
        super(R.layout.item_gift_send, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
