package com.kuwai.ysy.module.find.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class LocalCityAdapter extends BaseQuickAdapter<LocalNextBean.DataBean, BaseViewHolder> {
    public LocalCityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalNextBean.DataBean item) {
        helper.setText(R.id.tv_city, item.getRegion_name());

        TextView textView = helper.getView(R.id.tv_city);

        if (item.ischecked) {
            textView.setTextColor(mContext.getResources().getColor(R.color.city_press));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.balck_28));
        }
    }
}
