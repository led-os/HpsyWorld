package com.kuwai.ysy.widget.shadow;

import android.support.v4.view.ViewCompat;
import android.view.View;


public class ShadowHelper {

    public static void setShadowBgForView(View view, ShadowConfig.Builder config) {
        //关闭硬件加速
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, config.builder());
    }
}
