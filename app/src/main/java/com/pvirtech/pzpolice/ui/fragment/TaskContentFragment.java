package com.pvirtech.pzpolice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/3/26.
 * 我的任务-任务内容
 */

public class TaskContentFragment extends BaseFragment {


    Unbinder unbinder;

    @BindView(R.id.tv_fingerprint_info)
    TextView tvFingerprintInfo;
    @BindView(R.id.ll_fingerprint_info)
    LinearLayout llFingerprintInfo;
    @BindView(R.id.tv_completion_situation)
    TextView tvCompletionSituation;
    @BindView(R.id.tv_commit_time)
    TextView tvCommitTime;
    @BindView(R.id.ll_commit_time)
    LinearLayout llCommitTime;
    @BindView(R.id.tv_evaluation_method)
    TextView tvEvaluationMethod;
    @BindView(R.id.tv_task_type)
    TextView tvTaskType;
    @BindView(R.id.ll_task_type)
    LinearLayout llTaskType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.client)
    TextView client;
    @BindView(R.id.ll_client)
    LinearLayout llClient;
    @BindView(R.id.ll_client_list)
    LinearLayout llClientList;
    @BindView(R.id.tv_mission_content)
    TextView tvMissionContent;
    @BindView(R.id.ll_mission_content)
    LinearLayout llMissionContent;
    @BindView(R.id.tv_report_information)
    TextView tvReportInformation;
    @BindView(R.id.tv_main_police)
    TextView tvMainPolice;
    @BindView(R.id.tv_co_police)
    TextView tvCoPolice;
    @BindView(R.id.ll_co_police)
    LinearLayout llCoPolice;
    @BindView(R.id.tv_auxiliary_police)
    TextView tvAuxiliaryPolice;
    @BindView(R.id.ll_auxiliary_police)
    LinearLayout llAuxiliaryPolice;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    @BindView(R.id.et_alarm_remarks)
    EditText etAlarmRemarks;
    @BindView(R.id.tv_enclosure)
    TextView tvEnclosure;
    @BindView(R.id.ll_enclosure)
    LinearLayout llEnclosure;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_content, container, false);
        mContext = getActivity();
        initView(view);
        unbinder = ButterKnife.bind(this, view);
        tvCommitTime.setText(TimeUtil.getYMDHM());
        tvTime.setText(TimeUtil.getYMDHM());
        return view;

    }
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
    @OnClick({R.id.ll_commit_time,R.id.ll_time})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_commit_time:
                sublimePickerFragmentUtils.show(false, true, true, getFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String startTime = TimeUtil.getDateToYMD(date);
                        tvCommitTime.setText(startTime);
                    }
                });
                break;
            case R.id.ll_time:
                sublimePickerFragmentUtils.show(false, true, true, getFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String startTime = TimeUtil.getDateToYMD(date);
                        tvTime.setText(startTime);
                    }
                });
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
