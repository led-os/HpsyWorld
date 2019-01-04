package com.kuwai.ysy.module.home.adapter;


import android.view.ViewGroup;

import com.aliyun.vodplayerview.widget.AovPlayerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.home.bean.HomeMutiBean;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.Arrays;
import java.util.List;


public class VideoAdapter extends BaseMultiItemQuickAdapter<HomeMutiBean, BaseViewHolder> {

    private Banner mBanner = null;
    private String[] imgList = new String[]{"http://img.kaiyanapp.com/fa978756b844c4facbc08656a9916415.jpeg?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/gl.jpg",
            "http://img.kaiyanapp.com/d7e21f93f4dcb6e78271d125a1f41a9e.png?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/cq.jpg",
            "http://img.kaiyanapp.com/5ae529b018ada5073d486242afc855b7.jpeg?imageMogr2/quality/60/format/jpg",
            "http://img.kaiyanapp.com/4631818cd092e281dc2c93b250684d9f.jpeg?imageMogr2/quality/60/format/jpg",
            "http://pic.chinahpsy.com/home/750/yddm.jpg",
            "http://img.kaiyanapp.com/bc2479c09cd15cb93b69d82e5f21c3fc.png?imageMogr2/quality/60/format/jpg",};

    public VideoAdapter(List data) {
        super(data);
        addItemType(HomeMutiBean.TEXT, R.layout.item_banner);
        addItemType(HomeMutiBean.VIDEO, R.layout.item_page2);
        addItemType(HomeMutiBean.IMG_TEXT, R.layout.item_home_header);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeMutiBean item) {
        switch (helper.getItemViewType()) {
            case HomeMutiBean.TEXT:
                mBanner = helper.getView(R.id.banner);
                mBanner.setImageLoader(new GlideImageLoader());
                mBanner.setImages(Arrays.asList(imgList));
                mBanner.start();
                break;
            case HomeMutiBean.IMG_TEXT:

                break;
            case HomeMutiBean.VIDEO:
                AovPlayerView aovPlayerView = helper.getView(R.id.video);
                break;
        }
    }
}
