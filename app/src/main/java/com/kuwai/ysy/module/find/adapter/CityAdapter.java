package com.kuwai.ysy.module.find.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.PileLayout;

import java.util.ArrayList;
import java.util.List;


public class CityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CityAdapter(List data) {
        super(R.layout.item_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
