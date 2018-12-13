package com.kuwai.ysy.widget.exchange;

import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.utils.Utils;

public class BGAGridDivider extends RecyclerView.ItemDecoration {
    private int mSpace;

    private BGAGridDivider(int space) {
        mSpace = space;
    }

    /**
     * 设置间距资源 id
     *
     * @param resId
     * @return
     */
    public static BGAGridDivider newInstanceWithSpaceRes(@DimenRes int resId) {
        return new BGAGridDivider(Utils.getDimensionPixelOffset(resId));
    }

    /**
     * 设置间距
     *
     * @param spaceDp 单位为 dp
     * @return
     */
    public static BGAGridDivider newInstanceWithSpaceDp(int spaceDp) {
        return new BGAGridDivider(Utils.dp2px(spaceDp));
    }

    /**
     * 设置间距
     *
     * @param spacePx 单位为 px
     * @return
     */
    public static BGAGridDivider newInstanceWithSpacePx(int spacePx) {
        return new BGAGridDivider(spacePx);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.top = mSpace;
        outRect.bottom = mSpace;
    }
}