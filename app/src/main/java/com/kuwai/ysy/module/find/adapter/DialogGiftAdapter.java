package com.kuwai.ysy.module.find.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.NiceImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class DialogGiftAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {
    public DialogGiftAdapter(List data) {
        super(R.layout.item_gift_grid, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
