package com.kuwai.ysy.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kuwai.ysy.utils.language.FontCache;

public class CustomFontTextview extends AppCompatTextView {

    public CustomFontTextview(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomFontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomFontTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("HELVETICANEUE_BLACKCOND.TTF", context);
        setTypeface(customFont);
    }
}
