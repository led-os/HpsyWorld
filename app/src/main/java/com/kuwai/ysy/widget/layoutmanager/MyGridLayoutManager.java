package com.kuwai.ysy.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class MyGridLayoutManager  extends GridLayoutManager {

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
