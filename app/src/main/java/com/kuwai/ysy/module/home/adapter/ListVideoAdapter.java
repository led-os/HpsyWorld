package com.kuwai.ysy.module.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayerview.widget.AovPlayerView;
import com.bumptech.glide.Glide;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseRecAdapter;
import com.kuwai.ysy.common.BaseRecViewHolder;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.VideoBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class ListVideoAdapter extends BaseRecAdapter<HomeVideoBean.DataBean, BaseRecViewHolder> {

    public ListVideoAdapter(List<HomeVideoBean.DataBean> list) {
        super(list);
    }

    @Override
    public void onHolder(BaseRecViewHolder holder, HomeVideoBean.DataBean bean, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        int viewType = holder.getItemViewType();
        if (viewType == C.DY_FILM) {
            VideoViewHolder holder1 = (VideoViewHolder) holder;
            if (list.get(position).isPlay()) {

                AliyunVidSts mVidSts = new AliyunVidSts();
                mVidSts.setVid(list.get(position).getVideo_id());
                mVidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
                mVidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
                mVidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));
                holder1.mp_video.setVidSts(mVidSts);
            } else {
                holder1.mp_video.PlayStop();
                //holder1.mp_video.destroy();
            }
        } else {
            PicViewHolder holder2 = (PicViewHolder) holder;
            holder2.mp_video.setImageLoader(new GlideImageLoader());
            holder2.mp_video.setImages(bean.getAttach());
            holder2.mp_video.start();
            holder2.mp_video.setBannerStyle(BannerConfig.NUM_INDICATOR);
            holder2.mp_video.isAutoPlay(false);
        }

        //holder.mp_video.setVidSts();
        //Glide.with(context).load(bean).into(holder.mp_video.thumbImageView);
    }

    @Override
    public BaseRecViewHolder onCreateHolder(int viewType) {
        if (viewType == C.DY_PIC) {
            return new PicViewHolder(getViewByRes(R.layout.item_pic_view));
        } else if (viewType == C.DY_FILM) {
            return new VideoViewHolder(getViewByRes(R.layout.item_page2));
        }
        return new VideoViewHolder(getViewByRes(R.layout.item_page2));
    }

    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootView;
        public AovPlayerView mp_video;
        public TextView tv_title;

        public VideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.video);
            this.tv_title = rootView.findViewById(R.id.tv_title);
        }
    }

    public class PicViewHolder extends BaseRecViewHolder {
        public View rootView;
        public Banner mp_video;
        public TextView tv_title;

        public PicViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.banner);
            this.tv_title = rootView.findViewById(R.id.tv_title);
        }
    }

    //重写getItemViewType方法 根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {

        HomeVideoBean.DataBean moreTypeBean = list.get(position);
        if (!Utils.isNullString(moreTypeBean.getVideo_id())) {
            return C.DY_FILM;
        } else {
            return C.DY_PIC;
        }
    }
}
