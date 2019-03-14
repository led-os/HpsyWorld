package com.kuwai.ysy.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.alivc.player.VcPlayerLog;
import com.kuwai.ysy.utils.language.HanziToPinyin;
import com.rayhahah.rbase.BaseApplication;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public static void showOrHide(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //  imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static void showOrHide(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 将double类型的数字保留两位小数（四舍五入）
     *
     * @param number
     * @return
     */
    public static String format2Number(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.00");
        return df.format(number);
    }

    /**
     * 将double类型的数字保留两位小数（四舍五入）
     *
     * @param number
     * @return
     */
    public static String format1Number(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.0");
        return df.format(number);
    }

    /**
     * encrypt32
     *
     * @param encryptStr
     * @return String
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    public static boolean isStrangePhone() {
        boolean strangePhone = Build.DEVICE.equalsIgnoreCase("mx5")
                || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                || Build.DEVICE.equalsIgnoreCase("Z00A_1")
                || Build.DEVICE.equalsIgnoreCase("hwH60-L02")
                || Build.DEVICE.equalsIgnoreCase("hermes")
                || (Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))
                || (Build.DEVICE.equalsIgnoreCase("m1metal") && Build.MANUFACTURER.equalsIgnoreCase("Meizu"));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;

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

    /**
     * 状态栏高度
     */
    public static int getStatusHeight(Context context) {

        int statusBarHeight = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int getWindowWidth() {
        WindowManager wm = (WindowManager) BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }


    /**
     * 获取当前本地apk的版本
     *
     * @param
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = BaseApplication.getAppContext().getPackageManager().
                    getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @return
     * @para
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = BaseApplication.getAppContext().getPackageManager().
                    getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getScreenHeight() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return BaseApplication.getAppContext().getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    public static String getWeekOfDateString(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /* 根据月份和日期判断星座 */
    public static String getStar(String date) {
        if (TextUtils.isEmpty(date)) {
            return "未知";
        }
        String temp[] = date.split("-");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);
        String xz = null;
        if ((month == 3 && day >= 21 && day <= 31) || (month == 4 && day >= 1 && day <= 19)) {
            xz = "白羊";
        }
        if ((month == 4 && day >= 20 && day <= 30) || (month == 5 && day >= 1 && day <= 20)) {
            xz = "金牛";
        }
        if ((month == 5 && day >= 21 && day <= 31) || (month == 6 && day >= 1 && day <= 21)) {
            xz = "双子";
        }
        if ((month == 6 && day >= 22 && day <= 30) || (month == 7 && day >= 1 && day <= 22)) {
            xz = "巨蟹";
        }
        if ((month == 7 && day >= 23 && day <= 31) || (month == 8 && day >= 1 && day <= 22)) {
            xz = "狮子";
        }
        if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day >= 1 && day <= 22)) {
            xz = "处女";
        }
        if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 23)) {
            xz = "天秤";
        }
        if ((month == 10 && day >= 24 && day <= 31) || (month == 11 && day >= 1 && day <= 22)) {
            xz = "天蝎";
        }
        if ((month == 11 && day >= 23 && day <= 30) || (month == 12 && day >= 1 && day <= 21)) {
            xz = "射手";
        }
        if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day >= 1 && day <= 19)) {
            xz = "摩羯";
        }
        if ((month == 1 && day >= 20 && day <= 31) || (month == 2 && day >= 1 && day <= 18)) {
            xz = "水瓶";
        }
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) { // 闰年2月29天
            if ((month == 2 && day >= 19 && day <= 29) || (month == 3 && day >= 1 && day <= 20)) {
                xz = "双鱼";
            }
        } else { // 平年2月28天
            if ((month == 2 && day >= 19 && day <= 28) || (month == 3 && day >= 1 && day <= 20)) {
                xz = "双鱼";
            }
        }
        return xz;
    }
}
