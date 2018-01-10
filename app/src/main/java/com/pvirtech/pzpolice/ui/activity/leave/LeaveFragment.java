package com.pvirtech.pzpolice.ui.activity.leave;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveEntity;
import com.pvirtech.pzpolice.enumeration.LeaveType;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.RefreshEvent;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.adapter.LeaveTypeAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.DictionariesEntity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/26.
 * 请假主界面
 */

public class LeaveFragment extends BaseFragment {
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
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_reason)
    LinearLayout llReason;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.root_view)
    LinearLayout rootView;

    int httpTotalSize = 2;
    int httpSize = 0;

    /**
     * 我的假期数据
     */
    ArrayList<LeaveEntity> mData;
    LeaveEntity mLeaveEntity;
    String leaveType = "";
    LeaveTypeAdapter adapter;
    List<DictionariesEntity> listReason = new ArrayList<>();
    String leaveReason;
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
    double SurplusDay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_leave, container, false);
        initInfoTitleView(view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        /**
         * 初始化adapter
         */
        mData = new ArrayList<>();
        mData.clear();
        for (LeaveType leaveType : LeaveType.values()) {
            LeaveEntity temp = leaveType.getValue();
            temp.setChecked(false);
            mData.add(temp);
        }
        mLeaveEntity = mData.get(0);
        adapter = new LeaveTypeAdapter(mContext, mData, new LeaveTypeAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                for (LeaveEntity leaveEntity : mData) {
                    leaveEntity.setChecked(false);
                }

                LeaveEntity leaveEntity = mData.get(position);
                if (leaveEntity.getType().equals(LeaveType.COMPASSIONATE_LEAVE.getValue().getType
                        ()) || leaveEntity.getType().equals
                        (LeaveType.OFF_LEAVE.getValue().getType())) {
                    leaveEntity.setChecked(true);
                    mLeaveEntity = leaveEntity;
                    leaveType = leaveEntity.getType();
                    if (leaveEntity.getType().equals(LeaveType.COMPASSIONATE_LEAVE.getValue()
                            .getType()) || leaveEntity.getType().equals
                            (LeaveType
                                    .OFF_LEAVE.getValue().getType())) {
                        tvReason.setText("");
                    } else {
                        tvReason.setText(leaveEntity.getName());
                    }
                    adapter.notifyDataSetChanged();
                } else if (leaveEntity.getDay() > 0) {
                    leaveEntity.setChecked(true);
                    mLeaveEntity = leaveEntity;
                    leaveType = leaveEntity.getType();
                    if (leaveEntity.getType().equals(LeaveType.COMPASSIONATE_LEAVE.getValue()
                            .getType()) || leaveEntity.getType().equals
                            (LeaveType
                                    .OFF_LEAVE.getValue().getType())) {
                        tvReason.setText("");
                    } else {
                        tvReason.setText(leaveEntity.getName());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    if (mLeaveEntity.getTotal() > 0) {
                        Toasty.warning(mContext, "对不起，您的" + leaveEntity.getName() + "已休完！").show();
                    } else {
                        Toasty.warning(mContext, "对不起，您没有" + leaveEntity.getName() + "可休！").show();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 3));
        mData.get(0).setChecked(true);


        /**
         * 初始化控件的文字
         */
        tvStatrtTime.setText(TimeUtil.getYMD());
        tvEndTime.setText(TimeUtil.getYMD());
        /**
         * 初始化请假天数
         */
        mLeaveEntity = mData.get(0);
        mLeaveEntity.setChecked(true);
        leaveType = mLeaveEntity.getType();
        leaveReason = mLeaveEntity.getType();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.ll_total_time, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_reason, R.id
            .submit})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_total_time:
                sublimePickerFragmentUtils.show(true, true, false, getFragmentManager(), new
                        SublimePickerFragment.Callback() {
                            @Override
                            public void onCancelled() {
                                System.out.println("");
                            }

                            @Override
                            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay,
                                                                int minute, SublimeRecurrencePicker
                                                                        .RecurrenceOption recurrenceOption, String recurrenceRule) {
                                Date date = selectedDate.getFirstDate().getTime();
                                String startTime = TimeUtil.getDateToYMD(date);
                                tvStatrtTime.setText(startTime);

                                Date dateEnd = selectedDate.getSecondDate().getTime();
                                String endTime = TimeUtil.getDateToYMD(dateEnd);
                                tvEndTime.setText(endTime);

                                String totalTime = OperationUtils.getDays(startTime, endTime);
                                tvTotalTime.setText(totalTime);
                            }
                        });


                break;
            case R.id.ll_start_time:
                sublimePickerFragmentUtils.show(false, true, false, getFragmentManager(), new
                        SublimePickerFragment.Callback() {
                            @Override
                            public void onCancelled() {
                                System.out.println("");
                            }

                            @Override
                            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay,
                                                                int minute, SublimeRecurrencePicker
                                                                        .RecurrenceOption recurrenceOption, String recurrenceRule) {
                                Date date = selectedDate.getFirstDate().getTime();
                                String time = TimeUtil.getDateToYMD(date);
                                tvStatrtTime.setText(time);
                                String totalTime = OperationUtils.getDays(tvStatrtTime.getText().toString
                                        (), tvEndTime.getText().toString());
                                tvTotalTime.setText(totalTime);
                            }
                        });


                break;
            case R.id.ll_end_time:
                sublimePickerFragmentUtils.show(false, true, false, getFragmentManager(), new
                        SublimePickerFragment.Callback() {
                            @Override
                            public void onCancelled() {
                                System.out.println("");
                            }

                            @Override
                            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay,
                                                                int minute, SublimeRecurrencePicker
                                                                        .RecurrenceOption recurrenceOption, String recurrenceRule) {
                                Date date = selectedDate.getFirstDate().getTime();
                                String time = TimeUtil.getDateToYMD(date);
                                tvEndTime.setText(time);
                                String totalTime = OperationUtils.getDays(tvStatrtTime.getText().toString
                                        (), tvEndTime.getText().toString());
                                tvTotalTime.setText(totalTime);
                            }
                        });
                break;


            case R.id.ll_reason:

                DictionariesDialog dialog = new DictionariesDialog();
                dialog.showDialog(mContext, "", tvReason.getText().toString(), listReason, new
                        OnItemSelectedListener() {
                            @Override
                            public void onSelected(DictionariesEntity dictionariesEntity) {
                                leaveReason = dictionariesEntity.getID();
                                tvReason.setText(dictionariesEntity.getNAME());
                            }
                        });
                break;
            case R.id.submit:

                final String strLeaveStartTime = tvStatrtTime.getText().toString();
                final String strLeaveEndTime = tvEndTime.getText().toString();
                final String leaveTotalTime = tvTotalTime.getText().toString();
                if (!Constant.SUCCESS.equals(OperationUtils.compareTime(strLeaveStartTime,
                        strLeaveEndTime))) {
                    Toasty.warning(mContext, OperationUtils.compareTime(strLeaveStartTime,
                            strLeaveEndTime)).show();
                    return;
                }
                if (TextUtils.isEmpty(tvReason.getText().toString())) {
                    Toasty.warning(mContext, "请选择休假事由!").show();
                    return;
                }
                if (mLeaveEntity.getType().equals(LeaveType.OFF_LEAVE.getValue().getType()) ||
                        mLeaveEntity.getType().equals(LeaveType
                                .COMPASSIONATE_LEAVE.getValue().getType())) {
                    String content = "你将于" + strLeaveStartTime + "到" + strLeaveEndTime + "休" + mLeaveEntity.getName() + leaveTotalTime + "天" +
                            "，是否确认提交？";
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                            .setContentText(content).setConfirmText("确定")
                            .setCancelText
                                    ("取消").setConfirmClickListener(new SweetAlertDialog
                            .OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            submit(leaveType, leaveTotalTime, tvReason.getText().toString(),
                                    strLeaveStartTime, strLeaveEndTime,
                                    SurplusDay);
                        }
                    }).show();

                } else {
                    int intleaveTotalTime = 0;
                    try {//leaveTotalTime 为0的时候崩溃
                        intleaveTotalTime = Integer.valueOf(leaveTotalTime);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (mLeaveEntity.getDay() - intleaveTotalTime >= 0) {
                        SurplusDay = mLeaveEntity.getDay() - Double.valueOf(leaveTotalTime);
                        String content = "你将于" + strLeaveStartTime + "到" +
                                strLeaveEndTime + "休" + mLeaveEntity.getName() + leaveTotalTime +
                                "天，本次请假后，" + mLeaveEntity.getName() +
                                "还剩余" +
                                SurplusDay + "天，是否确认提交？";
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                .setContentText(content).setConfirmText("确定").setCancelText
                                ("取消").setConfirmClickListener(new SweetAlertDialog
                                .OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                submit(leaveType, leaveTotalTime, tvReason.getText().toString(),
                                        strLeaveStartTime, strLeaveEndTime,
                                        SurplusDay);
                            }
                        }).show();
                    } else {
                        Toasty.warning(mContext, "你选择的休假天数大于" + mLeaveEntity.getDay() + "天," +
                                "请重新选择！").show();
                        return;
                    }
                }


                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        showLoading("");
        getReason();
        getdays();

    }

    /**
     * 取得请假原因
     */
    private void getReason() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayReason(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<DictionariesEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<DictionariesEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    listReason = response.getData();
                    httpSize++;
                    if (httpSize == httpTotalSize) {
                        hideLoading();
                    }
                    if (!ListUtils.isEmpty(listReason)) {
                        leaveReason = listReason.get(0).getID();
                        tvReason.setText(listReason.get(0).getNAME());
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

    /**
     * 取得我可以请假的剩余天数
     */
    private void getdays() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidaDay(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<LeaveEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<LeaveEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    httpSize++;
                    List<LeaveEntity> daysLeaveEntity = response.getData();
                    if (httpSize == httpTotalSize) {
                        hideLoading();
                    }
                    if (ListUtils.isEmpty(daysLeaveEntity)) {
                        daysLeaveEntity = new ArrayList<>();
                    }
                    dealDays(daysLeaveEntity);
                    adapter.setmData(mData);
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

    /**
     * 处理请假的天数
     *
     * @param leaveEntities
     */
    private void dealDays(List<LeaveEntity> leaveEntities) {

        for (LeaveEntity leaveEntity : leaveEntities) {
            for (LeaveEntity leaveEntity2 : mData) {
                if (leaveEntity.getType().equals(leaveEntity2.getType())) {
                    leaveEntity2.setDay(leaveEntity.getDay());
                    leaveEntity2.setTotal(leaveEntity.getTotal());
                    break;
                }
            }
        }
        //当未安排婚假/产假/陪产假假期时，不应展示这三类假期
        List<LeaveEntity> removeList = new ArrayList<>();
        for (LeaveEntity leaveEntity : mData) {
            if (leaveEntity.getType().equals(LeaveType.MATERNITY_LEAVE.getValue().getType()) &&
                    leaveEntity.getDay() <= 0) {
                removeList.add(leaveEntity);
            } else if (leaveEntity.getType().equals(LeaveType.PATERNITY_LEAVE.getValue().getType
                    ()) && leaveEntity.getDay() <= 0) {
                removeList.add(leaveEntity);
            } else if (leaveEntity.getType().equals(LeaveType.MARRIAGE.getValue().getType()) &&
                    leaveEntity.getDay() <= 0) {
                removeList.add(leaveEntity);
            }
        }
        mData.removeAll(removeList);
        /**
         * 设置请假默认选择的第一个选项
         */
        for (LeaveEntity leaveEntity2 : mData) {
            leaveEntity2.setChecked(false);
            break;
        }
        for (LeaveEntity leaveEntity2 : mData) {
            if (leaveEntity2.getDay() > 0) {
                leaveEntity2.setChecked(true);
                leaveType = leaveEntity2.getType();
                mLeaveEntity = leaveEntity2;
                break;
            }
        }
    }

    /**
     * 发出请假的申请
     *
     * @param type
     * @param day
     * @param reason
     * @param startTime
     * @param endTime
     */
    public void submit(String type, String day, String reason, String startTime, String endTime,
                       final double SurplusDay) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("type", type);
        data.addProperty("day", day);
        data.addProperty("reason", reason);
        data.addProperty("startTime", startTime);
        data.addProperty("endTime", endTime);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayApply(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);
                    mLeaveEntity.setDay(SurplusDay);
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new RefreshEvent("LeaveActivity"));
                } else {
                    Toasty.error(mContext, response.getResultMessage()).show();
                    submit.setProgress(0);
                }
                L.d("sucess");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)
                ).show();
                submit.setProgress(0);
            }
        }));

    }


}
