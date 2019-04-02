package com.kuwai.ysy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class TakeView extends RelativeLayout{

    private TextView mTimeTxt,tv_dao;
    int repeatCount=4;//定义重复字数（执行动画1次 + 重复动画4次 = 公共5次）
    CountDownProgressBar countDownProgressBar;
    private TakeCallback mCallback = null;

    public TakeView(Context context) {
        this(context, null);
    }

    public TakeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //Inflate布局
        LayoutInflater.from(getContext()).inflate(R.layout.layout_pre, this, true);
        //设置view的监听事件
        //setViewListener();
        //更新view的显示
        //updateAllViews();
        mTimeTxt = findViewById(R.id.tv_time);
        tv_dao = findViewById(R.id.tv_dao);
        countDownProgressBar = findViewById(R.id.cpb_countdown);

        // 设置透明度渐变动画
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(960);//动画持续时间(定义900~1000,也就是1秒左右)
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(repeatCount);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        // 设置缩放渐变动画
        final ScaleAnimation scaleAnimation =new ScaleAnimation(0.5f, 1f, 0.5f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(960);//动画持续时间(定义900~1000,也就是1秒左右)
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(repeatCount);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);


        mTimeTxt.startAnimation(animationSet);
        //这里 alphAnimation 设置监听，不能用 animationSet 做监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            int count=repeatCount+1;// 加1为第一次要显示的数字 5
            @Override
            public void onAnimationStart(Animation animation) {// 此方法执行1次
                mTimeTxt.setVisibility(View.VISIBLE);
                mTimeTxt.setText(""+count);//设置显示的数字
                tv_dao.setText(count+"S倒计时后开始");
                count--;
            }

            @Override
            public void onAnimationEnd(Animation animation) {// 此方法执行1次
                // 动画结束 隐藏控件
                mTimeTxt.setVisibility(View.GONE);
                tv_dao.setVisibility(GONE);
                countDownProgressBar.setVisibility(VISIBLE);
                countDownProgressBar.setDuration(5000, new CountDownProgressBar.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        //ToastUtils.showShort("完成了");
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {// 此方法执行4次（repeatCount值）
                mTimeTxt.setText(""+count);
                tv_dao.setText(count+"S倒计时后开始");
                count--;
            }
        });

        findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback!=null){
                    mCallback.cancelCallback();
                }
            }
        });
    }

    public void setCallback(TakeCallback callback){
        mCallback = callback;
    }

    public interface TakeCallback{
        void cancelCallback();
    }
}
