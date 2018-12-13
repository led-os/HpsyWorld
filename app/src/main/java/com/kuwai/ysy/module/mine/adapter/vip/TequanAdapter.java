package com.kuwai.ysy.module.mine.adapter.vip;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class TequanAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public TequanAdapter(List data) {
        super(R.layout.item_tequan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
    }

}
