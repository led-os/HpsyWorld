package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class DialogOtherGiftAdapter extends BaseQuickAdapter<CommisDetailBean.DataBean.GiftBean, BaseViewHolder> {
    public DialogOtherGiftAdapter(List data) {
        super(R.layout.item_gift_grid, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommisDetailBean.DataBean.GiftBean item) {
        helper.setText(R.id.textView, item.getGirft_name());
        GlideUtil.loadRetangle(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.imageView));
    }

}
