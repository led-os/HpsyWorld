package com.kuwai.ysy.module.mine.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.SearchCityBean;
import com.kuwai.ysy.module.mine.bean.JobBean;

import java.util.List;


public class JobAdapter extends BaseQuickAdapter<JobBean.DataBean, BaseViewHolder> {


    public JobAdapter() {
        super(R.layout.item_text);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobBean.DataBean item) {
        ((TextView) helper.getView(R.id.tv_city)).setText(item.getName());
    }

}
