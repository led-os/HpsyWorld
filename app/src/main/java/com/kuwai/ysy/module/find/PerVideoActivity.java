package com.kuwai.ysy.module.find;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.widget.home.OnRemoveListener;
import com.kuwai.ysy.widget.home.RandomFrameLayout;
import com.kuwai.ysy.widget.home.RandomView;

public class PerVideoActivity extends AppCompatActivity {
    private RandomFrameLayout randomFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video);
        randomFrameLayout = (RandomFrameLayout) findViewById(R.id.fl_random);
        randomFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                randomFrameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                updateViewValue();
            }
        });
        randomFrameLayout.setOnRemoveListener(new OnRemoveListener() {
            @Override
            public void remove(RandomView randomView) {
                if (randomFrameLayout.getChildCount() == 2) {
                    //tvWait.startAnimation(animation());
                    //tvWait.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void updateViewValue() {
        for (int i = 0; i < 10; i++) {
            randomFrameLayout.updateView("0.0000" + i);
        }
    }


    private TranslateAnimation animation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -15, 15);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }
}
