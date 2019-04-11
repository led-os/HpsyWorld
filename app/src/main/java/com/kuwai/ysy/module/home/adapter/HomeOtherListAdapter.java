package com.kuwai.ysy.module.home.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.HomepageListBean;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;


public class HomeOtherListAdapter extends BaseQuickAdapter<PersolHome2PageBean.DataBean.SeniorBean, BaseViewHolder> {

    public HomeOtherListAdapter() {
        super(R.layout.item_other_home_page_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersolHome2PageBean.DataBean.SeniorBean item) {

        helper.setText(R.id.tv_left, item.getName());
        helper.setText(R.id.tv_center, item.getData());
        helper.addOnClickListener(R.id.tv_look);

        if(item.getIs_see() == 1){
            helper.getView(R.id.tv_center).setVisibility(View.GONE);
            helper.getView(R.id.tv_look).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.tv_center).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_look).setVisibility(View.GONE);
        }
    }

}
