package com.kuwai.ysy.module.mine.adapter.homepage;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class MineDyAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public MineDyAdapter(List data) {
        super(R.layout.item_page_dy_mine, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
