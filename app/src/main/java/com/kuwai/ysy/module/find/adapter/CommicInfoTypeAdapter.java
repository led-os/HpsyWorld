package com.kuwai.ysy.module.find.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;

import java.util.List;


public class CommicInfoTypeAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public CommicInfoTypeAdapter(List data) {
        super(R.layout.item_commic_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
