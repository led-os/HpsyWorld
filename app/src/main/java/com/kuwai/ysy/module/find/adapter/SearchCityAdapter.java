package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.SearchCityBean;

import java.util.List;


public class SearchCityAdapter extends BaseQuickAdapter<SearchCityBean.DataBean.AreaBean, BaseViewHolder> {


    public SearchCityAdapter(List data) {
        super(R.layout.item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchCityBean.DataBean.AreaBean item) {
        ((TextView) helper.getView(R.id.tv_city)).setText(item.getRegion_name());
    }

}
