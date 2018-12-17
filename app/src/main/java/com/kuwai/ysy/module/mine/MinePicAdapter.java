package com.kuwai.ysy.module.mine;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.utils.BlurTransformation;


public class MinePicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MinePicAdapter() {
        super(R.layout.item_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.img_pic);

        Glide.with(mContext).load(item).into(imageView);

    }

}
