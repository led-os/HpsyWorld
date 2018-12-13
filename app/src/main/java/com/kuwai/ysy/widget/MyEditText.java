package com.kuwai.ysy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;


import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.Utils;

import java.lang.reflect.Field;

public class MyEditText extends AppCompatEditText {

    /*
     * 定义属性变量
     * */
    private Paint mPaint; // 画笔
    private int ic_deleteResID; // 删除图标 资源ID
    private Drawable ic_delete; // 删除图标
    private int delete_x, delete_y, delete_width, delete_height; // 删除图标起点(x,y)、删除图标宽、高（px）
    private int cursor; // 光标

    // 分割线变量
    private int lineColor_click, lineColor_unclick;// 点击时 & 未点击颜色
    private int color;
    private int linePosition;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        // 获取控件资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);

        /**
         * 初始化删除图标
         */
        ic_deleteResID = typedArray.getResourceId(R.styleable.MyEditText_ic_delete, R.drawable.edt_delete);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        ic_delete = getResources().getDrawable(ic_deleteResID);
        // 3. 设置图标大小
        // 起点(x，y)、宽= left_width、高 = left_height
        delete_x = typedArray.getInteger(R.styleable.MyEditText_delete_x, 0);
        delete_y = typedArray.getInteger(R.styleable.MyEditText_delete_y, 0);
        delete_width = typedArray.getInteger(R.styleable.MyEditText_delete_width, 60);
        delete_height = typedArray.getInteger(R.styleable.MyEditText_delete_height, 60);
        ic_delete.setBounds(delete_x, delete_y, delete_width, delete_height);

        /**
         * 初始化光标（颜色 & 粗细）
         */
        cursor = typedArray.getResourceId(R.styleable.MyEditText_cursor, R.drawable.cursor);
        try {
            // 2. 通过反射 获取光标属性
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            // 3. 传入资源ID
            f.set(this, cursor);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 初始化分割线（颜色、粗细、位置）
         */
        // 1. 设置画笔
        mPaint = new Paint();
        mPaint.setStrokeWidth(2.0f); // 分割线粗细
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        color = context.getResources().getColor(R.color.balck_28);

        // 2. 设置分割线颜色（使用十六进制代码，如#333、#8e8e8e）
        int lineColorClick_default = context.getResources().getColor(R.color.theme); // 默认 = 蓝色#1296db
        int lineColorunClick_default = context.getResources().getColor(R.color.lineColor_unclick); // 默认 = 灰色#9b9b9b
        lineColor_click = typedArray.getColor(R.styleable.MyEditText_lineColor_click, lineColorClick_default);
        lineColor_unclick = typedArray.getColor(R.styleable.MyEditText_lineColor_unclick, lineColorunClick_default);

        mPaint.setColor(lineColor_unclick); // 分割线默认颜色 = 灰色
        setTextColor(color); // 字体默认颜色 = 灰色

        // 3. 分割线位置
        linePosition = typedArray.getInteger(R.styleable.MyEditText_linePosition, 1);
        // 消除自带下划线
        setBackground(null);
        invalidate();
    }

    /**
     * 复写EditText本身的方法：onTextChanged（）
     * 调用时刻：当输入框内容变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0, hasFocus());
    }

    /**
     * 复写EditText本身的方法：onFocusChanged（）
     * 调用时刻：焦点发生变化时
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(focused && length() > 0, focused);
    }


    /**
     * 作用：对删除图标区域设置为"点击 即 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
        switch (event.getAction()) {
            // 判断动作 = 手指抬起时
            case MotionEvent.ACTION_UP:
                Drawable drawable = ic_delete;

                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色
     */
    private void setDeleteIconVisible(boolean deleteVisible, boolean focus) {
        setCompoundDrawables(null, null,
                deleteVisible ? ic_delete : null, null);
        //color = focus ? lineColor_click : lineColor_unclick;
        //setTextColor(color);
        invalidate();
    }

}
