package com.kuwai.ysy.module.circle.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.others.NineImageAdapter;
import com.kuwai.ysy.widget.NineGridView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;

import java.util.Arrays;
import java.util.List;


public class TreeHoleAdapter extends BaseQuickAdapter<HoleMainListBean.DataBean.TreeHoleListBean, BaseViewHolder> {

    public TreeHoleAdapter() {
        super(R.layout.item_tree_hole);
    }

    @Override
    protected void convert(BaseViewHolder helper, HoleMainListBean.DataBean.TreeHoleListBean item) {
        helper.addOnClickListener(R.id.img_more);
        helper.setText(R.id.tv_title, item.getTitle());
        switch (item.getHot()) {
            case 0:
                helper.getView(R.id.id_hot).setVisibility(View.GONE);
                break;
            case 1:
                helper.getView(R.id.id_hot).setVisibility(View.VISIBLE);

                break;
        }
        helper.setText(R.id.tv_content, item.getText());
        helper.setText(R.id.tv_time, "发布于" + String.valueOf(DateTimeUitl.getStandardDate(item.getCreate_time())));
        helper.setText(R.id.tv_views, String.valueOf(item.getViews()) + "人看过");
    }

}
