package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class MeetFilterAdapter extends BaseQuickAdapter<MeetThemeBean.DataBean, BaseViewHolder> {
    public MeetFilterAdapter(List data) {
        super(R.layout.item_meet_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetThemeBean.DataBean item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_num, item.getCount() + "个结果");
        GlideUtil.load(mContext, item.getImg(), (ImageView) helper.getView(R.id.img_icon));
    }

}
