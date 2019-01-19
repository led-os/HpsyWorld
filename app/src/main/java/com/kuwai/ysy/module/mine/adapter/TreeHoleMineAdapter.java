package com.kuwai.ysy.module.mine.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.rayhahah.rbase.utils.base.DateTimeUitl;


public class TreeHoleMineAdapter extends BaseQuickAdapter<PersonalTreeHole.DataBean, BaseViewHolder> {

    private boolean noMore = false;

    public TreeHoleMineAdapter() {
        super(R.layout.item_tree_hole);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalTreeHole.DataBean item) {
        helper.addOnClickListener(R.id.img_more);
        if (noMore) {
            helper.getView(R.id.img_more).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getText());
        helper.setText(R.id.tv_time, "发布于" + String.valueOf(DateTimeUitl.getStandardDate(String.valueOf(item.getCreate_time()))));
        helper.setText(R.id.tv_views, String.valueOf(item.getViews()) + "人看过");
    }

    public void setNoMore() {
        noMore = true;
    }
}
