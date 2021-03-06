package com.kuwai.ysy.listener;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.home.adapter.ListVideoAdapter;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.aliyun.vodplayer.media.IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING;

public class PlayerManager {

    private Context context;
    private AliyunVodPlayer vodPlayer;
    private int position = -1;
    private List<HomeVideoBean.DataBean> urlList;

    private SurfaceTexture surfaceTexture;
    private ListVideoAdapter.VideoViewHolder holder;

    public PlayerManager(final Context context, AliyunVodPlayer player) {
        this.context = context;
        vodPlayer = player;
        vodPlayer.enableNativeLog();
        vodPlayer.setCirclePlay(true);
        //vodPlayer.setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING );
        vodPlayer.setAutoPlay(true);
        vodPlayer.setReferer("http://aliyun.com");
        vodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                stop();
                getSts();
            }
        });

        vodPlayer.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                getSts();
            }
        });

        vodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (holder.getTips() != null) {
                    holder.tipsView.hideNetLoadingTipView();
                }
                vodPlayer.start();
            }

        });
    }

    public void start(ListVideoAdapter.VideoViewHolder holder) {
        if (holder.getTag() == this.position) {
            return;
        }

        stop();

        this.holder = holder;
        this.position = holder.getTag();

        AliyunVidSts mVidSts = new AliyunVidSts();
        mVidSts.setVid(urlList.get(position).getVideo_id());
        mVidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
        mVidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
        mVidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));

        vodPlayer.reset();
        if (holder.tipsView != null) {
            holder.tipsView.showNetLoadingTipView();
        }
        vodPlayer.prepareAsync(mVidSts);
    }

    void getSts() {
        ChatApiFactory.getSts().subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                if (stsBean.getCode() == 200) {
                    SPManager.get().putString(C.ALI_ACID, stsBean.getData().getAccessKeyId());
                    SPManager.get().putString(C.ALI_SECRET, stsBean.getData().getAccessKeySecret());
                    SPManager.get().putString(C.ALI_TOKEN, stsBean.getData().getSecurityToken());
                } else {
                    //ToastUtils.showShort(stsBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        });
    }

    public void setData(List<HomeVideoBean.DataBean> url) {
        urlList = url;
    }

    public void resetTextureView() {
        if (holder != null) {
            holder.removeTextureView();

            TextureView textureView = new TextureView(context);
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    setSurfaceTexture(surface);
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

            holder.addTextureView(textureView);
        }

    }

    public void reset() {
        if (holder != null) {
            holder.removeTextureView();

            TextureView textureView = new TextureView(context);
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    setSurfaceTexture(surface);
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

            holder.addTextureView(textureView);
        }

    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
        Surface surface = new Surface(surfaceTexture);
        vodPlayer.setSurface(surface);
    }

    public void stop() {

        if (position < 0) {
            return;
        }
        this.position = -1;
        vodPlayer.stop();

        if (holder != null) {
//                clearSurface(surfaceTexture);
            holder.stop();
        }
    }

    public int getPosition() {
        return position;
    }

    public void release() {
        stop();
        vodPlayer.release();
    }

}
