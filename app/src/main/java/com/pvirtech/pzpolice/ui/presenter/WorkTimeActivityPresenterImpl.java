package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.entity.WorkDay;
import com.pvirtech.pzpolice.enumeration.DayStatus;
import com.pvirtech.pzpolice.ui.contract.WorkTimeActivityContract;
import com.pvirtech.pzpolice.ui.model.WorkTimeActivityModelImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/03/22
 */

public class WorkTimeActivityPresenterImpl implements WorkTimeActivityContract.Presenter {
    WorkTimeActivityModelImpl model = new WorkTimeActivityModelImpl();
    WorkTimeActivityContract.View view;

    public WorkTimeActivityPresenterImpl(WorkTimeActivityContract.View view) {
        this.view = view;
    }

    @Override
    public List<WorkDay> initDays(String date) {
        List<WorkDay> mData = new ArrayList<>();
        String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
        for (String data : weeks) {
            WorkDay workDay = new WorkDay();
            workDay.setText(data);
            workDay.setDayStatus(DayStatus.WEEK.getValue());
            mData.add(workDay);
        }
        //获取前月的第一天
        Calendar cal = Calendar.getInstance();
        //设置日历中月份的第1天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeekOfDay = cal.get(Calendar.DAY_OF_WEEK);
        firstWeekOfDay = (firstWeekOfDay + 6) % 7;
        System.out.println(firstWeekOfDay);
        for (int i = 1; i < firstWeekOfDay; i++) {
            WorkDay workDay = new WorkDay();
            workDay.setText("");
            workDay.setDayStatus(DayStatus.EMPTY.getValue());
            mData.add(workDay);
        }
        for (int i = 1; i < 31; i++) {
            WorkDay workDay = new WorkDay();
            workDay.setText(String.valueOf(i));
            workDay.setDayStatus(DayStatus.NORMAL.getValue());
            if (i == 2) {
                workDay.setDayStatus(DayStatus.TODAY.getValue());
            }
            if (i == 3) {
                workDay.setDayStatus(DayStatus.SELECTED.getValue());
            }
            mData.add(workDay);
        }
        return mData;
    }
}