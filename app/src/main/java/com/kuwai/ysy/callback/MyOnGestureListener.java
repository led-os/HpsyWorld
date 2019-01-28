package com.kuwai.ysy.callback;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //addLoveView(e);
        return super.onDoubleTap(e);
    }
}
