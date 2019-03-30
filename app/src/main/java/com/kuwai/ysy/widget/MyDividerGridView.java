package com.kuwai.ysy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class MyDividerGridView extends GridView {

    private Paint paint;

    public MyDividerGridView(Context context) {
        super(context);
        initPaint();
    }

    public MyDividerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyDividerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#e6e6e6"));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        //子view的总数
        int childTotalCount = getChildCount();
        //列数
        int columnCount = getNumColumns();
        //行数
        int rowCount;
        if (childTotalCount % columnCount == 0) {
            rowCount = childTotalCount / columnCount;
        } else {
            rowCount = (childTotalCount / columnCount) + 1; //当余数不为0时，要把结果加上1
        }

        for (int i = 0; i < childTotalCount; i++) {//遍历子view
            View cellView = getChildAt(i);//获取子view
            int height = cellView.getHeight();//子view的高度

            if (!(i % columnCount == 0)) {//不是第一列
                canvas.drawLine(cellView.getLeft(), cellView.getTop() + 0.15f * height,
                        cellView.getLeft(), cellView.getBottom() - 0.15f * height, paint);
            }

            if (!(i >= (rowCount - 1) * columnCount)) {//不是最后一行的第一个及其他
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(),
                        cellView.getRight(), cellView.getBottom(), paint);
            }
        }
    }
}
