package com.kuwai.ysy.module.mine.adapter.homepage;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.utils.TimeFormatterUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class MineDyAdapter extends BaseQuickAdapter<DyMainListBean.DataBean, BaseViewHolder> {


    public MineDyAdapter() {
        super(R.layout.item_page_dy_mine);
    }

    @Override
    protected void convert(BaseViewHolder helper, DyMainListBean.DataBean item) {
        helper.setText(R.id.tv_content, item.getText());
        helper.setText(R.id.day, TimeFormatterUtils.stampToMonth(item.getUpdate_time()) + "");
        helper.setText(R.id.month, TimeFormatterUtils.stampToDaty(item.getUpdate_time()) + "");
        if (item.getAttach().size() > 0) {
            helper.getView(R.id.img_pic).setVisibility(View.VISIBLE);
            GlideUtil.loadRetangle(mContext, item.getAttach().get(0), (ImageView) helper.getView(R.id.img_pic));
        } else {
            helper.getView(R.id.img_pic).setVisibility(View.GONE);
        }
    }

}
