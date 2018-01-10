package com.pvirtech.pzpolice.third;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by youpengda on 2017/3/1.
 */

public class DateAndTimePickerDialog {

    public void show(Activity mContext, String dateAndTime, String title, final OnSelectedListener listener) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.date_time_picker_layout, null);
        /**
         * 设置日期
         */
        Date date;

        if (TextUtils.isEmpty(dateAndTime) || dateAndTime.equals(mContext.getResources().getString(R.string.please_fill_in))) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = getDateFromYMDHM(dateAndTime);
        }
        final String[] times = {getDateByFrom(date, "yyyy-MM-dd"), getDateByFrom(date, "HH:mm")};
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.getTime();
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        // 初始化DatePicker组件，初始化时指定监听器

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new
                DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String datePickerString = year + "-" + getWidthZero((monthOfYear + 1)) + "-" + getWidthZero(dayOfMonth);
                times[0] = datePickerString;
                System.out.println("=======" + datePickerString);
            }
        });
        /**
         * 设置时间
         */
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        //设置成24小时，隐藏AM/PM picker
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String timePickerString = getWidthZero(hourOfDay) + ":" + getWidthZero(minute);
                times[1] = timePickerString;
                System.out.println("=======" + timePickerString);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        View mTitleView = LayoutInflater.from(mContext).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = times[0] + " " + times[1];
                listener.onSelected(data);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.show();
    }

    public Date getDateFromYMDHM(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return format.parse(time);
        } catch (Exception e) {
            System.out.println(e);
//            e.printStackTrace();
        }
        return new Date(System.currentTimeMillis());
    }

    public String getDateToYMDHM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public String getDateByFrom(Date date, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        return format.format(date);
    }

    private String getWidthZero(int data) {
        String strData = "";
        if (data < 9) {
            strData = "0" + data;
        } else {
            strData = String.valueOf(data);
        }
        return strData;
    }
}
