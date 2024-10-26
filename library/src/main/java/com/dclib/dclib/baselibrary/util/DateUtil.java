package com.dclib.dclib.baselibrary.util;

import android.text.format.DateUtils;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 * Created on 2018/5/10..
 *
 * @author daichao
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD2 = "yyyy/MM/dd";
    public static final String YYYY_MM_DD3 = "yyyy年MM月dd日";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM2 = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM3 = "yyyy年MM月dd日 HH:mm";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS3 = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * 字符串转Date
     *
     * @param dateString 日期字符串 2020-10-01
     * @return Date
     */
    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 字符串转Date
     *
     * @param dateString 日期字符串
     * @param format     yyyy/MM/dd HH:mm:ss
     * @return Date
     */
    public static Date strToDate(String dateString, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd
     */
    public static String dateToStr(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date转字符串
     *
     * @param date   时间
     * @param format yyyy/MM/dd HH:mm:ss
     * @return yyyy-MM-dd
     */
    public static String dateToStr(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前日期时间字符串
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDateStr() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前日期时间字符串
     *
     * @param format yyyy/MM/dd HH:mm:ss
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String getCurrentDatetimeStr(String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取刚刚时间优化时间
     *
     * @param dateStr The milliseconds.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String dateStr) {
        if (StringUtils.isTrimEmpty(dateStr)) {
            return "";
        }

        long millis = TimeUtils.string2Millis(dateStr);
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tc", millis);
        }
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d\n秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d\n分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return "今天";
        } else if (millis >= wee - TimeConstants.DAY) {
            return "昨天";
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 获取刚刚时间优化时间
     *
     * @param millis 毫秒值
     * @return
     */
    public static String getFriendlyTimeSpanByNow(long millis) {
        if (millis == 0) {
            return "";
        }

        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else if (millis >= (wee - TimeConstants.DAY) * 2) {
            return String.format("天%tR", millis);
        } else {
            return "3天前";
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取年
     */
    public static int getYear(String timeString) {
        return Integer.parseInt(timeString.substring(0, 4));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取月
     */
    public static int getMonth(String timeString) {
        return Integer.parseInt(timeString.substring(5, 7));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取日
     */
    public static int getDay(String timeString) {
        return Integer.parseInt(timeString.substring(8, 10));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取时
     */
    public static int getHour(String timeString) {
        return Integer.parseInt(timeString.substring(11, 13));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取分
     */
    public static int getMinute(String timeString) {
        return Integer.parseInt(timeString.substring(14, 16));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取秒
     */
    public static int getSecond(String timeString) {
        return Integer.parseInt(timeString.substring(17, 19));
    }

    /**
     * yyyy-MM-dd HH:mm:ss 获取时分
     */
    public static String getHourMinute(String timeString) {
        return timeString.substring(11, 16);
    }

    /**
     * 获取当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 判断是否是昨天
     */
    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }

    /**
     * 判断是否是今天
     */
    public static boolean isToday(Date d) {
        return DateUtils.isToday(d.getTime());
    }

    /**
     * 判断是否是明天
     */
    public static boolean isTomorrow(Date d) {
        return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
    }

    /**
     * 获取当天的时间
     */
    public static String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int calendarMonth = now.get(Calendar.MONTH) + 1;
        String month = calendarMonth + "";
        if (calendarMonth < 10) {
            month = "0" + calendarMonth;
        }
        return now.get(Calendar.YEAR) + "-" + month + "-" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 昨日
     */
    public static String getYesterday() {
        Calendar now = Calendar.getInstance();
        int calendarMonth = now.get(Calendar.MONTH) + 1;
        String month = calendarMonth + "";
        if (calendarMonth < 10) {
            month = "0" + calendarMonth;
        }
        return now.get(Calendar.YEAR) + "-" + month + "-" + (now.get(Calendar.DAY_OF_MONTH) - 1);
    }

    /**
     * 本周开始的第一天
     */
    public static String getThisWeekStart() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * 本周开始的最后一天
     */
    public static String getThisWeekEnd() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        c.add(Calendar.DAY_OF_WEEK, 6);
        return df.format(c.getTime());
    }

    /**
     * 本月开始的第一天
     */
    public static String getThisMonthStart() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * 本月的最后一天
     */
    public static String getThisMonthEnd() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(ca.getTime());
    }

    /**
     * 本季度的第一天
     */
    public static String getThisQuarterStart() {
        Calendar now = Calendar.getInstance();
        int calendarMonth = now.get(Calendar.MONTH) + 1;
        return getThisSeasonFirstTime(calendarMonth);
    }

    private static String getThisSeasonFirstTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        String seasonDate = years_value + "-" + start_month + "-" + "01";
        return seasonDate;

    }

    /**
     * 本季度的最后一天
     */
    public static String getThisQuarterEnd() {
        Calendar now = Calendar.getInstance();
        int calendarMonth = now.get(Calendar.MONTH) + 1;
        return getThisSeasonFinallyTime(calendarMonth);
    }

    private static String getThisSeasonFinallyTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + end_month + "-" + end_days;
        return seasonDate;
    }

    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 本年度第一天
     */
    public static String getThisYearStart() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "-01-01";
    }

    /**
     * 本年度最后
     */
    public static String getThisYearEnd() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "-12-31";
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：负的往前推, 正的往后推
     * @return yyyy-MM-dd HH:mm
     */
    public static String getOtherDateTimeStr(int diff, String format) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, diff);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(mCalendar.getTime());
    }

    /**
     * 秒转换为时分秒
     */
    public static String convertSecondsToTime(int seconds) {
        try {
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int secs = seconds % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, secs);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 毫秒转成时分秒
     *
     * @param millisecond 毫秒
     * @return 00:00:00
     */
    public static String millisecondToHmsStr(long millisecond) {
        millisecond = millisecond / 1000;
        String hh = String.valueOf(millisecond / 60 / 60 % 60);
        if (hh.length() < 2) {
            hh = "0" + hh;
        }
        String mm = String.valueOf(millisecond / 60 % 60);
        if (mm.length() < 2) {
            mm = "0" + mm;
        }
        String ss = String.valueOf(millisecond % 60);
        if (ss.length() < 2) {
            ss = "0" + ss;
        }
        return hh + ":" + mm + ":" + ss;
    }
}
