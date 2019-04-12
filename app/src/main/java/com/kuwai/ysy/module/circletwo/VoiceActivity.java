package com.kuwai.ysy.module.circletwo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.TestVoice;
import com.kuwai.ysy.utils.MediaPlayerUtils;
import com.kuwai.ysy.utils.MediaRecorderUtilsAmr;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.SecondsTimeFormatTime;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.io.File;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class VoiceActivity extends BaseActivity implements View.OnClickListener, MediaRecorderUtilsAmr.OnMediaRecorderDisposeInterfa, MediaPlayerUtils.OnMediaPlayerUtilsInterfa {

    //录音时间message标识
    private final int VOICE_RECORD_TIME = 0x12;
    //录音完成message标识
    private final int VOICE_RECORD_OK = 0X13;
    //录音错误message标识
    private final int ERROR_TAG = 0x14;
    //录音超过限制message标识
    private final int LENGTHTIME = 0x15;
    //播音倒计时message标识
    private final int MEDIAPLAYERTAG = 0x16;
    //播音完毕message标识
    private final int MEDIAPLAYEROK = 0X17;
    //录音标识
    public final static int RECORD_TAG = 0x1;
    //播放录音标识
    public final static int PLAY_RECORD_TAG = 0x2;

    private Chronometer chronometer;
    private RelativeLayout audioLayout;
    private TextView info;
    private RelativeLayout mic;
    private ImageView micIcon;
    private NavigationLayout navigationLayout;
    private RelativeLayout mRlVoice;
    private TextView mTimeTv;
    private String duration;
    private TextView tv_press;

    private MediaRecorderUtilsAmr mMediaRecorderUtils;
    private MediaPlayerUtils mMediaPlayerUtils;
    //语音录制存储文件夹
    private final static String FILEVOICE = "ysy/voice";
    //录音文件名字
    private final String FILENAME = "test";
    private File voiceFile = null;

    @Override
    protected int getLayoutID() {
        return R.layout.act_voice;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VOICE_RECORD_TIME:
                    String time = (String) msg.obj;
                    //mVoiceRecordTime.setText(null == time ? "00:00" : time);
                    break;
                case VOICE_RECORD_OK:
                    Toast.makeText(VoiceActivity.this, "录音保存成功", Toast.LENGTH_SHORT).show();
                    mic.setBackground(getResources().getDrawable(R.drawable.mic_bg));
                    voiceFile = (File) msg.obj;
                    if (voiceFile != null) {
                        String path = voiceFile.getPath();
                        mRlVoice.setVisibility(View.VISIBLE);
                        mMediaPlayerUtils = MediaPlayerUtils.getMediaPlayerUtils().init(VoiceActivity.this, path).setOnMediaPlayerUtilsInterfa(VoiceActivity.this);
                        String minuteTime = mMediaPlayerUtils.jsSecondMinuteText(new SecondsTimeFormatTime(), mMediaPlayerUtils.getDuration());
                        mTimeTv.setText(minuteTime);
                    }
                    break;
                case ERROR_TAG:
                    Toast.makeText(VoiceActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(500);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case LENGTHTIME:
                    //showDialog("录音保存中...").show();
                    //mVoiceRecordTime.setText("02:00");
                    mMediaRecorderUtils.saveRecord();
                    break;
                case MEDIAPLAYERTAG:
                    int mediaplayerTime = (int) msg.obj;
                    if (mediaplayerTime >= 0) {
                       /* if (null != mMediaPlayerUtils) {
                            String minuteText = mMediaPlayerUtils.jsSecondMinuteText(new SecondsTimeFormatText(), mediaplayerTime);
                            String minuteTime = mMediaPlayerUtils.jsSecondMinuteText(new SecondsTimeFormatTime(), mediaplayerTime);
                            mVoicePlayTime.setText("时间转换格式一：" + minuteText + "\n" + "时间转换格式二：" + minuteTime);
                        }*/
                    }
                    break;
                case MEDIAPLAYEROK:
                    //mediaPlayerPauseState();
                    break;
            }

        }
    };

    @Override
    protected void initView() {
        chronometer = (Chronometer) findViewById(R.id.time_display);
        chronometer.setOnChronometerTickListener(tickListener);
        micIcon = (ImageView) findViewById(R.id.mic_icon);
        mic = (RelativeLayout) findViewById(R.id.tv_mic);
        tv_press = findViewById(R.id.tv_press);
        navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPic(voiceFile);
            }
        });
        mRlVoice = findViewById(R.id.rl_voice);
        mTimeTv = findViewById(R.id.tv_time);
        info = (TextView) findViewById(R.id.tv_info);
        audioLayout = (RelativeLayout) findViewById(R.id.audio_layout);
        mic.setOnTouchListener(touchListener);
        iniRecordLayou();

        mRlVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayerUtils.start();
            }
        });

        changeXuan();
    }

    /**
     * 初始化录音布局
     */
    private void iniRecordLayou() {
        if (null == mMediaRecorderUtils)
            mMediaRecorderUtils = Utils.getRecorderUtilsAmr(this).init(getPath(this, FILEVOICE), FILENAME).setOnMediaRecorderDisposeInterfa(this);
    }

    /**
     * 获取保存路径
     *
     * @param activity
     * @param name     路径名字
     * @return
     */
    private String getPath(Context activity, String name) {
        String path = "";
        // 判断是否安装有SD卡
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/" + name;
        } else {
            path = activity.getCacheDir() + "/" + name;
        }
        return path;
    }

    private boolean isCancel = false;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            float downY = 0;
            int action = event.getAction();
            switch (v.getId()) {
                case R.id.tv_mic:
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            startAnim(true);
                            mMediaRecorderUtils.startRecord();
                            ret = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            stopAnim();
                            if (isCancel) {
                                isCancel = false;
                                //mediaUtils.stopRecordUnSave();
                                mMediaRecorderUtils.release();
                                Toast.makeText(VoiceActivity.this, "取消保存", Toast.LENGTH_SHORT).show();
                            } else {
                                int duration = getDuration(chronometer.getText().toString());
                                switch (duration) {
                                    case -1:
                                        break;
                                    case -2:
                                        mMediaRecorderUtils.release();
                                        //mediaUtils.stopRecordUnSave();
                                        Toast.makeText(VoiceActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        mMediaRecorderUtils.saveRecord();
                                        //mediaUtils.stopRecordSave();
                                        //String path = mediaUtils.getTargetFilePath();
                                        //Toast.makeText(AudioRecorderActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
                                        mMediaRecorderUtils.release();
                                        break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                moveAnim();
                                isCancel = true;
                            } else {
                                isCancel = false;
                                startAnim(false);
                            }
                            break;
                    }
                    break;
            }
            return ret;
        }
    };

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    Chronometer.OnChronometerTickListener tickListener = new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000) {
                stopAnim();
                //mediaUtils.stopRecordSave();
                Toast.makeText(VoiceActivity.this, "录音超时", Toast.LENGTH_SHORT).show();
                //String path = mediaUtils.getTargetFilePath();
                //Toast.makeText(VoiceActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                duration = d;
                return Integer.valueOf(d);
            } else {
                duration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            duration = "60";
            return -1;
        }

    }

    private void startAnim(boolean isStart) {
        audioLayout.setVisibility(View.VISIBLE);
        info.setText("上滑取消");
        //mic.setBackground(getResources().getDrawable(R.drawable.mic_pressed_bg));
        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.ic_mic_white_24dp));
        if (isStart) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%S");
            chronometer.start();
        }
    }

    private void stopAnim() {
        audioLayout.setVisibility(View.GONE);
        //tv_press.setText("重新录制");
        //mic.setBackground(getResources().getDrawable(R.drawable.mic_bg));
        chronometer.stop();
    }

    private void moveAnim() {
        info.setText("松手取消");
        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.ic_undo_black_24dp));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMediaRecorderTime(String time) {
        mHandler.sendMessage(getMessage(VOICE_RECORD_TIME, time));
    }

    @Override
    public void onMediaRecorderLengthTime(boolean lengthTag) {
        mHandler.sendMessage(getMessage(LENGTHTIME, lengthTag));
    }

    @Override
    public void onMediaRecorderError() {
        //mHandler.sendMessage(getMessage(ERROR_TAG, "录音初始化出错，请重试"));
    }

    @Override
    public void onMediaRecorderOK(File pathFile) {
        mHandler.sendMessage(getMessage(VOICE_RECORD_OK, pathFile));
    }

    private Message getMessage(int tag, Object object) {
        Message message = mHandler.obtainMessage();
        message.what = tag;
        message.obj = object;
        return message;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mMediaRecorderUtils)
            mMediaRecorderUtils.release();
    }

    @Override
    public void onMediaPlayerTime(int time) {
        //mHandler.sendMessage(getMessage(MEDIAPLAYERTAG, time));
    }

    @Override
    public void onMediaPlayerOk() {

    }

    @Override
    public void onMediaPlayerError() {

    }

    private void publishPic(File file) {
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();

        helper.addParameter("file" + 0 + "\";filename=\"" + file.getName(), file);
        addSubscription(MineApiFactory.uploadVoice(helper.builder()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                if (dyDetailBean.code == 200) {
                    ToastUtils.showShort("成功");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
                LogUtils.error(throwable);
            }
        }));

    }

    private void changeXuan() {
        addSubscription(MineApiFactory.getVoice().subscribe(new Consumer<TestVoice>() {
            @Override
            public void accept(TestVoice response) throws Exception {
                if (response.getCode() == 200) {
                        String path = response.getData();
                        mMediaPlayerUtils = MediaPlayerUtils.getMediaPlayerUtils().init(VoiceActivity.this, path).setOnMediaPlayerUtilsInterfa(VoiceActivity.this);
                        mRlVoice.setVisibility(View.VISIBLE);
                        String minuteTime = mMediaPlayerUtils.jsSecondMinuteText(new SecondsTimeFormatTime(), mMediaPlayerUtils.getDuration());
                        mTimeTv.setText(minuteTime);
                        mMediaPlayerUtils.start();
                }
                //ToastUtils.showShort(response.getMsg());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
