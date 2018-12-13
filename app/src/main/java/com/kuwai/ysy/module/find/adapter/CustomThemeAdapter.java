package com.kuwai.ysy.module.find.adapter;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.HomeAppUavBean;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;

import java.util.List;


public class CustomThemeAdapter extends BaseQuickAdapter<DateTheme.DataBean.CustomHotBean, BaseViewHolder> {

    public CustomThemeAdapter(List data) {
        super(R.layout.item_theme_custom, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateTheme.DataBean.CustomHotBean item) {
        //helper.setText(R.id.tv_home_message, item.getClass_name());
        helper.setText(R.id.tv_type_date, item.getName());
        SuperButton button = (SuperButton) helper.getView(R.id.tv_type_date);
        helper.addOnClickListener(R.id.tv_type_date);
        if (item.isCheck) {
            button.setShapeStrokeColor(mContext.getResources().getColor(R.color.filter_text_select)).setShapeSolidColor(mContext.getResources().getColor(R.color.filter_solid_select)).setUseShape();
            button.setTextColor(mContext.getResources().getColor(R.color.filter_text_select));
        } else {
            button.setShapeStrokeColor(mContext.getResources().getColor(R.color.bg_color_f4)).setShapeSolidColor(mContext.getResources().getColor(R.color.bg_color_f4)).setUseShape();
            button.setTextColor(mContext.getResources().getColor(R.color.balck_28));
        }
    }

}

