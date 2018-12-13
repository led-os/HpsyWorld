package com.kuwai.ysy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.print.PrinterId;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;


public class QuestionLayout extends RelativeLayout implements
        View.OnClickListener {
    private TextView tv_title;
    private EditText et_question_answer;
    private ImageView img_select;
    private SuperButton btn_send;
    private TextView tv_num;
    private View mRootView;
    private ImageView img_arrow;
    private String mTitle;
    private boolean isShowContent = true;

    public QuestionLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public QuestionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public QuestionLayout(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(
                R.layout.item_question, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }
        btn_send = mRootView.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        img_arrow = mRootView.findViewById(R.id.img_arrow);
        img_arrow.setOnClickListener(this);
        et_question_answer = mRootView.findViewById(R.id.et_question_answer);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        tv_num = (TextView) mRootView.findViewById(R.id.tv_num);
        img_select = (ImageView) mRootView.findViewById(R.id.img_select);
        img_select.setImageResource(R.drawable.ic_list_unselect);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if (mSendClick != null) {
                    mSendClick.onClick(v);
                }
                break;
            case R.id.img_arrow:
                if (mArrowClick != null) {
                    mArrowClick.onClick(v);
                }
                break;
        }

    }

    public void setTitle(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    private OnClickListener mSendClick;
    private OnClickListener mArrowClick;

    public void setSendClick(OnClickListener sendClick) {
        mSendClick = sendClick;
    }

    public void setArrowClick(OnClickListener arrowClick) {
        mArrowClick = arrowClick;
    }

    public void hasAnswerd(boolean flag) {
        if (flag) {
            img_select.setImageResource(R.drawable.ic_list_unselect);
            tv_title.setTextColor(getResources().getColor(R.color.balck_28));
            img_arrow.setVisibility(GONE);
            hideContent();
        } else {
            img_select.setImageResource(R.drawable.ic_list_select);
            tv_title.setTextColor(getResources().getColor(R.color.theme));
            img_arrow.setVisibility(VISIBLE);
            showContent();
        }
    }

    public boolean getIshShow() {
        return isShowContent;
    }

    public void hideContent() {
        img_arrow.setImageResource(R.drawable.ic_list_arrow_down);
        et_question_answer.setVisibility(GONE);
        tv_num.setVisibility(GONE);
        btn_send.setVisibility(GONE);
        isShowContent = false;
    }

    public void showContent() {
        img_arrow.setImageResource(R.drawable.ic_list_arrow_up);
        et_question_answer.setVisibility(VISIBLE);
        tv_num.setVisibility(VISIBLE);
        btn_send.setVisibility(VISIBLE);
        isShowContent = true;
    }

}
