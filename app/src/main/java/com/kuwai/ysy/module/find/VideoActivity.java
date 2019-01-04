package com.kuwai.ysy.module.find;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.google.gson.Gson;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class VideoActivity extends BaseActivity{

    private AliyunVodPlayerView mAliyunVodPlayerView = null;
    private IAliyunVodPlayer.OnPreparedListener mOutPreparedListener = null;
    private StsBean mStsBean = null;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        initData();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_video;
    }

    private void initData() {

        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        mAliyunVodPlayerView.setKeepScreenOn(true);


        mAliyunVodPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                //准备完成触发
                if (mOutPreparedListener != null)
                    mOutPreparedListener.onPrepared();

                mAliyunVodPlayerView.start();
//                status = PLAY;
            }
        });
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                //首帧显示触发
            }
        });
        mAliyunVodPlayerView.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                //出错时处理，查看接口文档中的错误码和错误消息
            }
        });
        mAliyunVodPlayerView.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放正常完成时触发
            }
        });
        mAliyunVodPlayerView.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //seek完成时触发
            }
        });
//        mAliyunVodPlayerView.setLockPortraitMode(new IAliyunVodPlayer.LockPortraitListener() {
//            @Override
//            public void onLockScreenMode(int i) {
////                mAliyunVodPlayerView.setRenderRotate(AliyunScreenMode.Full);
//            }
//        });
        mAliyunVodPlayerView.setOnStoppedListener(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                //使用stop功能时触发
                if (VideoActivity.this != null) {
                    onStop();
                }
            }
        });
        mAliyunVodPlayerView.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                //视频清晰度切换成功后触发
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                //视频清晰度切换失败时触发
            }
        });
        mAliyunVodPlayerView.setOnCircleStartListener(new IAliyunVodPlayer.OnCircleStartListener() {
            @Override
            public void onCircleStart() {
                //循环播放开始
            }
        });

        //请求源过期
        mAliyunVodPlayerView.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                getSts();
            }
        });

        getSts();
    }

    private void preparePlay() {
        AliyunVidSts mVidSts = new AliyunVidSts();
        mVidSts.setVid("e626f51f35064961b95576d9a46925dd");
        mVidSts.setAcId(mStsBean.getData().getAccessKeyId());
        mVidSts.setAkSceret(mStsBean.getData().getAccessKeySecret());
        mVidSts.setSecurityToken(mStsBean.getData().getSecurityToken());
        mAliyunVodPlayerView.setVidSts(mVidSts);
    }

    void getSts() {
        addSubscription(ChatApiFactory.getSts().subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                if (stsBean.getCode() == 200) {
                    mStsBean = stsBean;
                    preparePlay();
                } else {
                    ToastUtils.showShort(stsBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
//                if (!isStrangePhone()) {
//                    getSupportActionBar().show();
//                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
//                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                if (!Utils.isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = (ViewGroup.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = 0;
//                }

            }

        }
    }
}
