package com.kuwai.ysy.module.mine.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.utils.BlurTransformation;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class PicAdapter extends BaseQuickAdapter<PersolHomePageBean.DataBean.InfoBean.VideoBean, BaseViewHolder> {


    public PicAdapter() {
        super(R.layout.item_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersolHomePageBean.DataBean.InfoBean.VideoBean item) {
        ImageView imageView = helper.getView(R.id.img_pic);
        ImageView video = helper.getView(R.id.iv_video);
        if (!Utils.isNullString(item.getVideo_id())) {
            video.setVisibility(View.VISIBLE);
        } else {
            video.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(item.getAttach()).into(imageView);
    }

}
