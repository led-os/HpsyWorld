package com.kuwai.ysy.module.find.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class ProvinceAdapter extends BaseQuickAdapter<ProvincesAndCityBean.DataBean, BaseViewHolder> {
    public ProvinceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvincesAndCityBean.DataBean item) {
        helper.setText(R.id.tv_city, item.getRegion_name());
        LinearLayout parent = helper.getView(R.id.lay_parent);
        TextView textView = helper.getView(R.id.tv_city);

        if (item.isChecked) {
            parent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            textView.setTextColor(mContext.getResources().getColor(R.color.city_press));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.left_line_color));
        } else {
            parent.setBackgroundColor(mContext.getResources().getColor(R.color.city_bg));
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            textView.setTextColor(Color.parseColor("#b3282828"));
        }
    }
}
