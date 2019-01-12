package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.widget.PileLayout;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class CityAdapter extends BaseQuickAdapter<LocalNextBean.DataBean, BaseViewHolder> {


    public CityAdapter(List data) {
        super(R.layout.item_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalNextBean.DataBean item) {
        ((TextView) helper.getView(R.id.tv_reason)).setText(item.getRegion_name());
        if (item.ischecked) {
            helper.getView(R.id.img).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.tv_reason)).setTextColor(mContext.getResources().getColor(R.color.theme));
        } else {
            ((TextView) helper.getView(R.id.tv_reason)).setTextColor(mContext.getResources().getColor(R.color.balck_28));
            helper.getView(R.id.img).setVisibility(View.GONE);
        }
    }

}
