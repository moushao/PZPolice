package com.pvirtech.pzpolice.ui.activity.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.DeclareEntity;
import com.pvirtech.pzpolice.entity.WorkTypeEntity;
import com.pvirtech.pzpolice.enumeration.WokType;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.activity.work.SelectPersonActivity;
import com.pvirtech.pzpolice.ui.adapter.TimeDeclareAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.pvirtech.pzpolice.utils.TimeUtil.getYMD;


/**
 * 时间申报 主界面
 */
public class TimeDeclareActivity extends BaseActivity {
    DeclareEntity declareEntity;
    List<DeclareEntity.DAYRECORDBean> declareEntityList = new ArrayList<>();
    ArrayList<WorkTypeEntity> mData;
    TimeDeclareAdapter timeDeclareAdapter;
    WorkTypeEntity mWorkTypeEntity;
    String[] mWorkTypeEntityTime;
    String workType;
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();

    String date;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.ll_total_time)
    LinearLayout llTotalTime;
    @BindView(R.id.tv_statrt_time)
    TextView tvStatrtTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_person_notice)
    TextView tvPersonNotice;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.tv_statrt_hour)
    TextView tvStatrtHour;
    @BindView(R.id.tv_end_hour)
    TextView tvEndHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        ButterKnife.bind(this);
        initInfoTitleView("时间申报");
        mContext = TimeDeclareActivity.this;
        TAG = "NeedToeDealActivity";
        initView();

    }

    private void initView() {
        /**
         * 如果是普通用户，屏蔽
         */
        if (OperationUtils.getRoleLeader()) {
            llPerson.setVisibility(View.VISIBLE);
        } else {
            llPerson.setVisibility(View.GONE);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        declareEntity = bundle.getParcelable("data");
        declareEntityList = declareEntity.getDAYRECORD();
        date = getIntent().getStringExtra("date");
        if (TextUtils.isEmpty(date)) {
            date = getYMD();
        }


        tvInfoDate.setText(date);

        mData = new ArrayList<>();
        for (WokType wokType : WokType.values()) {
            WorkTypeEntity temp = wokType.getValue();
            temp.setChecked(false);
            mData.add(temp);
        }
        mWorkTypeEntity = mData.get(0);
        mWorkTypeEntityTime = mWorkTypeEntity.getTime().split("-");
        mWorkTypeEntity.setChecked(true);
        workType = mWorkTypeEntity.getType();


        timeDeclareAdapter = new TimeDeclareAdapter(mContext, mData, new TimeDeclareAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                for (WorkTypeEntity workTypeEntity : mData) {
                    workTypeEntity.setChecked(false);
                }
                mData.get(position).setChecked(true);
                mWorkTypeEntity = mData.get(position);
                workType = mWorkTypeEntity.getType();
                timeDeclareAdapter.notifyDataSetChanged();
                mWorkTypeEntityTime = mWorkTypeEntity.getTime().split("-");
                tvStatrtHour.setText(mWorkTypeEntityTime[0]);
                tvEndHour.setText(mWorkTypeEntityTime[1]);

                tvStatrtTime.setText(date + " ");
                tvEndTime.setText(date + " ");
                if (mWorkTypeEntity.getType().equals(WokType.DUTY.getValue().getType())) {
                    tvEndTime.setText(getNextYMD(date) + " ");
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(timeDeclareAdapter);
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 2));
        /**
         * 初始化控件的文字
         */
        tvStatrtTime.setText(date + " ");
        tvEndTime.setText(date + " ");
        tvStatrtHour.setText(mWorkTypeEntityTime[0]);
        tvEndHour.setText(mWorkTypeEntityTime[1]);
    }

    public String getNextYMD(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long time = null;
        try {
            time = format.parse(date).getTime() + 24 * 60 * 60 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(new Date(time));
    }

    @OnClick({R.id.ll_total_time, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_person, R.id.submit, R.id
            .tv_statrt_hour, R.id.tv_end_hour})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_total_time:
                sublimePickerFragmentUtils.show(true, true, false, getSupportFragmentManager(), new 
                        SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, 
                                                        SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String startTime = TimeUtil.getDateToYMD(date);
                        tvStatrtTime.setText(startTime + " ");

                        Date dateEnd = selectedDate.getSecondDate().getTime();
                        String endTime = TimeUtil.getDateToYMD(dateEnd);
                        tvEndTime.setText(endTime + " ");

                        setTotalTime();
                    }
                });
                break;
            case R.id.ll_start_time:
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(), new 
                        SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, 
                                                        SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String time = TimeUtil.getDateToYMD(date);
                        tvStatrtTime.setText(time + " ");
                        setTotalTime();
                    }
                });


                break;
            case R.id.ll_end_time:
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(), new 
                        SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, 
                                                        SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String time = TimeUtil.getDateToYMD(date);
                        tvEndTime.setText(time + " ");
                        setTotalTime();
                    }
                });
                break;

            case R.id.tv_statrt_hour:
                sublimePickerFragmentUtils.show(false, false, true, getSupportFragmentManager(), new 
                        SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, 
                                                        SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        tvStatrtHour.setText(dealTime(hourOfDay) + ":" + dealTime(minute));
                        setTotalTime();

                    }
                });
                break;

            case R.id.tv_end_hour:
                sublimePickerFragmentUtils.show(false, false, true, getSupportFragmentManager(), new 
                        SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, 
                                                        SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        tvEndHour.setText(dealTime(hourOfDay) + ":" + dealTime(minute));
                        setTotalTime();
                    }
                });
                break;
            case R.id.ll_person:
                Intent intent = new Intent(mContext, SelectPersonActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.submit:
                final String strLeaveStartTime = tvStatrtTime.getText().toString() + tvStatrtHour.getText().toString();
                final String strLeaveEndTime = tvEndTime.getText().toString() + tvEndHour.getText().toString();
                final String leaveTotalTime = tvTotalTime.getText().toString();
                String s = tvTotalTime.getText().toString();
                if (TextUtils.isEmpty(tvTotalTime.getText().toString())) {
                    Toasty.warning(mContext, "开始时间大于结束时间，请重新选择时间").show();
                    return;
                }


                if (TimeUtil.compareDate(tvStatrtTime.getText().toString() + "00:00:00", getYMD() + " 00:00:00") <= 
                        -8) {
                    Toasty.warning(mContext, "开始时间不能在7天之前").show();
                    return;
                }
                if (TimeUtil.compareDate(tvEndTime.getText().toString() + "00:00:00", getYMD() + " 00:00:00") <= -8) {
                    Toasty.warning(mContext, "结束时间不能在7天之前").show();
                    return;
                }

                String strUserList = "";
                if (OperationUtils.getRoleLeader()) {//如果是领导
                    for (Group.GroupUSERBean groupUSERBean : list) {
                        strUserList += groupUSERBean.getUser_ID() + ",";
                    }
                    if (!TextUtils.isEmpty(strUserList)) {
                        strUserList = strUserList.substring(0, strUserList.length() - 1);
                    } else {
                        Toasty.warning(mContext, "请选择人员").show();
                        return;
                    }
                } else {
                    strUserList = AppValue.getInstance().getmUserInfo().getUSER_ID();
                }
                final String strUserListData = strUserList;
                /**
                 * 不能申报同一类型的时间
                 */
                if (!ListUtils.isEmpty(declareEntityList)) {
                    boolean blnIsReturn = false;
                    for (DeclareEntity.DAYRECORDBean dayrecordBean : declareEntityList) {
                        if (mWorkTypeEntity.getTypeName().equals(WokType.WORK.getValue().getTypeName()) || 
                                mWorkTypeEntity.getTypeName().equals
                                (WokType.DUTY.getValue().getTypeName())) {
                            if (TimeUtil.compareSection(dayrecordBean.getStartTime(), dayrecordBean.getEndTime(), 
                                    strLeaveStartTime)) {
                                Toasty.warning(mContext, "您申报的" + mWorkTypeEntity.getTypeName() + "开始时间有重叠").show();
                                blnIsReturn = true;
                                break;
                            } else if (TimeUtil.compareSection(dayrecordBean.getStartTime(), dayrecordBean.getEndTime
                                    (), strLeaveEndTime)) {
                                Toasty.warning(mContext, "您申报的" + mWorkTypeEntity.getTypeName() + "结束时间有重叠").show();
                                blnIsReturn = true;
                                break;
                            }
                            if (TimeUtil.compareSection(strLeaveStartTime, strLeaveEndTime, dayrecordBean
                                    .getStartTime())) {
                                Toasty.warning(mContext, "您申报的" + mWorkTypeEntity.getTypeName() + "时间内已经有申报").show();
                                blnIsReturn = true;
                                break;
                            }
                            if (TimeUtil.compareSection(strLeaveStartTime, strLeaveEndTime, dayrecordBean.getEndTime
                                    ())) {
                                Toasty.warning(mContext, "您申报的" + mWorkTypeEntity.getTypeName() + "时间内已经有申报").show();
                                blnIsReturn = true;
                                break;
                            }
                        } else if (mWorkTypeEntity.getTypeName().equals(dayrecordBean.getType())) {
                            Toasty.warning(mContext, "今日已申报过" + mWorkTypeEntity.getTypeName() + "不能重复申报").show();
                            blnIsReturn = true;
                            break;
                        }
                    }
                    if (blnIsReturn) {
                        return;
                    }
                }

                if (mWorkTypeEntity.getType().equals(WokType.WORK.getValue().getType())) {//如果是上班，只能选择一天
                    if (!tvStatrtTime.getText().toString().equals(tvEndTime.getText().toString())) {
                        Toasty.warning(mContext, "上班时间只能选择一天！").show();
                        return;
                    }
                }

                String notice = tvPerson.getText().toString();
                notice = OperationUtils.getInterceptString(notice);

                String content = "";
                if (OperationUtils.getRoleLeader()) {
                    content = "为";
                }
                content = content + notice + "申报" + mWorkTypeEntity.getTypeName() + tvTotalTime.getText().toString() +
                        "，确认提交？";
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE).setContentText(content).setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                submit(workType, strLeaveStartTime, strLeaveEndTime, strUserListData);
                            }
                        }).show();
                break;
        }
    }


    private void submit(String workType, String beginTime, String endTime, String userList) {
        if (submit.getProgress() != 0) {
            return;
        }
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("workType", workType);
        data.addProperty("beginTime", beginTime);
        data.addProperty("endTime", endTime);
        data.addProperty("userList", userList);

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        /**
         * 网路访问登录
         */

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().declare(httpSubmit).subscribeOn(Schedulers.io())
                .observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(100);
                    finish();
                } else {
                    Toasty.warning(mContext, response.getResultMessage()).show();
                    submit.setProgress(0);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("失败");
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)).show();
                submit.setProgress(0);
            }
        }));


    }


    private String dealTime(int time) {
        String date = "";
        if (time < 10) {
            return "0" + String.valueOf(time);
        } else {
            return String.valueOf(time);
        }
    }

    private void setTotalTime() {
        String totalTime = OperationUtils.getIntervalTime(tvStatrtTime.getText().toString() + tvStatrtHour.getText()
                .toString(), tvEndTime.getText
                ().toString() + tvEndHour.getText().toString());
        tvTotalTime.setText(totalTime);
    }


    ArrayList<Group.GroupUSERBean> list = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_SELECT_PERSON) {
            list = data.getParcelableArrayListExtra(Constant.PERSON_SELECTED);
            String strSelectedPersons = "";
            for (Group.GroupUSERBean groupUSERBean : list) {
                strSelectedPersons = strSelectedPersons + groupUSERBean.getName() + ",";
            }
            if (!TextUtils.isEmpty(strSelectedPersons)) {
                tvPersonNotice.setText("");
                strSelectedPersons = strSelectedPersons.substring(0, strSelectedPersons.length() - 1);
            } else {
                tvPersonNotice.setText(mContext.getResources().getString(R.string.please_fill_in));
            }
            tvPersonNotice.setText("(" + list.size() + ")人");
            tvPerson.setText(strSelectedPersons);
        }
    }
}
