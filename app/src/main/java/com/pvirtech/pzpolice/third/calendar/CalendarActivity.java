package com.pvirtech.pzpolice.third.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.third.calendar.view.CalendarView;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pvirtech.pzpolice.R.id.tv_next;
import static com.pvirtech.pzpolice.R.id.tv_pre;

public class CalendarActivity extends BaseActivity {
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.calendar)
    CalendarView calendar;


    /**
     * 日历对象
     */
    private Calendar cal;
    /**
     * 格式化工具
     */
    private SimpleDateFormat formatter;
    /**
     * 日期
     */
    private Date curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        initTitleView("出勤情况");
        initView();
    }

    private void initView() {
        cal = Calendar.getInstance();
        //初始化界面
        init();
    }

    /**
     * 初始化界面
     */
    private void init() {
        formatter = new SimpleDateFormat("yyyy年MM月");
        //获取当前时间
        curDate = cal.getTime();
        String str = formatter.format(curDate);
        tvMonth.setText(str);
        String strPre = (cal.get(Calendar.MONTH)) + "月";
        if (strPre.equals("0月")) {
            strPre = "12月";
        }
        tvPre.setText(strPre);
        String strNext = (cal.get(Calendar.MONTH) + 2) + "月";
        if (strNext.equals("13月")) {
            strNext = "1月";
        }
        tvNext.setText(strNext);
    }


    @OnClick({R.id.tv_pre, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_pre:
                cal.add(Calendar.MONTH, -1);
                init();
                calendar.setCalendar(cal);
                break;
            case tv_next:
                cal.add(Calendar.MONTH, +1);
                init();
                calendar.setCalendar(cal);
                break;
        }
    }
}
