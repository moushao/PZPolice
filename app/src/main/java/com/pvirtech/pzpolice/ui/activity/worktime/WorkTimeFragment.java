package com.pvirtech.pzpolice.ui.activity.worktime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkDay;
import com.pvirtech.pzpolice.enumeration.DayStatus;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.ui.adapter.CalendarAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.pvirtech.pzpolice.utils.TimeUtil.getDateFromYMDHM;
import static com.pvirtech.pzpolice.utils.TimeUtil.getDateToYMD;

/**
 * A simple {@link Fragment} subclass.
 * 工作时间
 */
public class WorkTimeFragment extends BaseFragment {

    static String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
    int httpSize = 0;
    int httpTotalSize = 1;

    @BindView(R.id.recyle_calendar)
    RecyclerView recyleCalendar;
    List<WorkDay> mData = new ArrayList<>();
    CalendarAdapter taskMenuAdapter = null;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    int firstWeekOfDay;
    String strDate = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_work_time, container, false);
        initView(view);
        initView();
        appholidayWtime(tvMonth.getText().toString());
        return view;
    }


    @OnClick({R.id.tv_user, R.id.lin_chose_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user:
                break;
            case R.id.lin_chose_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(getActivity(), tvMonth.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvMonth.setText(data);
                        strDate = data;
                        appholidayWtime(data);
                    }
                });
                break;
        }
    }


    private void appholidayWtime(String yearAndMonth) {

        Date date = getDateFromYMDHM(yearAndMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        date = calendar.getTime();
        yearAndMonth = getDateToYMD(date);
        /**
         * 放置要提交的数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("date", yearAndMonth);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appworksheetMtime(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<WorkDay>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<WorkDay>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<WorkDay> list = response.getData();
                    initDays(list);
                    taskMenuAdapter.setmData(mData);
                    taskMenuAdapter.notifyDataSetChanged();
                    httpSize++;
                    if (httpSize == httpTotalSize) {
                        hideLoading();
                    }
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showError("");
            }
        }));

    }

    private void initView() {
        taskMenuAdapter = new CalendarAdapter(mContext, mData, new CalendarAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (position < firstWeekOfDay + 7 - 1) {
                    return;
                }
                for (WorkDay workDay : mData) {
                    if (workDay.getDayStatus() == DayStatus.SELECTED.getValue()) {
                        workDay.setDayStatus(DayStatus.NORMAL.getValue());
                    }
                }
                mData.get(position).setDayStatus(DayStatus.SELECTED.getValue());
                taskMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(int position) {
                System.out.println("aaaaaaa");
                return false;
            }
        });
        recyleCalendar.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyleCalendar.setAdapter(taskMenuAdapter);
        tvMonth.setText(TimeUtil.getYMD());
    }

    public List<WorkDay> initDays(List<WorkDay> list) {
        mData.clear();
        String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
        for (String data : weeks) {
            WorkDay workDay = new WorkDay();
            workDay.setText(data);
            workDay.setDayStatus(DayStatus.WEEK.getValue());
            mData.add(workDay);
        }
        Date date = getDateFromYMDHM(list.get(0).getDate());

        //获取前月的第一天
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        firstWeekOfDay = cal.get(Calendar.DAY_OF_WEEK);
        firstWeekOfDay = (firstWeekOfDay + 6) % 7;
        for (int i = 1; i < firstWeekOfDay; i++) {
            WorkDay workDay = new WorkDay();
            workDay.setText("");
            workDay.setDayStatus(DayStatus.EMPTY.getValue());
            mData.add(workDay);
        }
        for (WorkDay workDay : list) {
            String[] day = workDay.getDate().split("-");
            workDay.setText(day[2]);
            workDay.setDayStatus(DayStatus.NORMAL.getValue());
            mData.add(workDay);
        }
        return mData;
    }


}
