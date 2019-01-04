package com.kuwai.ysy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具
 *
 * @author xlx
 */
public class TimeFormatterUtils {
    /**
     * 将long行毫秒值转换为 00:00格式的string
     *
     * @param ms 毫秒值
     * @return 00:00格式的string
     */
    public static String formatTime(long ms) {
        long totalSeconds = ms / 1000;
        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60 % 60;
        long hours = totalSeconds / 60 / 60;
        String timeStr = "";
        if (hours > 9) {
            timeStr += hours + ":";
        } else if (hours > 0) {
            timeStr += "0" + hours + ":";
        }
        if (minutes > 9) {
            timeStr += minutes + ":";
        } else if (minutes > 0) {
            timeStr += "0" + minutes + ":";
        } else {
            timeStr += "00:";
        }
        if (seconds > 9) {
            timeStr += seconds;
        } else if (seconds > 0) {
            timeStr += "0" + seconds;
        } else {
            timeStr += "00";
        }

        return timeStr;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为月份
     */
    public static Integer stampToMonth(int s) {
        try {
            Date date = new Date(s * 1000);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * 将时间戳转换为天
     */
    public static Integer stampToDaty(int s) {
        try {
            Date date = new Date(s * 1000);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
