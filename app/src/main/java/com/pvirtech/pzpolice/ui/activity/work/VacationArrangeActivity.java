package com.pvirtech.pzpolice.ui.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveEntity;
import com.pvirtech.pzpolice.enumeration.VacationType;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.adapter.VacationArrangeAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.DictionariesEntity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


public class VacationArrangeActivity extends BaseActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_total_time)
    EditText tvTotalTime;
    @BindView(R.id.ll_total_time)
    LinearLayout llTotalTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_reason)
    LinearLayout llReason;
    @BindView(R.id.tv_person_notice)
    TextView tvPersonNotice;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;

    VacationArrangeAdapter adapter;
    ArrayList<LeaveEntity> mData;
    String leaveType = "";
    String leaveReason;
    List<DictionariesEntity> listReason = new ArrayList<>();
    LeaveEntity leaveEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_arrange);
        ButterKnife.bind(this);
        initInfoTitleView(getString(R.string.vacation_arrange));
        mContext = VacationArrangeActivity.this;
        TAG = "VacationArrangeActivity";
        initView();
        initData();
    }

    private void initView() {
        /**
         * 设置默认天数
         */
        llTime.setVisibility(View.GONE);
        tvTime.setText(TimeUtil.getYMD());
        mData = new ArrayList<>();
        for (VacationType vacationType : VacationType.values()) {
            LeaveEntity temp = vacationType.getValue();
            temp.setChecked(false);
            mData.add(temp);
        }
        leaveEntity = mData.get(0);
        leaveEntity.setChecked(true);
        leaveReason = leaveEntity.getType();
        leaveType = leaveEntity.getType();
        tvReason.setText(leaveEntity.getName());
        tvTotalTime.setText(dealDay(leaveEntity.getDay()));
        adapter = new VacationArrangeAdapter(mContext, mData, new VacationArrangeAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                for (LeaveEntity leaveEntity : mData) {
                    leaveEntity.setChecked(false);
                }
                leaveEntity = mData.get(position);
                leaveEntity.setChecked(true);
                leaveType = mData.get(position).getType();
                adapter.notifyDataSetChanged();
                if (leaveEntity.getType().equals(VacationType.MAKE_FALSE.getValue().getType())) {
                    llTime.setVisibility(View.VISIBLE);
                } else {
                    llTime.setVisibility(View.GONE);
                }
                /**
                 * 选择不同的假期给休假天数赋值
                 */
                if (leaveEntity.getType().equals(VacationType.MAKE_FALSE.getValue().getType())) {
                    tvReason.setText("");
                } else {
                    tvReason.setText(leaveEntity.getName());
                }
                tvTotalTime.setText(dealDay(leaveEntity.getDay()));

                leaveReason = leaveEntity.getType();
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

    }


    private void initData() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayReason(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new io.reactivex.functions.Consumer<HttpResult<List<DictionariesEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<DictionariesEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    listReason = response.getData();
                    hideLoading();
                    if (!ListUtils.isEmpty(listReason)) {
//                        leaveReason = listReason.get(0).getID();
//                        tvReason.setText(listReason.get(0).getNAME());
                    }
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new io.reactivex.functions.Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showError("");
            }
        }));


    }

    public void submit(String type, String day, String reason, String time, String userList) {
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
        data.addProperty("reason", reason);
        data.addProperty("userList", userList);
        data.addProperty("time", time);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayPlan(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new io.reactivex.functions.Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);
                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setContentText("假期安排成功，是否继续安排假期？")
                            .setConfirmText("确定").setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    }).show();
                } else {
                    Toasty.error(mContext, response.getResultMessage()).show();
                    submit.setProgress(0);
                }
                L.d("sucess");
            }
        }, new io.reactivex.functions.Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)
                ).show();
                submit.setProgress(0);
            }
        }));


    }

    @OnClick({R.id.ll_time, R.id.ll_reason, R.id.ll_person, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_time:
                SublimePickerFragmentUtils sublimePickerFragmentUtils = new
                        SublimePickerFragmentUtils();
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(),
                        new SublimePickerFragment.Callback() {
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
                                tvTime.setText(time);
                                System.out.println("");
                            }
                        });
                break;
            case R.id.ll_reason:
                if (ListUtils.isEmpty(listReason)) {
                    initData();
                    return;
                }
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
            case R.id.ll_person:
                Intent intent = new Intent(mContext, SelectPersonActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.submit:
                String strUserList = "";
                for (Group.GroupUSERBean groupUSERBean : list) {
                    strUserList += "" + groupUSERBean.getUser_ID() + ",";
                }
                if (TextUtils.isEmpty(tvReason.getText().toString())) {
                    Toasty.warning(mContext, "请选择休假事由!").show();
                    return;
                }
                if (!TextUtils.isEmpty(strUserList)) {
                    strUserList = strUserList.substring(0, strUserList.length() - 1);
                } else {
                    Toasty.warning(mContext, "请选择人员").show();
                    return;
                }
                final String strUserListData = strUserList;

                String notice = tvPerson.getText().toString();
                notice = OperationUtils.getInterceptString(notice);

                String content = "为" + notice + "安排" + leaveEntity.getName
                        () + tvTotalTime.getText().toString() + "天，确认提交？";
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE).setContentText(content)
                        .setConfirmText("确定").setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                submit(leaveType, tvTotalTime.getText().toString(), leaveReason,
                                        tvTime.getText().toString(), strUserListData);
                            }
                        }).show();

                break;
        }
    }

    private String dealDay(double day) {
        try {
            String data = String.valueOf(day);
            if (data.endsWith(".0")) {
                data = data.substring(0, data.length() - 2);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    ArrayList<Group.GroupUSERBean> list = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_SELECT_PERSON) {
            list = data.getParcelableArrayListExtra(Constant.PERSON_SELECTED);
        } else {
            list.clear();
        }
        String person = "";
        for (Group.GroupUSERBean groupUSERBean : list) {
            person = person + groupUSERBean.getName() + ",";
        }
        if (!TextUtils.isEmpty(person)) {
            person = person.substring(0, person.length() - 1);
        }
        tvPersonNotice.setText("(" + list.size() + ")人");
        tvPerson.setText(person);
    }
}

