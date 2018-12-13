package com.kuwai.ysy.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.TypedValue;

import com.kuwai.ysy.utils.language.HanziToPinyin;
import com.rayhahah.rbase.BaseApplication;

import java.util.ArrayList;

public class Utils {

    private static char[] initialtable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',};

    public static Drawable tint(Drawable drawable, int color) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static boolean isNullString(String string) {

        if (string == null || "".equals(string)) {
            return true;
        }
        return false;
    }

    /**
     * 获取colorPrimary的颜色,需要V7包的支持
     *
     * @param context 上下文
     * @return 0xAARRGGBB
     */
    public static int getColorPrimary(Context context) {
        Resources res = context.getResources();
        int attrRes = res.getIdentifier("colorPrimary", "attr", context.getPackageName());
        if (attrRes == 0) {
            return 0xFF009688;
        }
        return ContextCompat.getColor(context, getResourceId(context, attrRes));
    }

    /**
     * 获取自定义属性的资源ID
     *
     * @param context 上下文
     * @param attrRes 自定义属性
     * @return resourceId
     */
    private static int getResourceId(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.resourceId;
    }

    public static Drawable newDrawable(Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        return constantState != null ? constantState.newDrawable() : drawable;
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int dip2px(Context mContext, float dp) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, BaseApplication.getAppContext().getResources().getDisplayMetrics());
    }

    /**
     * 根据字符串获取当前首字母
     *
     * @param name
     * @return
     */
    public static String getLetter(String name) {
        String DefaultLetter = "#";
        if (TextUtils.isEmpty(name)) {
            return DefaultLetter;
        }
        char char0 = name.toLowerCase().charAt(0);
        if (Character.isDigit(char0)) {
            return DefaultLetter;
        }
        ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
        if (l != null && l.size() > 0 && l.get(0).target.length() > 0) {
            HanziToPinyin.Token token = l.get(0);
            // toLowerCase()返回小写， toUpperCase()返回大写
            String letter = token.target.substring(0, 1).toUpperCase();
            /*char c = letter.charAt(0);
            // 这里的 'a' 和 'z' 要和letter的大小写保持一直。
            if (c < 'a' || c > 'z') {
                return DefaultLetter;
            }*/
            return letter;
        }
        return DefaultLetter;
    }

    public static int getScreenWidth() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return BaseApplication.getAppContext().getResources().getDimensionPixelOffset(resId);
    }
}