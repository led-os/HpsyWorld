package com.kuwai.ysy.module.home.adapter;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayerview.view.TipsView;
import com.aliyun.vodplayerview.widget.AovPlayerView;
import com.allen.library.SuperButton;
import com.amap.api.services.help.Tip;
import com.bumptech.glide.Glide;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.callback.HomeCallBack;
import com.kuwai.ysy.common.BaseRecAdapter;
import com.kuwai.ysy.common.BaseRecViewHolder;
import com.kuwai.ysy.listener.PlayerManager;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.VideoBean;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.GlideHomeLoader;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class ListVideoAdapter extends BaseRecAdapter<HomeVideoBean.DataBean, BaseRecViewHolder> {

    private PlayerManager playerManager;
    private HomeCallBack homeCallBack;

    public ListVideoAdapter(List<HomeVideoBean.DataBean> list, PlayerManager playerManager) {
        super(list);
        this.playerManager = playerManager;
    }

    public void setCallBack(HomeCallBack callBack) {
        this.homeCallBack = callBack;
    }

    @Override
    public void onHolder(final BaseRecViewHolder holder, final HomeVideoBean.DataBean bean, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        int viewType = holder.getItemViewType();
        if (viewType == C.DY_FILM) {
            final VideoViewHolder videoholder = (VideoViewHolder) holder;
            GlideUtil.load(context, bean.getAvatar(), videoholder.headImg);
            videoholder.giftTv.setText(bean.getGift_sum() + "");
            videoholder.loveTv.setText(bean.getLove_sum() + "");
            if (bean.getLove() == 1) {
                GlideUtil.load(context, R.drawable.home_home_ic_redherat, videoholder.heartImg);
            } else {
                GlideUtil.load(context, R.drawable.home_home_ic_heart_nor, videoholder.heartImg);
            }
            if (bean.getGift() == 1) {
                GlideUtil.load(context, R.drawable.home_home_ic_redgift, videoholder.giftImg);
            } else {
                GlideUtil.load(context, R.drawable.home_home_ic_gift_nor, videoholder.giftImg);
            }
            videoholder.mBtnDetail.setText(bean.getAge() + "岁|" + bean.getHeight() + "cm|" + bean.getEducation() + "|" + bean.getAnnual_income());
            videoholder.tv_title.setText(bean.getNickname());
            videoholder.setTag(position);
            if (position == 0  && bean.isPlay()) {
                TextureView textureView = new TextureView(context);
                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                    @Override
                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                        playerManager.start(videoholder);
                        playerManager.setSurfaceTexture(surface);
                    }

                    @Override
                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                        return true;
                    }

                    @Override
                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                    }
                });

                videoholder.addTextureView(textureView);
            }
           /* VideoViewHolder holder1 = (VideoViewHolder) holder;
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
            }*/

            videoholder.giftImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeCallBack != null) {
                        homeCallBack.giftClick(position);
                    }
                }
            });
            videoholder.heartImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeCallBack != null) {
                        homeCallBack.heartClick(position);
                    }
                }
            });

            videoholder.headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                        if (!SPManager.get().getStringValue("uid").equals(String.valueOf(bean.getUid()))) {
                            Intent intent1 = new Intent(context, OtherHomeActivity.class);
                            intent1.putExtra("uid", String.valueOf(bean.getUid()));
                            context.startActivity(intent1);
                        }
                    } else {
                        ToastUtils.showShort(R.string.login_error);
                    }
                }
            });
        } else {
            PicViewHolder holder2 = (PicViewHolder) holder;
            GlideUtil.load(context, bean.getAvatar(), holder2.headImg);
            holder2.giftTv.setText(bean.getGift_sum() + "");
            holder2.loveTv.setText(bean.getLove_sum() + "");

            holder2.giftImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeCallBack != null) {
                        homeCallBack.giftClick(position);
                    }
                }
            });
            holder2.heartImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeCallBack != null) {
                        homeCallBack.heartClick(position);
                    }
                }
            });

            holder2.headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                        if (!SPManager.get().getStringValue("uid").equals(String.valueOf(bean.getUid()))) {
                            Intent intent1 = new Intent(context, OtherHomeActivity.class);
                            intent1.putExtra("uid", String.valueOf(bean.getUid()));
                            context.startActivity(intent1);
                        }
                    } else {
                        ToastUtils.showShort(R.string.login_error);
                    }
                }
            });

            if (bean.getLove() == 1) {
                GlideUtil.load(context, R.drawable.home_home_ic_redherat, holder2.heartImg);
            } else {
                GlideUtil.load(context, R.drawable.home_home_ic_heart_nor, holder2.heartImg);
            }
            if (bean.getGift() == 1) {
                GlideUtil.load(context, R.drawable.home_home_ic_redgift, holder2.giftImg);
            } else {
                GlideUtil.load(context, R.drawable.home_home_ic_gift_nor, holder2.giftImg);
            }
            holder2.tvDetail.setText(bean.getAge() + "岁|" + bean.getHeight() + "cm|" + bean.getEducation() + "|" + bean.getAnnual_income());
            holder2.tv_title.setText(bean.getNickname());
            holder2.mp_video.setImages(bean.getAttach());
            holder2.mp_video.start();
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
        return new PicViewHolder(getViewByRes(R.layout.item_pic_view));
    }

    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootView;
        //public AovPlayerView mp_video;
        public TextView tv_title;
        private int position;
        private SuperButton mBtnDetail;
        public TipsView tipsView;
        private ImageView giftImg, heartImg, headImg;
        private TextView loveTv, giftTv;

        public VideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            //this.mp_video = rootView.findViewById(R.id.video);
            this.mBtnDetail = rootView.findViewById(R.id.tv_detail);
            this.tv_title = rootView.findViewById(R.id.tv_title);
            giftImg = rootView.findViewById(R.id.img_gift);
            heartImg = rootView.findViewById(R.id.img_heart);
            headImg = rootView.findViewById(R.id.img_head);
            loveTv = rootView.findViewById(R.id.tv_love_num);
            giftTv = rootView.findViewById(R.id.tv_gift_num);
        }

        public void addTextureView(TextureView textureView) {
            tipsView = new TipsView(context);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            ((RelativeLayout) VideoViewHolder.this.itemView).addView(textureView, 0, param);
            ((RelativeLayout) VideoViewHolder.this.itemView).addView(tipsView, 1, param);
        }

        public int getTag() {
            return position;
        }

        public void removeTextureView() {
            tipsView = null;
            View firstChildView = ((RelativeLayout) VideoViewHolder.this.itemView).getChildAt(0);
            if (firstChildView instanceof TextureView) {
                ((RelativeLayout) VideoViewHolder.this.itemView).removeView(firstChildView);
            }

            View secondChild = ((RelativeLayout) VideoViewHolder.this.itemView).getChildAt(1);
            if (secondChild instanceof TipsView) {
                ((RelativeLayout) VideoViewHolder.this.itemView).removeView(secondChild);
            }
        }

        public TipsView getTips() {
            return tipsView;
        }

        public void setTag(int position) {
            this.position = position;
            if (playerManager.getPosition() == position) {
                //是当前播放的item
                //playView.setVisibility(View.INVISIBLE);
            } else {
                viewStopped();
            }
        }

        public void stop() {
            //Log.d(TAG, "holder stop pos = " + position);
            viewStopped();
        }

        private void viewStopped() {
            removeTextureView();
            //playView.setVisibility(View.VISIBLE);
        }
    }

    public class PicViewHolder extends BaseRecViewHolder {
        public View rootView;
        public Banner mp_video;
        public TextView tv_title;
        private SuperButton tvDetail;
        private ImageView giftImg, heartImg, headImg;
        private TextView loveTv, giftTv;

        public PicViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvDetail = rootView.findViewById(R.id.tv_detail);
            this.mp_video = rootView.findViewById(R.id.banner);
            this.tv_title = rootView.findViewById(R.id.tv_title);
            loveTv = rootView.findViewById(R.id.tv_love_num);
            giftTv = rootView.findViewById(R.id.tv_gift_num);
            giftImg = rootView.findViewById(R.id.img_gift);
            heartImg = rootView.findViewById(R.id.img_heart);
            headImg = rootView.findViewById(R.id.img_head);
            mp_video.setBannerStyle(BannerConfig.NUM_INDICATOR);
            mp_video.setImageLoader(new GlideHomeLoader());
            mp_video.isAutoPlay(false);
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
