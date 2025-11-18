package com.example.forkchat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class TimeUtils {
    
    public static String getTimeAgo(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        
        long second = 1000;
        long minute = 60 * second;
        long hour = 60 * minute;
        long day = 24 * hour;
        long month = 30 * day;
        long year = 12 * month;
        
        if (diff < minute) {
            return "刚刚";
        } else if (diff < hour) {
            return diff / minute + "分钟前";
        } else if (diff < day) {
            return diff / hour + "小时前";
        } else if (diff < month) {
            return diff / day + "天前";
        } else if (diff < year) {
            return diff / month + "个月前";
        } else {
            return diff / year + "年前";
        }
    }
    
    public static String formatDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(new Date(timestamp));
    }
    
    public static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date(timestamp));
    }
}

