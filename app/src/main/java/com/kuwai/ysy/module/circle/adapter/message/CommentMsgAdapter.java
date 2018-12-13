package com.kuwai.ysy.module.circle.adapter.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class CommentMsgAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public CommentMsgAdapter(List data) {
        super(R.layout.item_dy_message_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
