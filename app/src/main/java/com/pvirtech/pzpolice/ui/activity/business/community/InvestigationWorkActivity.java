package com.pvirtech.pzpolice.ui.activity.business.community;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.InvestigationWorkPointEntity;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.adapter.InvestigationWorkPointAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 业务考核-基础工作-经侦工作
 */
public class InvestigationWorkActivity extends BaseActivity {
    InvestigationWorkPointAdapter investigationWorkPointAdapter;

    @BindView(R.id.tv_investigation_work_case_type)
    TextView tvInvestigationWorkCaseType;
    @BindView(R.id.tv_investigation_work_time)
    TextView tvInvestigationWorkTime;
    @BindView(R.id.ll_investigation_work_time)
    LinearLayout llInvestigationWorkTime;
    @BindView(R.id.tv_investigation_work_chose_name)
    TextView tvInvestigationWorkChoseName;
    @BindView(R.id.tv_investigation_work_place)
    TextView tvInvestigationWorkPlace;
    @BindView(R.id.tv_investigation_work_work_content)
    TextView tvInvestigationWorkWorkContent;
    @BindView(R.id.tv_investigation_work_montant)
    TextView tvInvestigationWorkMontant;
    @BindView(R.id.tv_investigation_work_police)
    TextView tvInvestigationWorkPolice;
    @BindView(R.id.tv_investigation_work_co_police)
    TextView tvInvestigationWorkCoPolice;
    @BindView(R.id.tv_investigation_work_auxiliary_police)
    TextView tvInvestigationWorkAuxiliaryPolice;
    @BindView(R.id.tv_investigation_work_point)
    TextView tvInvestigationWorkPoint;
    @BindView(R.id.investigation_work_recyclerview)
    RecyclerView investigationWorkRecyclerview;
    @BindView(R.id.tv_investigation_work_upload_attachments)
    TextView tvInvestigationWorkUploadAttachments;
    @BindView(R.id.bt_investigation_work_submit)
    CircularProgressButton btInvestigationWorkSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private List<InvestigationWorkPointEntity> mDataLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_work);
        ButterKnife.bind(this);
        initTitleView("经侦工作");
        mContext = InvestigationWorkActivity.this;
        TAG = "InvestigationWorkActivity";
//        initsSecurityCase();
        initsInvestigationWorkPoint();
//        securityCaseAdapter = new SecurityCaseAdapter(mContext, mDataLists1, new SecurityCaseAdapter.OnRecyclerViewListener() {
//            @Override
//            public void onItemClick(int position) {
//                for (SecurityCaseEntity SecurityCaseEntity :
//                        mDataLists1) {
//                    SecurityCaseEntity.setChecked(false);
//                }
//                mDataLists1.get(position).setChecked(true);
//                securityCaseAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public boolean onItemLongClick(int position) {
//                return false;
//            }
//        });
//        securityCaseRecycleview.setAdapter(securityCaseAdapter);
//        securityCaseRecycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        securityCaseRecycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
//        securityCaseRecycleview.setLayoutManager(new GridLayoutManager(mContext, 3));

        investigationWorkPointAdapter = new InvestigationWorkPointAdapter(mContext, mDataLists, new InvestigationWorkPointAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                for (InvestigationWorkPointEntity InvestigationWorkPointEntity :
                        mDataLists) {
                    InvestigationWorkPointEntity.setChecked(false);
                }
                mDataLists.get(position).setChecked(true);
                investigationWorkPointAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        investigationWorkRecyclerview.setAdapter(investigationWorkPointAdapter);
        investigationWorkRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        investigationWorkRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        investigationWorkRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));

        tvInvestigationWorkTime.setText(TimeUtil.getYMD());
    }

    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();

    @OnClick({R.id.ll_investigation_work_time})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_investigation_work_time:
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String startTime = TimeUtil.getDateToYMD(date);
                        tvInvestigationWorkTime.setText(startTime);
                    }
                });


                break;
        }
    }


    private void initsInvestigationWorkPoint() {
        InvestigationWorkPointEntity police1 = new InvestigationWorkPointEntity("李警官", "10分");
        mDataLists.add(police1);
        InvestigationWorkPointEntity police2 = new InvestigationWorkPointEntity("王警官", "10分");
        mDataLists.add(police2);
        InvestigationWorkPointEntity police3 = new InvestigationWorkPointEntity("张警官", "10分");
        mDataLists.add(police3);
    }

}
