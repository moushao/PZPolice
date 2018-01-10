package com.pvirtech.pzpolice.third.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pvirtech.pzpolice.third.calendar.Day;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义的日历控件
 * Created by xiaozhu on 2016/8/1.
 */
public class CalendarView extends View {

    private Context context;
    /**
     * 画笔
     */
    private Paint paint;
    /***
     * 当前的时间
     */
    private Calendar calendar;
    private OnSelectChangeListener listener;

    /**
     * 改变日期，并更改当前状态，由于绘图是在calendar基础上进行绘制的，所以改变calendar就可以改变图片
     *
     * @param calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if ((calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR)).equals(DayManager.getCurrentTime())) {
            DayManager.setCurrent(DayManager.getTempcurrent());
        } else {
            DayManager.setCurrent(-1);
        }
        invalidate();
    }

    public CalendarView(Context context) {
        super(context);
        this.context = context;
        //初始化控件
        initView();
    }


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //初始化控件
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化控件
        initView();
    }

    /***
     * 初始化控件
     */
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        calendar = Calendar.getInstance();
        DayManager.setCurrent(calendar.get((Calendar.DAY_OF_MONTH)));
        DayManager.setTempcurrent(calendar.get(Calendar.DAY_OF_MONTH
        ));
        DayManager.setCurrentTime(calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR));
    }

    List<Day> days;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取day集合并绘制
        days = DayManager.createDayByCalendar(calendar, getMeasuredWidth(), getMeasuredHeight());
        for (Day day : days) {
            day.drawDays(canvas, context, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            //判断点击的是哪个日期
            float x = event.getX();
            float y = event.getY();
            //计算点击的是哪个日期
            int locationX = (int) (x * 7 / getMeasuredWidth());
            int locationY = (int) ((calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1) * y / getMeasuredHeight());
            if (locationY == 0) {
                return super.onTouchEvent(event);
            } else if (locationY == 1) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                if (locationX < calendar.get(Calendar.DAY_OF_WEEK) - 2) {
                    return super.onTouchEvent(event);
                }
            } else if (locationY == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                if (locationX > calendar.get(Calendar.DAY_OF_WEEK) - 2) {
                    return super.onTouchEvent(event);
                }
            }
            calendar.set(Calendar.WEEK_OF_MONTH, (int) locationY);
            calendar.set(Calendar.DAY_OF_WEEK, (int) (locationX + 2));
            if (locationX >= 6) {
                calendar.set(Calendar.WEEK_OF_MONTH, (int) locationY + 1);
                calendar.set(Calendar.DAY_OF_WEEK, (int) (locationX + 2) % 7);
            }
            Date date = calendar.getTime();
            System.out.println("CalendarView-----getTimeToday" + getTimeToday(date));
            DayManager.setSelect(calendar.get(Calendar.DAY_OF_MONTH));
            if (listener != null) {
                listener.selectChange(this, calendar.getTime());
            }
            invalidate();
            Day day=days.get((int) calendar.get(Calendar.DAY_OF_MONTH) + 6);
            System.out.println("天数" + day.text);
        }
        return super.onTouchEvent(event);
    }

    public static String getTimeToday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 设置日期选择改变监听
     *
     * @param listener
     */
    public void setOnSelectChangeListener(OnSelectChangeListener listener) {

        this.listener = listener;
    }

    /**
     * 日期选择改变监听的接口
     */
    public interface OnSelectChangeListener {
        void selectChange(CalendarView calendarView, Date date);
    }

    /**
     * 更新这一天的详细信息
     */


}
