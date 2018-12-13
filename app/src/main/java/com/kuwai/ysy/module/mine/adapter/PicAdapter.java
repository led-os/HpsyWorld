package com.kuwai.ysy.module.mine.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.BlurTransformation;

import java.util.List;


public class PicAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public PicAdapter(List data) {
        super(R.layout.item_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        ImageView imageView = helper.getView(R.id.img_pic);
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //高斯模糊 范围在 0 -- 25 越大模糊程度越高
                .transforms(new BlurTransformation(mContext, 15));
        Glide.with(mContext).load(R.drawable.banner).apply(options).into(imageView);
    }

}