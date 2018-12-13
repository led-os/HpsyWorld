package com.kuwai.ysy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class TextImageView extends AppCompatImageView {
    private String testString = "";
    private float defaultTextSize = 30.0F;
    private int textColor = -1;
    private int startX = 0;
    private int startY = 0;
    private int onLineColor = -11351483;
    private int offLineColor = -3289651;
    private Paint textPaint;
    private Paint circlePaint;
    private Paint.FontMetrics m;
    private int n = 2;

    public TextImageView(Context paramContext) {
        super(paramContext);
        init();
    }

    public TextImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public TextImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        this.textPaint = new Paint(33);
        this.textPaint.setColor(this.textColor);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(this.defaultTextSize);
        this.circlePaint = new Paint(1);
        this.circlePaint.setStrokeWidth(4.0F);
    }

    private void initAttr(Context paramContext, AttributeSet paramAttributeSet) {
        if (paramAttributeSet == null) {
            return;
        }
        //paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, b.n.TextImageView);
        //this.f = paramContext.getColor(b.n.TextImageView_textColor, -1);
        //this.e = paramContext.getDimensionPixelSize(b.n.TextImageView_textSize, 30);
        //paramContext.recycle();
    }

    @Override
    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        if ((this.testString != null) && (!this.testString.equals(""))) {
            paramCanvas.drawText(this.testString, this.startX, this.startY + Math.abs(this.m.top + this.m.bottom) / 2.0F, this.textPaint);
        }
        if ((this.n == 0) || (this.n == 1)) {
            float f1 = getWidth() * 0.3157895F / 3.0F;
            this.circlePaint.setColor(-1);
            this.circlePaint.setStyle(Paint.Style.STROKE);
            float f2 = getWidth() - f1;
            float f3 =  f1;
            paramCanvas.drawCircle(f2, f3, f1, this.circlePaint);
            if (this.n == 0) {
                this.circlePaint.setColor(this.onLineColor);
            } else if (this.n == 1) {
                this.circlePaint.setColor(this.offLineColor);
            }
            this.circlePaint.setStyle(Paint.Style.FILL);
            paramCanvas.drawCircle(f2, f3, f1 - 2.0F, this.circlePaint);
        }
    }

    @Override
    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        paramInt3 = getPaddingLeft();
        paramInt4 = getPaddingTop();
        paramInt1 = Math.min(paramInt1 - paramInt3 - getPaddingRight(), paramInt2 - paramInt4 - getPaddingBottom()) / 2;
        this.startX = (paramInt3 + paramInt1);
        this.startY = (paramInt4 + paramInt1);
        this.m = this.textPaint.getFontMetrics();
    }

    public void setOnlineState(int paramInt) {
        this.n = paramInt;
        invalidate();
    }

    public void setTextString(String paramString) {
        this.testString = paramString;
        invalidate();
    }
}
