package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.widget.NiceImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class DialogGiftAdapter extends BaseQuickAdapter<MyCommisDetailBean.DataBean.GiftBean, BaseViewHolder> {
    public DialogGiftAdapter(List data) {
        super(R.layout.item_gift_grid, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommisDetailBean.DataBean.GiftBean item) {
        helper.setText(R.id.textView, item.getGirft_name());
        GlideUtil.load(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.imageView));
    }

}
