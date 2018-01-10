package com.pvirtech.pzpolice.third;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by youpengda on 2017/3/1.
 */

public class DatePickerDialog {

    public void show(Activity mContext, String dateAndTime, final OnSelectedListener listener) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.date_picker_layout_dialog, null);
        /**
         * 设置日期
         */
        Date date;

        if (TextUtils.isEmpty(dateAndTime) || dateAndTime.equals(mContext.getResources().getString(R.string.please_fill_in))) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = getDateFromYMDHM(dateAndTime);
        }
        final String[] times = {getDateByFrom(date, "yyyy-MM-dd")};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.getTime();
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        // 初始化DatePicker组件，初始化时指定监听器

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker
                .OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String datePickerString = year + "-" + getWidthZero((monthOfYear + 1)) + "-" + getWidthZero(dayOfMonth);
                times[0] = datePickerString;
                System.out.println("=======" + datePickerString);
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        View mTitleView = LayoutInflater.from(mContext).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        tvTitle.setText("请选择日期");
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = times[0];
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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


    /**
     * 年月选择器
     */
    String datePickerString = "";

    public void showDialogYM(Activity mContext, String ym, final OnSelectedListener listener) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.date_picker_layout_dialog, null);
        /**
         * 设置日期
         */
        Date date;

        if (TextUtils.isEmpty(ym)) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = getDateFromYM(ym);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.getTime();
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        // 初始化DatePicker组件，初始化时指定监听器
        hidePicker(datePicker, true, true, false);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker
                .OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerString = year + "-" + getWidthZero((monthOfYear + 1));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        View mTitleView = LayoutInflater.from(mContext).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        tvTitle.setText("请选择月份");
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSelected(datePickerString);
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

    public Date getDateFromYM(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            return format.parse(time);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Date(System.currentTimeMillis());
    }

    /**
     * @param picker 传入一个DatePicker对象,隐藏或者显示相应的时间项
     */
    public static void hidePicker(DatePicker picker, boolean y, boolean m, boolean d) {
        // 利用java反射技术得到picker内部的属性，并对其进行操作
        Class<? extends DatePicker> c = picker.getClass();
        try {
            Field fd = null, fm = null, fy = null;
            // 系统版本大于5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = picker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                        return;
                    }
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                // 系统版本大于4.0
                fd = c.getDeclaredField("mDaySpinner");
                fm = c.getDeclaredField("mMonthSpinner");
                fy = c.getDeclaredField("mYearSpinner");
            } else {
                fd = c.getDeclaredField("mDayPicker");
                fm = c.getDeclaredField("mMonthPicker");
                fy = c.getDeclaredField("mYearPicker");
            }
            // 对字段获取设置权限
            fd.setAccessible(d);
            fm.setAccessible(m);
            fy.setAccessible(y);
            // 得到对应的控件
            View vd = (View) fd.get(picker);
            View vm = (View) fm.get(picker);
            View vy = (View) fy.get(picker);

            vd.setVisibility(View.GONE);
            vm.setVisibility(View.VISIBLE);
            vy.setVisibility(View.VISIBLE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
