package com.kuwai.ysy.module.findtwo.business;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.callback.WindowCallback;
import com.kuwai.ysy.widget.KickBackAnimator;
import com.ms_square.etsyblur.BlurringView;

public class MoreWindow extends PopupWindow implements OnClickListener {

    private Activity mContext;
    private RelativeLayout layout;
    private ImageView close;
    private View bgView;
    private BlurringView blurringView;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Handler mHandler = new Handler();
    private WindowCallback windowCallback;

    private TextView mTvFood;
    private TextView mTvMovie;
    private TextView mTvTravel;
    private TextView mTvSport;
    private TextView mTvSing;
    private TextView mTvPlay;
    private TextView mTvGame;
    private TextView mTvOther;

    private int index = 0;

    public MoreWindow(Activity context) {
        mContext = context;
    }

    public void setWindowCallback(WindowCallback callback){
        this.windowCallback = callback;
    }

    /**
     * 初始化
     *
     * @param view 要显示的模糊背景View,一般选择跟布局layout
     */
    public void init(View view) {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);

        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.more_window, null);

        setContentView(layout);

        close = (ImageView) layout.findViewById(R.id.iv_close);
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    closeAnimation();
                }
            }

        });

        blurringView = (BlurringView) layout.findViewById(R.id.blurring_view);
        blurringView.blurredView(view);//模糊背景

        bgView = layout.findViewById(R.id.rel);
        mTvFood = layout.findViewById(R.id.tv_food);
        mTvMovie = layout.findViewById(R.id.tv_movie);
        mTvTravel = layout.findViewById(R.id.tv_travel);
        mTvSport = layout.findViewById(R.id.tv_sport);
        mTvSing = layout.findViewById(R.id.tv_sing);
        mTvPlay = layout.findViewById(R.id.tv_play);
        mTvGame = layout.findViewById(R.id.tv_game);
        mTvOther = layout.findViewById(R.id.tv_other);
        mTvFood.setOnClickListener(this);
        mTvGame.setOnClickListener(this);
        mTvMovie.setOnClickListener(this);
        mTvOther.setOnClickListener(this);
        mTvPlay.setOnClickListener(this);
        mTvSing.setOnClickListener(this);
        mTvSport.setOnClickListener(this);
        mTvTravel.setOnClickListener(this);
        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(false);//使popupwindow全屏显示
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 显示window动画
     *
     * @param anchor
     */
    public void showMoreWindow(View anchor) {

        showAtLocation(anchor, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        int x = mWidth / 2;
                        int y = (int) (mHeight - fromDpToPx(25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                                y, 0, bgView.getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
//                                bgView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //							layout.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        showAnimation(layout);

    }

    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //＋ 旋转动画
                    close.animate().rotation(90).setDuration(400);
                }
            });
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                index = i;
                //child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                        fadeAnim.setDuration(400);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(300);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }
                }, i * 100 + 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                close.animate().rotation(-90).setDuration(400);
            }
        });

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int x = mWidth / 2;
                int y = (int) (mHeight - fromDpToPx(25));
                Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                        y, bgView.getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //							layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        bgView.setVisibility(View.GONE);
                        dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (isShowing()) {
            closeAnimation();
        }

        switch (v.getId()) {
            case R.id.tv_food:
                windowCallback.windowClick(0, C.DATE_FOOD);
                break;
            case R.id.tv_movie:
                windowCallback.windowClick(1,C.DATE_MOVIE);
                break;
            case R.id.tv_travel:
                windowCallback.windowClick(2,C.DATE_TRAVEL);
                break;
            case R.id.tv_sport:
                windowCallback.windowClick(3,C.DATE_SPORT);
                break;
            case R.id.tv_sing:
                windowCallback.windowClick(4,C.DATE_SING);
                break;
            case R.id.tv_play:
                windowCallback.windowClick(5,C.DATE_PLAY);
                break;
            case R.id.tv_game:
                windowCallback.windowClick(6,C.DATE_GAME);
                break;
            case R.id.tv_other:
                windowCallback.windowClick(7,C.DATE_OTHER);
                break;
        }

    }

    float fromDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
