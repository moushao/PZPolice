package com.pvirtech.pzpolice.ui.activity.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.DeclareEntity;
import com.pvirtech.pzpolice.entity.WorkDay;
import com.pvirtech.pzpolice.enumeration.DayStatus;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.adapter.CalendarAdapter;
import com.pvirtech.pzpolice.ui.adapter.DeclareEntityAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WorkTimeCalendlarActivity extends BaseActivity {
    String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
    int httpTotalSize = 2;
    int httpSize = 0;
    DeclareEntityAdapter declareEntityAdapter;
    List<DeclareEntity.DAYRECORDBean> declareEntityList = new ArrayList<>();
    int firstWeekOfDay;
    String strDate = "";
    List<WorkDay> mData = new ArrayList<>();
    CalendarAdapter taskMenuAdapter = null;
    DeclareEntity declareEntity;

    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.recycleview_work_time)
    RecyclerView recycleviewWorkTime;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.ll_day01)
    LinearLayout llDay01;
    @BindView(R.id.tv_before)
    TextView tvBefore;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.recyle_calendar)
    RecyclerView recyleCalendar;
    @BindView(R.id.tv_statistics)
    TextView tvStatistics;
    @BindView(R.id.tv_declare)
    TextView tvDeclare;
    @BindView(R.id.ll_declare)
    LinearLayout llDeclare;
    @BindView(R.id.ll_statistics)
    LinearLayout llStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_time_calendlar);
        ButterKnife.bind(this);
        initInfoTitleView("工作时间");
        mContext = WorkTimeCalendlarActivity.this;
        TAG = "TeamAssessmentActivity-------------";
        initView();
        showLoading("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskMenuAdapter.setSelectedDay(tvInfoDate.getText().toString());
        appholidayWtime(tvInfoDate.getText().toString());
    }

    /**
     * 取得月历信息
     *
     * @param yearAndMonth
     */
    private void appholidayWtime(final String yearAndMonth) {
        if (httpSize >= httpTotalSize) {
            showLoading("");
        }

        String today = yearAndMonth;
        Date date = TimeUtil.getDateFromYMDHM(today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        date = calendar.getTime();
        today = TimeUtil.getDateToYMD(date);
        /**
         * 放置要提交的数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("date", today);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appworksheetMtime(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<WorkDay>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<WorkDay>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    httpSize++;
                    if (httpSize >= httpTotalSize) {
                        hideLoading();
                    }
                    List<WorkDay> list = response.getData();
                    tvInfoDate.setText(yearAndMonth);
                    initDays(list);
                    taskMenuAdapter.setmData(mData);
                    initAdapter();

                    taskMenuAdapter.notifyDataSetChanged();
                    hideLoading();

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
        tvStatistics.setText("");
        llStatistics.setVisibility(View.INVISIBLE);
        /**
         * 初始化月历详情
         */
        initAdapter();


        /**
         * 时间申报详情view
         */
        declareEntityAdapter = new DeclareEntityAdapter(mContext, declareEntityList, new DeclareEntityAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });


        recycleviewWorkTime.setLayoutManager(new GridLayoutManager(mContext, 1));
        recycleviewWorkTime.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycleviewWorkTime.setAdapter(declareEntityAdapter);

      /*  recyleCalendar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isFirstTouch == 1) {
                            mPosX = event.getX();
                            mPosY = event.getY();
                        }
                        isFirstTouch++;
                        break;
                    case MotionEvent.ACTION_UP:
                        isFirstTouch = 1;
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        if (mCurPosX - mPosX > 0 && (Math.abs(mCurPosX - mPosX) > 100)) {
                            //向右滑動
                            next();
                        } else if (mCurPosX - mPosX < 0 && (Math.abs(mCurPosX - mPosX) > 100)) {
                            //向左滑动
                            before();
                        }
                        break;
                }
                return false;
            }
        });*/
    }

    private void next() {
        String time = TimeUtil.getDateToYMDNextOrPre(tvInfoDate.getText().toString(), false);
        appholidayWtime(time);
        declareEntityList.clear();
        declareEntityAdapter.setmData(declareEntityList);
        llDay01.setVisibility(View.GONE);
        tvStatistics.setText("");
        llStatistics.setVisibility(View.INVISIBLE);
        initXY();
    }

    private void before() {
        String time = TimeUtil.getDateToYMDNextOrPre(tvInfoDate.getText().toString(), true);
        appholidayWtime(time);
        declareEntityList.clear();
        declareEntityAdapter.setmData(declareEntityList);
        llDay01.setVisibility(View.GONE);
        tvStatistics.setText("");
        llStatistics.setVisibility(View.INVISIBLE);
        initXY();
    }

    private void initAdapter() {
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
                tvInfoDate.setText(mData.get(position).getDate());
                appworksheetDtime(tvInfoDate.getText().toString());
                taskMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(int position) {
                System.out.println("aaaaaaa");
                return false;
            }
        });
        recyleCalendar.setLayoutManager(new GridLayoutManager(mContext, 7));
        taskMenuAdapter.setSelectedDay(tvInfoDate.getText().toString());
        recyleCalendar.setAdapter(taskMenuAdapter);


    }

    float mPosX = 0;
    float mPosY = 0;
    float mCurPosX = 0;
    float mCurPosY = 0;
    int isFirstTouch = 1;

    private void initXY() {
        mPosX = 0;
        mPosY = 0;
        mCurPosX = 0;
        mCurPosY = 0;
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
        Date date = TimeUtil.getDateFromYMDHM(list.get(0).getDate());

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

            /*if (workDay.getDate().equals(TimeUtil.getYMD())) {*/
            if (workDay.getDate().equals(tvInfoDate.getText().toString())) {
                workDay.setDayStatus(DayStatus.SELECTED.getValue());
                appworksheetDtime(tvInfoDate.getText().toString());
            }
            mData.add(workDay);
        }
        return mData;
    }


    /**
     * 取得某一天的详情
     */
    private void appworksheetDtime(final String day) {
        if (httpSize >= httpTotalSize) {
            showLoading("");
        }

        final String dayNow = TimeUtil.getYMD();
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        data.addProperty("date", day);
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appworksheetDtime(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<DeclareEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<DeclareEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    declareEntity = response.getData();
                    int state = declareEntity.getSTATE();
                    declareEntityList.clear();
                    llDay01.setVisibility(View.VISIBLE);
                    List<DeclareEntity.DAYRECORDBean> dayrecordBeanList = response.getData().getDAYRECORD();
                    if (!ListUtils.isEmpty(dayrecordBeanList)) {
                        declareEntityList = dayrecordBeanList;
                        declareEntityAdapter.setmData(declareEntityList);
                        tvStatistics.setText("今日申报" + declareEntityList.size() + "次" + ",共计" + "分");
                        tvStatistics.setTextColor(mContext.getResources().getColor(R.color.black));
                        llStatistics.setVisibility(View.VISIBLE);

                        if (state == 1001 || state == -1) {
                            llDeclare.setVisibility(View.VISIBLE);
                            tvDeclare.setText("继续申报");
                        }
                    } else {
                        if (TimeUtil.compareDate(day + " 00:00:00", dayNow + " 00:00:00") > -8) {
                            declareEntityList = new ArrayList<>();
                            declareEntityAdapter.setmData(declareEntityList);
                            tvStatistics.setText("今日申报0次共计0分");
                            tvStatistics.setTextColor(mContext.getResources().getColor(R.color.black));
                            llStatistics.setVisibility(View.VISIBLE);
                            if (state == 1001 || state == -1) {
                                llDeclare.setVisibility(View.VISIBLE);
                                tvDeclare.setText("现在申报");
                            }
                        } else {
                            declareEntityList = new ArrayList<>();
                            declareEntityAdapter.setmData(declareEntityList);
                            tvStatistics.setText("超过7日未申报共计0分");
                            tvStatistics.setTextColor(mContext.getResources().getColor(R.color.red));
                            llStatistics.setVisibility(View.VISIBLE);
                            llDeclare.setVisibility(View.GONE);
                        }
                    }
                    if (state == 100) {
                        tvStatistics.setText("今日在休假状态");
                        llDeclare.setVisibility(View.GONE);

                    }
                    httpSize++;
                    if (httpSize >= httpTotalSize) {
                        hideLoading();
                    }
                } else {
                    tvStatistics.setText("今日申报0次共计0分");
                    tvStatistics.setTextColor(mContext.getResources().getColor(R.color.black));
                    llStatistics.setVisibility(View.VISIBLE);
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                tvStatistics.setText("今日申报0次共计0分");
                llStatistics.setVisibility(View.GONE);
                declareEntityList.clear();
                declareEntityAdapter.setmData(declareEntityList);
                L.d("Error");
                showError("");
            }
        }));


    }

    @OnClick({R.id.tv_before, R.id.tv_next, R.id.lin_chose_date, R.id.tv_declare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_chose_date:
                SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String time = TimeUtil.getDateToYMD(date);
                        tvInfoDate.setText(time);
                        appholidayWtime(tvInfoDate.getText().toString());

                        declareEntityList.clear();
                        declareEntityAdapter.setmData(declareEntityList);
                    }
                });
                break;

            case R.id.tv_before:
                next();
                break;
            case R.id.tv_next:
                before();
                break;

            case R.id.tv_declare:
                Intent intent1 = new Intent(mContext, TimeDeclareActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", tvInfoDate.getText().toString());
                bundle.putParcelable("data", declareEntity);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }
}
