package com.kuwai.ysy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allen.library.CircleImageView;
import com.bumptech.glide.Glide;
import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PileLayout extends ViewGroup {

    private Context context;

    private List<String> allUrls = new ArrayList<>();

    private boolean flag = false;//false表示右边增加，true表示左边增加

    private int spWidth = 20;
    private int picWidth = 26;
    private int picCount = 0;

    //在new的时候会调用此方法
    public PileLayout(Context context) {
        super(context);
        this.context = context;
    }

    public PileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PileLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;

        //每一行的，宽，高
        int lineWidth = 0;
        int lineHeight = 0;
        //获取所有的子view
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //换行判断
                height += lineHeight;
                //新的行高
                lineHeight = childHeight;
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;

            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }


    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingRight() - getPaddingLeft()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin - spWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();

        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();


        int top = parentTop;
        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //反序显示
            if (flag) {
                Collections.reverse(lineViews);
            }
//            Collections.reverse(lineViews);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                //int tc = top + lp.topMargin;
                int tc = parentTop + (parentBottom - parentTop - child.getMeasuredHeight()) / 2 + lp.topMargin - lp.bottomMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin - spWidth;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setSpWidth(int spWidth) {
        this.spWidth = spWidth;
    }

    public void setPicWidth(int spWidth) {
        this.picWidth = Utils.dp2px(24);
    }

    public void setPicCount(int count) {
        this.picCount = count;
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setUrls(List<String> listVals) {
        allUrls.clear();
        allUrls.addAll(listVals);
        //开始绘制view
        setViews();
    }

    public void setOneUrls(String urlVal) {
        allUrls.add(urlVal);
        setViews();
    }

    public void cancels(String urlVal) {
        allUrls.remove(urlVal);
        setViews();
    }


    private void setViews() {
        //清空，重新绘制
        removeAllViews();
        for (int i = 0; i < allUrls.size(); i++) {
            //清空重新绘制

            CircleImageView imageView = (CircleImageView) LayoutInflater.from(context).inflate(R.layout.item_round_head, this, false);
            imageView.setBorderColor(getResources().getColor(R.color.white));
            imageView.setBorderWidth(2);
            if (picWidth != 26) {
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = picWidth;
                layoutParams.height = picWidth;
                imageView.setLayoutParams(layoutParams);
            }
            GlideUtil.load(context, allUrls.get(i), imageView);
            this.addView(imageView);
            if (i >= 3) {
                break;
            }
        }
        if (picCount > 3) {
            CircleTextView circleTextView = (CircleTextView) LayoutInflater.from(context).inflate(R.layout.item_round_text, this, false);
            circleTextView.setTextSize(12);
            if (picWidth != 26) {
                ViewGroup.LayoutParams layoutParams = circleTextView.getLayoutParams();
                layoutParams.width = picWidth;
                layoutParams.height = picWidth;
                circleTextView.setLayoutParams(layoutParams);
            }
            circleTextView.setText("+" + picCount);
            this.addView(circleTextView);
        }
        postInvalidate();
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
