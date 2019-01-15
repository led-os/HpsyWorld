package com.kuwai.ysy.module.chat.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.ReportBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.TextImageView;

import java.util.List;


public class ReportAdapter extends BaseQuickAdapter<ReportBean, BaseViewHolder> {
    public ReportAdapter(List data) {
        super(R.layout.item_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportBean item) {
        ((TextView) helper.getView(R.id.tv_reason)).setText(item.getReason());
        ((ImageView) helper.getView(R.id.img)).setImageResource(item.isCheck() ? R.drawable.ic_list_select : R.drawable.ic_list_unselect);
    }

}
