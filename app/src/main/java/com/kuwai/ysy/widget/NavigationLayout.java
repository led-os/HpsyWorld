package com.kuwai.ysy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;


public class NavigationLayout extends RelativeLayout implements
        View.OnClickListener {
    private TextView mTitle;
    private String mTitleStr;
    private ImageView mRightImg;
    private Drawable mLeftImgDb;
    private Drawable mRightImgDb;
    private TextView mRightText;
    private String mRightStr;
    private View mRootView;
    private int rightColor;
    private ImageView mLeftView;
    private TextView mLeftText;
    private String mLeftStr;

    public NavigationLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public NavigationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavigationLayout(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(
                R.layout.navigation_layout, this, true);
        if (attrs != null) {
            int lineColorClick_default = context.getResources().getColor(R.color.black); // 默认 = 蓝色#1296db
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.NavigationLayout);
            if (a != null) {
                mTitleStr = a.getString(R.styleable.NavigationLayout_title1);
                rightColor = a.getColor(R.styleable.NavigationLayout_rightColor, lineColorClick_default);
                mLeftImgDb = a.getDrawable(R.styleable.NavigationLayout_leftImg);
                mRightImgDb = a.getDrawable(R.styleable.NavigationLayout_rightImg);
                mRightStr = a.getString(R.styleable.NavigationLayout_rightTxt);
                mLeftStr = a.getString(R.styleable.NavigationLayout_leftTxt);
                a.recycle();
            }

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }
        mLeftView = (ImageView) mRootView.findViewById(R.id.left);
        mLeftView.setOnClickListener(this);
        if (mLeftImgDb != null) {
            mLeftView.setImageDrawable(mLeftImgDb);
        }

        View rightBtn = mRootView.findViewById(R.id.right_btn);
        rightBtn.setOnClickListener(this);

        View leftBtn = mRootView.findViewById(R.id.left_btn);
        leftBtn.setOnClickListener(this);

        mTitle = (TextView) mRootView.findViewById(R.id.title);
        mTitle.setText(mTitleStr);
        mRightText = (TextView) mRootView.findViewById(R.id.right_txt);
        mRightText.setText(mRightStr);
        mRightText.setTextColor(rightColor);
        mRightImg = (ImageView) mRootView.findViewById(R.id.right);
        mRightImg.setImageDrawable(mRightImgDb);

        mLeftText = (TextView) mRootView.findViewById(R.id.left_txt);
        mLeftText.setText(mLeftStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                if (mLeftClick != null) {
                    mLeftClick.onClick(v);
                }
                break;
            case R.id.right_btn:
                if (mRightClick != null) {
                    mRightClick.onClick(v);
                }
                break;
            case R.id.left_btn:
                if (mLeftClick != null) {
                    mLeftClick.onClick(v);
                }
                break;
        }

    }

    public void setLeftImg(int id) {
        if (mLeftView != null) {
            mLeftView.setImageResource(id);
        }
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public void setRightColor(int color) {
        this.rightColor = color;
        mRightText.setTextColor(rightColor);
    }

    private OnClickListener mLeftClick;
    private OnClickListener mRightClick;

    public void setLeftClick(OnClickListener leftClick) {
        mLeftClick = leftClick;
    }

    public void setRightClick(OnClickListener rightClick) {
        mRightClick = rightClick;
    }

    public void hideLeft(boolean flag) {
        if (flag) {
            mLeftView.setVisibility(View.GONE);
        } else {
            mLeftView.setVisibility(View.VISIBLE);
        }
    }

    public void hideRight(boolean flag) {
        if (flag) {
            mRightText.setVisibility(View.GONE);
        } else {
            mRightText.setVisibility(View.VISIBLE);
        }
    }

    public void showRightText(boolean flag) {
        if (flag) {
            mRightText.setVisibility(View.VISIBLE);
        } else {
            mRightText.setVisibility(View.GONE);
        }
    }

    public void showLeftText(boolean flag) {
        if (flag) {
            mLeftText.setVisibility(View.VISIBLE);
            mLeftView.setVisibility(View.GONE);
        } else {
            mLeftText.setVisibility(View.GONE);
            mLeftView.setVisibility(View.VISIBLE);

        }
    }

    public void hideRightImgBackground() {
        mRightImg.setBackgroundResource(0);
    }

    public void setLeftText(String leftStr) {
        mLeftText.setText(leftStr);

    }

    public void setRightText(String rightStr) {
        mRightText.setText(rightStr);

    }

    public void setRightBackground(int rightStr) {
        mRightImg.setBackgroundResource(rightStr);

    }

    public void setrRightImg(int resId) {
        if (mRightImg != null) {
            mRightImg.setImageResource(resId);
        }
    }

    private NavigationClickListener clickListener;

    public void setNavigationClickLister(NavigationClickListener listener) {
        clickListener = listener;
    }

    public interface NavigationClickListener {
        public void leftClick();

        public void rightClick();
    }

}
