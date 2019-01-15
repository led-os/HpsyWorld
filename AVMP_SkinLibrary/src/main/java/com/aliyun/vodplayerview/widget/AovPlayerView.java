package com.aliyun.vodplayerview.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.view.TipsView;
import com.bumptech.glide.Glide;


import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


public class AovPlayerView extends RelativeLayout {
    private static final String TAG = "AovPlayer";
    private SurfaceView mSurfaceView;
    private AliyunVodPlayer mAliyunVodPlayer;
    private TipsView mTipsView;
    private ImageView mCoverView;
    private int ScrOrientation = 0;
    private boolean isPlaying = false;
    private IAliyunVodPlayer.OnPreparedListener mOutPreparedListener = null;
    private IAliyunVodPlayer.OnTimeExpiredErrorListener mOutTimeExpiredErrorListener = null;
    private IAliyunVodPlayer.OnUrlTimeExpiredListener onUrlTimeExpiredListener = null;


    public AovPlayerView(Context context) {
        super(context);
        initVideoView();
    }

    public AovPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView();
    }

    public AovPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView();
    }

    private void initVideoView() {
        //初始化播放用的surfaceView
        initSurfaceView();
        //初始化播放器
        initAliVcPlayer();
        //初始化封面
        initCoverView();
        initTipsView();
        initListener();

    }

    private void initTipsView() {

        mTipsView = new TipsView(getContext());
        addSubView(mTipsView);
    }

    private void initListener() {

        //准备好
        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (mOutPreparedListener != null) {
                    mOutPreparedListener.onPrepared();
                }
                if (ScrOrientation == 1) {
                    mAliyunVodPlayer.setRenderRotate(IAliyunVodPlayer.VideoRotate.ROTATE_90);
                }
                mAliyunVodPlayer.start();
                isPlaying = true;
                mTipsView.hideNetLoadingTipView();
                mCoverView.setVisibility(INVISIBLE);

            }

        });
        //请求源过期
        mAliyunVodPlayer.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                if (mOutTimeExpiredErrorListener != null) {
                    mOutTimeExpiredErrorListener.onTimeExpiredError();
                }

            }
        });
        //播放地址即将过期
        mAliyunVodPlayer.setOnUrlTimeExpiredListener(new IAliyunVodPlayer.OnUrlTimeExpiredListener() {
            @Override
            public void onUrlTimeExpired(String s, String s1) {
                if (mOutTimeExpiredErrorListener != null) {
                    onUrlTimeExpiredListener.onUrlTimeExpired(s, s1);
                }
            }
        });
        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int i, int i1, String s) {
                Log.d(TAG, "onError: " + i + "错误2:" + i1 + ":::::" + s);
            }
        });
    }

    private void initCoverView() {
        mCoverView = new ImageView(getContext());
        addSubView(mCoverView);
    }

    private void initAliVcPlayer() {
        mAliyunVodPlayer = new AliyunVodPlayer(getContext());
        setKeepScreenOn(true);
        mAliyunVodPlayer.setDisplay(mSurfaceView.getHolder());
        mAliyunVodPlayer.setCirclePlay(true);

        /*//全屏
        ((Activity) getContext()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/
    }

    private void initSurfaceView() {
        mSurfaceView = new SurfaceView(getContext().getApplicationContext());
        addSubView(mSurfaceView);

        SurfaceHolder holder = mSurfaceView.getHolder();
        //增加surfaceView的监听
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                mAliyunVodPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,
                                       int height) {
                mAliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });


    }


    private SimpleTarget<Drawable> mTarget = new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            int h = resource.getIntrinsicHeight();
            int w = resource.getIntrinsicWidth();
            if (w > h) {
                ScrOrientation = 1;
            }
            mCoverView.setImageDrawable(resource);

        }
    };

    /**
     * 添加view
     */

    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //添加到布局中
        addView(view, params);
    }


    private void prepareVidsts(AliyunVidSts vidSts) {
        mTipsView.showNetLoadingTipView();
        mAliyunVodPlayer.prepareAsync(vidSts);
    }

    public void PlayStop() {

        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.stop();
            isPlaying = false;
        }
    }

    public void PlayStart() {

        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.start();
            isPlaying = true;
        }
    }


    public void destroy() {
        //((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.release();
            mAliyunVodPlayer = null;
        }
       /* if (mSurfaceView != null) {
            mSurfaceView = null;
        }
        if (mCoverView != null) {
            mSurfaceView = null;
        }
        if (mTipsView != null) {
            mTipsView = null;
        }*/
        isPlaying = false;
    }

    public void setVidSts(AliyunVidSts vidSts) {
        if (mAliyunVodPlayer == null) {
            return;
        }

        prepareVidsts(vidSts);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setImg(String uri) {
        Glide.with(getContext()).load(uri).into(mTarget);
    }

    public void setOnPreparedListener(IAliyunVodPlayer.OnPreparedListener l) {
        mOutPreparedListener = l;
    }

    public void setOnTimeExpiredErrorListener(IAliyunVodPlayer.OnTimeExpiredErrorListener l) {
        mOutTimeExpiredErrorListener = l;
    }

    public void setOnUrlTimeExpiredListener(IAliyunVodPlayer.OnUrlTimeExpiredListener l) {
        onUrlTimeExpiredListener = l;
    }

}
