package com.kuwai.ysy.module.circle.adapter.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class LikeMsgAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public LikeMsgAdapter(List data) {
        super(R.layout.item_dy_message_zan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
