package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;

import java.util.List;


public class FilterAdapter extends BaseQuickAdapter<LocalNextBean.DataBean, BaseViewHolder> {


    public FilterAdapter(List data) {
        super(R.layout.item_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalNextBean.DataBean item) {
        ((TextView) helper.getView(R.id.tv_reason)).setText(item.getRegion_name());
        if (item.ischecked) {
            ((TextView) helper.getView(R.id.tv_reason)).setTextColor(mContext.getResources().getColor(R.color.theme));
        } else {
            ((TextView) helper.getView(R.id.tv_reason)).setTextColor(mContext.getResources().getColor(R.color.balck_28));
        }
    }

}
