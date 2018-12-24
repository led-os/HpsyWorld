package com.kuwai.ysy.widget.shadow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;


import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.glide.CalcUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 自定义签到View.
 *
 * @author Sorgs.
 * @date 2018/8/17.
 */
public class StepsView extends View {

    /**
     * 动画执行的时间 230毫秒
     */
    private final static int ANIMATION_TIME = 230;
    /**
     * 动画执行的间隔次数
     */
    private final static int ANIMATION_INTERVAL = 10;

    /**
     * 线段的高度
     */
    private float mCompletedLineHeight = CalcUtils.dp2px(getContext(), 2f);

    /**
     * 图标宽度
     */
    private float mIconWidth = CalcUtils.dp2px(getContext(), 24f);
    /**
     * 图标的高度
     */
    private float mIconHeight = CalcUtils.dp2px(getContext(), 24f);
    /**
     * UP宽度
     */
    private float mUpWidth = CalcUtils.dp2px(getContext(), 20.5f);
    /**
     * up的高度
     */
    private float mUpHeight = CalcUtils.dp2px(getContext(), 12f);

    /**
     * 线段长度
     */
    private float mLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());

    /**
     * 已经完成的图标
     */
    private Drawable mCompleteIcon;
    /**
     * 正在进行的图标
     */
    private Drawable mAttentionIcon;
    /**
     * 默认的图标
     */
    private Drawable mDefaultIcon;
    /**
     * UP图标
     */
    private Drawable mUpIcon;
    /**
     * 图标中心点Y
     */
    private float mCenterY;
    /**
     * 线段的左上方的Y
     */
    private float mLeftY;
    /**
     * 线段的右下方的Y
     */
    private float mRightY;

    /**
     * 数据源
     */
    private List<StepBean> mStepBeanList;
    private int mStepNum = 0;

    /**
     * 图标中心点位置
     */
    private List<Float> mCircleCenterPointPositionList;
    /**
     * 未完成的线段Paint
     */
    private Paint mUnCompletedPaint;
    /**
     * 完成的线段paint
     */
    private Paint mCompletedPaint;
    /**
     * 未完成颜色
     */
    private int mUnCompletedLineColor = ContextCompat.getColor(getContext(), R.color.gray_7b);
    /**
     * 天数颜色
     */
    private int mUnCompletedTextColor = ContextCompat.getColor(getContext(), R.color.white);
    /**
     * up魅力值颜色
     */
    private int mCurrentTextColor = ContextCompat.getColor(getContext(), R.color.white);
    /**
     * 完成的颜色
     */
    private int mCompletedLineColor = ContextCompat.getColor(getContext(), R.color.theme);

    private Paint mTextNumberPaint;

    /**
     * 是否执行动画
     */
    private boolean isAnimation = false;

    /**
     * 记录重绘次数
     */
    private int mCount = 0;

    /**
     * 执行动画线段每次绘制的长度，线段的总长度除以总共执行的时间乘以每次执行的间隔时间
     */
    private float mAnimationWidth = (mLineWidth / ANIMATION_TIME) * ANIMATION_INTERVAL;

    /**
     * 执行动画的位置
     */
    private int mPosition;
    private int[] mMax;

    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mStepBeanList = new ArrayList<>();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mLineWidth = displayMetrics.widthPixels / 13;
        mCircleCenterPointPositionList = new ArrayList<>();

        //未完成文字画笔
        mUnCompletedPaint = new Paint();
        mUnCompletedPaint.setAntiAlias(true);
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mUnCompletedPaint.setStrokeWidth(2);
        mUnCompletedPaint.setStyle(Paint.Style.FILL);

        //已完成画笔文字
        mCompletedPaint = new Paint();
        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(mCompletedLineColor);
        mCompletedPaint.setStrokeWidth(2);
        mCompletedPaint.setStyle(Paint.Style.FILL);

        //number paint
        mTextNumberPaint = new Paint();
        mTextNumberPaint.setAntiAlias(true);
        mTextNumberPaint.setColor(mUnCompletedTextColor);
        mTextNumberPaint.setStyle(Paint.Style.FILL);
        mTextNumberPaint.setTextSize(CalcUtils.sp2px(getContext(), 12f));

        //已经完成的icon
        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.center_ic_ok);
        //正在进行的icon
        mAttentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.center_mark_ic_gift);
        //未完成的icon
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.center_ic_no);
        //UP的icon
        //mUpIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_sign_up);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图标的中中心Y点
        mCenterY = CalcUtils.dp2px(getContext(), 28f) + mIconHeight;
        //获取左上方Y的位置，获取该点的意义是为了方便画矩形左上的Y位置
        mLeftY = mCenterY - (mCompletedLineHeight / 2);
        //获取右下方Y的位置，获取该点的意义是为了方便画矩形右下的Y位置
        mRightY = mCenterY + mCompletedLineHeight / 2;

        //计算图标中心点
        mCircleCenterPointPositionList.clear();
        //第一个点距离父控件左边14.5dp
        float size = mIconWidth / 2 + CalcUtils.dp2px(getContext(), 14.5f);
        mCircleCenterPointPositionList.add(size);
        for (int i = 1; i < mStepNum; i++) {
            //从第二个点开始，每个点距离上一个点为图标的宽度加上线段的23dp的长度
            size = size + mIconWidth + mLineWidth;
            mCircleCenterPointPositionList.add(size);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimation) {
            drawSign(canvas);
        } else {
            drawUnSign(canvas);
        }
    }

    /**
     * 绘制签到(伴随签到动画)
     */
    @SuppressLint("DrawAllocation")
    private void drawSign(Canvas canvas) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            //绘制线段
            float preComplectedXPosition = mCircleCenterPointPositionList.get(i) + mIconWidth / 2;
            if (i != mCircleCenterPointPositionList.size() - 1) {
                //最后一条不需要绘制
                if (i + 1 < mPosition) {
                    //下一个是已完成，当前才需要绘制绿色
                    canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                            mRightY, mCompletedPaint);
                } else {
                    //其余绘制灰色

                    //当前位置执行动画
                    if (i == mPosition - 1) {
                        //绿色开始绘制的地方,
                        float endX = preComplectedXPosition + mAnimationWidth * (mCount / ANIMATION_INTERVAL);
                        //绘制绿色
                        canvas.drawRect(preComplectedXPosition, mLeftY, endX+33, mRightY, mCompletedPaint);
                        //绘制灰色
                       /* canvas.drawRect(endX, mLeftY, preComplectedXPosition + mLineWidth,
                                mRightY, mUnCompletedPaint);*/
                    } else {
                        canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                                mRightY, mUnCompletedPaint);
                    }
                }
            }

            //绘制图标
            float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);
            Rect rect = new Rect((int) (currentComplectedXPosition - mIconWidth / 2),
                    (int) (mCenterY - mIconHeight / 2),
                    (int) (currentComplectedXPosition + mIconWidth / 2),
                    (int) (mCenterY + mIconHeight / 2));

            if (i == mCircleCenterPointPositionList.size() - 1) {
                rect = new Rect((int) (currentComplectedXPosition - mIconWidth / 2 - CalcUtils.dp2px(getContext(), 6f)),
                        (int) (mCenterY - mIconHeight / 2 - CalcUtils.dp2px(getContext(), 4f)),
                        (int) (currentComplectedXPosition + mIconWidth / 2 + CalcUtils.dp2px(getContext(), 4f)),
                        (int) (mCenterY + mIconHeight / 2));
            }

            StepBean stepsBean = mStepBeanList.get(i);

            if (i == mPosition && mCount == ANIMATION_TIME) {
                //当前需要绘制成绿色了
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
            } else {
                if (stepsBean.getState() == StepBean.STEP_UNDO) {
                    mDefaultIcon.setBounds(rect);
                    mDefaultIcon.draw(canvas);
                } else if (stepsBean.getState() == StepBean.STEP_CURRENT) {
                    mAttentionIcon.setBounds(rect);
                    mAttentionIcon.draw(canvas);
                } else if (stepsBean.getState() == StepBean.STEP_COMPLETED) {
                    mCompleteIcon.setBounds(rect);
                    mCompleteIcon.draw(canvas);
                }
            }

            float xDis = mTextNumberPaint.measureText(stepsBean.getNumber());

            canvas.drawText(stepsBean.getNumber(),
                    currentComplectedXPosition - xDis / 2,
                    mCenterY - mIconHeight / 2 - CalcUtils.dp2px(getContext(), 8f),
                    mTextNumberPaint);
        }

        //记录重绘次数
        mCount = mCount + ANIMATION_INTERVAL;
        if (mCount <= ANIMATION_TIME) {
            //引起重绘
            postInvalidate();
        } else {
            //重绘完成
            isAnimation = false;
            mCount = 0;
        }
    }

    /**
     * 绘制初始状态的view
     */
    @SuppressLint("DrawAllocation")
    private void drawUnSign(Canvas canvas) {
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            //绘制线段
            float preComplectedXPosition = mCircleCenterPointPositionList.get(i) + mIconWidth / 2;
            if (i != mCircleCenterPointPositionList.size() - 1) {
                //最后一条不需要绘制
               /* if (mStepBeanList.get(i + 1).getState() == StepBean.STEP_COMPLETED) {
                    //下一个是已完成，当前才需要绘制绿色
                    canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                            mRightY, mCompletedPaint);
                } else {
                    //其余绘制灰色
                    canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                            mRightY, mUnCompletedPaint);
                }*/

                if (i + 1 < mPosition) {
                    //下一个是已完成，当前才需要绘制绿色
                    canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                            mRightY, mCompletedPaint);
                } else {
                    //其余绘制灰色
                    canvas.drawRect(preComplectedXPosition, mLeftY, preComplectedXPosition + mLineWidth,
                            mRightY, mUnCompletedPaint);
                }
            }

            //绘制图标
            float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);
            Rect rect = new Rect((int) (currentComplectedXPosition - mIconWidth / 2),
                    (int) (mCenterY - mIconHeight / 2),
                    (int) (currentComplectedXPosition + mIconWidth / 2),
                    (int) (mCenterY + mIconHeight / 2));

            if (i == mCircleCenterPointPositionList.size() - 1) {
                rect = new Rect((int) (currentComplectedXPosition - mIconWidth / 2 - CalcUtils.dp2px(getContext(), 6f)),
                        (int) (mCenterY - mIconHeight / 2 - CalcUtils.dp2px(getContext(), 4f)),
                        (int) (currentComplectedXPosition + mIconWidth / 2 + CalcUtils.dp2px(getContext(), 4f)),
                        (int) (mCenterY + mIconHeight / 2));
            }

            StepBean stepsBean = mStepBeanList.get(i);

            if (stepsBean.getState() == StepBean.STEP_UNDO) {
                mDefaultIcon.setBounds(rect);
                mDefaultIcon.draw(canvas);
            } else if (stepsBean.getState() == StepBean.STEP_CURRENT) {
                mAttentionIcon.setBounds(rect);
                mAttentionIcon.draw(canvas);
            } else if (stepsBean.getState() == StepBean.STEP_COMPLETED) {
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
            }

            float xDis = mTextNumberPaint.measureText(stepsBean.getNumber());

            canvas.drawText(stepsBean.getNumber(),
                    currentComplectedXPosition - xDis / 2,
                    mCenterY - mIconHeight / 2 - CalcUtils.dp2px(getContext(), 8f),
                    mTextNumberPaint);
        }
    }

    /**
     * 设置流程步数
     *
     * @param stepsBeanList 流程步数
     */
    public void setStepNum(List<StepBean> stepsBeanList, int position) {
        if (stepsBeanList == null) {
            return;
        }
        mPosition = position;
        mStepBeanList = stepsBeanList;
        mStepNum = mStepBeanList.size();
        //找出最大的两个值的位置
        mMax = CalcUtils.findMax(stepsBeanList);
        //引起重绘
        postInvalidate();
    }

    /**
     * 执行签到动画
     *
     * @param position 执行的位置
     */
    public void startSignAnimation(int position) {
        //线条从灰色变为绿色
        isAnimation = true;
        mPosition = position;
        if (position < mStepBeanList.size()) {
            mStepBeanList.get(position).setState(StepBean.STEP_COMPLETED);
            //引起重绘
            postInvalidate();
        }

    }

}
