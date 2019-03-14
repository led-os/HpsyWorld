package com.kuwai.ysy.widget.home;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.VideoCallBack;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.widget.IdentityImageView;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomFrameLayout extends FrameLayout {
    private List<TagViewBean> tagViews;
    private TagViewBean tagBean;
    private MyHandler mHandler = new MyHandler();
    private int termX, termY;
    private List<RandomView> textList;
    private OnRemoveListener onRemoveListener;
    private VideoCallBack videoCallBack;

    private boolean isBig = true;

    public RandomFrameLayout(Context context) {
        super(context);
    }

    public RandomFrameLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
        textList = new ArrayList<>();
        tagViews = Collections.synchronizedList(new ArrayList<TagViewBean>());
        termX = 80;
        termY = 80;
    }

    public RandomFrameLayout(Context context, AttributeSet attributeSet, int styles) {
        super(context, attributeSet, styles);
    }

    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    public void setVideoClick(VideoCallBack callBack) {
        this.videoCallBack = callBack;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void updateView(VideoChatBean.DataBean.ArrBean value) {
        initXY(value);
    }

    public void removeeView() {
        removeAllViews();
        tagViews = Collections.synchronizedList(new ArrayList<TagViewBean>());
    }

    private void initXY(final VideoChatBean.DataBean.ArrBean value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (tagViews) {
                    int x = new Random().nextInt(getWidth() - 360);
                    int y = new Random().nextInt(getHeight() - 360);
                    while (!isContains(x, y)) {
                        x = new Random().nextInt(getWidth() - 360);
                        y = new Random().nextInt(getHeight() - 360);
                    }
                    tagBean = new TagViewBean();
                    tagBean.setX(x);
                    tagBean.setY(y);
                    tagViews.add(tagBean);
                    Message message = Message.obtain();
                    message.obj = value;
                    message.what = 0;
                    message.arg1 = x;
                    message.arg2 = y;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private boolean isContains(int x, int y) {
        if (x < termX || y < termY) {
            return false;
        }
        for (int i = 0; i < tagViews.size(); i++) {
            try {
                if (tagViews.get(i).getX() < x + 240 && tagViews.get(i).getX() > x - 240) {
                    if (tagViews.get(i).getY() < y + 240 && tagViews.get(i).getY() > y - 240) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final VideoChatBean.DataBean.ArrBean chatBean = (VideoChatBean.DataBean.ArrBean) msg.obj;
            IdentityImageView imageView = new IdentityImageView(getContext());
            LayoutParams params = null;
            if(isBig){
                 params = new LayoutParams(200, 200);
                 isBig = false;
            }else{
                params = new LayoutParams(300, 300);
                isBig = true;
            }
            GlideUtil.load(getContext(),chatBean.getAvatar(),imageView.getBigCircleImageView());
           // imageView.getBigCircleImageView().setImageResource(R.drawable.icon);
            if(chatBean.getGender() == 1){
                imageView.getSmallCircleImageView().setImageResource(R.drawable.center_charm_ic_man);
            }else{
                imageView.getSmallCircleImageView().setImageResource(R.drawable.center_charm_ic_woman);
            }

            imageView.setBorderColor(R.color.text_blue);
            imageView.setBorderWidth(4);
            params.leftMargin = msg.arg1;
            params.topMargin = msg.arg2;
            imageView.start();
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(videoCallBack!=null){
                        videoCallBack.videoClick(chatBean);
                    }
                }
            });
            addView(imageView,params);
           /* RandomView textView = new RandomView(getContext());
            textView.setText(msg.obj.toString());
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = msg.arg1;
            params.topMargin = msg.arg2;
            addView(textView, params);
            textList.add(textView);
            textView.setOnRemoveListener(new OnRemoveListener() {
                @Override
                public void remove(RandomView randomView) {
                    if (onRemoveListener != null)
                        onRemoveListener.remove(randomView);
                }
            });*/
        }
    }


}
