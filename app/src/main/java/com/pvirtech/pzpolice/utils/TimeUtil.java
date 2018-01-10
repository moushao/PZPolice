package com.pvirtech.pzpolice.utils;

import android.annotation.SuppressLint;

import com.pvirtech.pzpolice.main.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author way
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static String getChatTime(long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(timesamp);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(timesamp);
                break;
            case 2:
                result = "前天 " + getHourAndMin(timesamp);
                break;

            default:
                result = temp + "天前 ";
                break;
        }
        return result;
    }

    public static String getDHMS() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd_HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getYM() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date(time));
    }

    public static String getYMD() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getYMDHM() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static long compareDate(String s1, String s2) {

        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到指定模范的时间
        Date d1, d2;
        try {
            d1 = sdf.parse(s1);
            d2 = sdf.parse(s2);
            return (((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
        //比较

    }

    /**
     * 判断时间是否在某一个区间
     */
    public static boolean compareSection(String start, String end, String compare) {

        //设定时间的模板
        SimpleDateFormat sdf = null;
        if (start.length() == 19) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (start.length() == 16) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (start.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        //得到指定模范的时间
        Date d1, d2, d3;
        try {
            d1 = sdf.parse(start);
            d2 = sdf.parse(end);
            d3 = sdf.parse(compare);
            if (d3.getTime() - d1.getTime() >= 0 && d2.getTime() - d3.getTime() >= 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
        //比较

    }

    public static String getHMS() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date(time));
    }


    public static Date getDateFromYMDHM(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(time);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Date(System.currentTimeMillis());
    }


    public static String getCalendarToYMD(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static String getDateToYMD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getDateToYMDHM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String appendHM9(String time) {
        StringBuffer stringBuffer = new StringBuffer(time);
        stringBuffer.append(Constant.WORK_ON);
        return stringBuffer.toString();
    }

    public static String appendHM17(String time) {
        StringBuffer stringBuffer = new StringBuffer(time);
        stringBuffer.append(Constant.WORK_OFF);
        return stringBuffer.toString();
    }


    /**
     * 获取当前日期的前一个月或下一个月
     *
     * @param time
     * @param isNext
     * @return
     */
    public static String getDateToYMDNextOrPre(String time, boolean isNext) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(time);
        } catch (Exception e) {
            System.out.println(e);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(date);
        if (isNext) {
            calendar.add(Calendar.MONTH, +1);
        } else {
            calendar.add(Calendar.MONTH, -1);
        }
        return format.format(calendar.getTime());
    }

    /**
     * 日期今天以前返回false 否则返回true
     */
    public static boolean isOverdue(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date();
        Date date = new Date();
        try {
            dateNow = format.parse(TimeUtil.getYMD());
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (date.getTime() - dateNow.getTime() >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 时间是否在当前时间之前
     *
     * @param data
     * @return
     */
    public static boolean isOverdueCurrentTime(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (date.getTime() - System.currentTimeMillis() >= 0) {
            return true;
        }
        return false;
    }


}
