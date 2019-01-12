package com.kuwai.ysy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kuwai.ysy.R;

public class CircleTextView extends AppCompatTextView {

    private Paint mBgPaint = new Paint();
    private Paint mCirclePaint = new Paint();

    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint(context);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public CircleTextView(Context context) {
        super(context);
        initPaint(context);
    }

    private void initPaint(Context context) {
        mBgPaint.setColor(context.getResources().getColor(R.color.bg_circle));
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        mCirclePaint.setColor(context.getResources().getColor(R.color.white));
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max, max);
    }

    @Override
    public void setBackgroundColor(int color) {
        mBgPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, Math.max(getWidth(), getHeight()) / 2, mCirclePaint);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, Math.max(getWidth(), getHeight()) / 2 - 2, mBgPaint);
        super.onDraw(canvas);
    }

}
