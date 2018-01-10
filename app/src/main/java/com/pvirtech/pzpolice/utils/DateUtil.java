package com.pvirtech.pzpolice.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能描述：时间工具类
 *
 * @author Justice
 */
public class DateUtil {


    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        DateFormat dateFormat = null;
        try {

            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        DateFormat dateFormat = null;
        Calendar calendar = null;
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDateString() {
        String result = null;

        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int second = mCalendar.get(Calendar.SECOND);

        result = String.format("%d%02d%02d%02d%02d%02d", year, month, day,
                hour, minute, second);

        return result;
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回指定日期的周数
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定年周的第一天和最后一天
     *
     * @param year
     * @param week
     * @param flag true-返回第一天日期,false-返回最后一天日期
     * @return
     */
    public static Date getDayByWeek(int year, int week, boolean flag) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        if (!flag)
            cal.setTimeInMillis(cal.getTimeInMillis() + 6 * 24 * 60 * 60 * 1000);
        return cal.getTime();
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = null;

        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        Date date = null;
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        Date date = null;
        Calendar calendar = null;

        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param time   时间字符串
     * @param format 时间字符串对应的时间格式
     * @return
     */

    public static Date stringToDate(String time, String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date s_date = (Date) sdf.parse(time);
            return s_date;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM").format(new Date()).split("-")[1]);
    }

    /**
     * 获取当前周数
     *
     * @return
     */
    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentDay() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-")[2]);
    }

    /**
     * 获取当前年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int month) {
        return getDateOfMonth(getCurrentYear(), month);
    }

    /**
     * 获取指定年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int year, int month) {
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);// Java月份才0开始算
        int dateOfMonth = calendar.getActualMaximum(Calendar.DATE); // 获取当前月的天数
        return dateOfMonth;
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    public static void main(String[] args) {
        Date d = new Date();
        // System.out.println(d.toString());
        // System.out.println(formatDate(d).toString());
        // System.out.println(getMonthBegin(formatDate(d).toString()));
        // System.out.println(getMonthBegin("2008/07/19"));
        // System.out.println(getMonthEnd("2008/07/19"));
        System.out.println(addDate(d, 15).toString());

    }

}
