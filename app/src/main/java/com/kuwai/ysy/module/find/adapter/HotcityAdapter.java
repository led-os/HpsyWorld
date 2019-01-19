package com.kuwai.ysy.module.find.adapter;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.ThemeBean;

import java.util.List;


public class HotcityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HotcityAdapter(List data) {
        super(R.layout.item_hotcity, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SuperButton chengyiBtn = helper.getView(R.id.tv_chengyi);
        chengyiBtn.setText(item);
    }

}
