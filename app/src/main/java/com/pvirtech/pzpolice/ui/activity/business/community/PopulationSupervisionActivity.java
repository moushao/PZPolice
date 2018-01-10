package com.pvirtech.pzpolice.ui.activity.business.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TaskAndIntegration;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.WorkContent02Enum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseCaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.TimeUtil;
import com.tonypy.tonypy.addressbooks.entity.Group;

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

import static com.pvirtech.pzpolice.R.id.tv_work_content;

/**
 * 业务考核-基础工作-重点人口监改对象
 */
public class PopulationSupervisionActivity extends BaseCaseActivity {


    @BindView(R.id.tv_case_type)
    TextView tvCaseType;
    @BindView(R.id.ll_case_type)
    LinearLayout llCaseType;
    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.ll_task_name)
    LinearLayout llTaskName;
    @BindView(R.id.tv_case_time)
    TextView tvCaseTime;
    @BindView(R.id.ll_case_time)
    LinearLayout llCaseTime;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(tv_work_content)
    TextView tvWorkContent;
    @BindView(R.id.ll_work_content)
    LinearLayout llWorkContent;
    @BindView(R.id.ed_report_information)
    EditText edReportInformation;
    @BindView(R.id.ll_report_information)
    LinearLayout llReportInformation;
    @BindView(R.id.tv_input_persons)
    TextView tvInputPersons;
    @BindView(R.id.ll_input)
    LinearLayout llInput;
    @BindView(R.id.ed_bz)
    EditText edBz;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    List<DictionariesEntity> dictionariesEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        setBaseContentView(R.layout.activity_population_supervision);
        initTitleView(title);
        super.onCreate(savedInstanceState);
        TAG = "PopulationSupervisionActivity";
        initView(caseComeType);
        initCommonView();

    }

    private void initView(int caseComeType) {
        /**
         * 初始化选择数据
         */
        for (WorkContent02Enum myEnum : WorkContent02Enum.values()) {
            dictionariesEntityList.add(myEnum.getValue());
        }


        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            tvCaseType.setText(myTasksEntity.getCASE_NAME());
            tvTaskName.setText(myTasksEntity.getTASK_NAME());
            tvCaseTime.setText(myTasksEntity.getDATE_STR());
            apptaskScoreway();
        } else if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
            tvCaseType.setText(title);
            tvCaseTime.setText(TimeUtil.getYMDHM());
            apptaskTaskTypeList(id);
        }

    }


    @OnClick({R.id.ll_case_type, R.id.ll_case_time, R.id.ll_task_name, R.id.submit, R.id.ll_work_content})
    public void onViewClicked(View view) {
        final DictionariesDialog dictionariesDialog = new DictionariesDialog();
        switch (view.getId()) {

            case R.id.ll_case_time:
                sublimePickerFragmentUtils.show(false, true, true, getSupportFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String startTime = TimeUtil.getDateToYMD(date);
                        String time = dealTime(hourOfDay) + ":" + dealTime(minute);
                        tvCaseTime.setText(startTime + " " + time);
                    }
                });
                break;
            case R.id.ll_task_name:
                if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
                    dictionariesDialog.showTaskTypeDialog(mContext, "", tvTaskName.getText().toString(), taskAndIntegration.getTYPELIST(), new
                            DictionariesDialog
                                    .OnItemSelectedListenerTaskType() {
                                @Override
                                public void onSelected(TaskAndIntegration.TYPELISTBean typelistBean) {
                                    mTypelistBean = typelistBean;
                                    taskTypeID = typelistBean.getID() + "";
                                    tvTaskName.setText(typelistBean.getNAME());
                                    tvCasePoint.setText(typelistBean.getSIGLE_SCORE() + "");
                                    initsSecurityCasePoint(mTypelistBean.getSIGLE_SCORE(), mTypelistBean.getSCORE_WAY());

                                }
                            });
                }
                break;
            case R.id.ll_work_content:
                dictionariesDialog.showDialog(mContext, "", tvWorkContent.getText().toString(), dictionariesEntityList, new OnItemSelectedListener() {
                    @Override
                    public void onSelected(DictionariesEntity dictionariesEntity) {
                        tvWorkContent.setText(dictionariesEntity.getNAME());
                    }
                });
                break;
            case R.id.submit:


                if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
                    if (TextUtils.isEmpty(tvCaseType.getText().toString())) {
                        Toasty.warning(mContext, "请选择案件类型！").show();
                        return;
                    }
                }
                final String task_name = tvTaskName.getText().toString();
                if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
                    if (TextUtils.isEmpty(task_name)) {
                        Toasty.warning(mContext, "请选择任务名称！").show();
                        return;
                    }
                }
                if (TimeUtil.isOverdueCurrentTime(tvCaseTime.getText().toString())) {
                    Toasty.warning(mContext, getString(R.string.time_is_only_before_the_current_time)).show();
                    return;
                }

                final String PERSON_NAME = edName.getText().toString();//当事人姓名
                if (TextUtils.isEmpty(PERSON_NAME)) {
                    Toasty.warning(mContext, "请输入姓名！").show();
                    return;
                }

                final String REMARKS = edBz.getText().toString();

                String RESOURCE_IDtemp = "";
                for (String data : mSelectPicturePath) {
                    String[] splitString = data.split("/");
                    RESOURCE_IDtemp = RESOURCE_IDtemp + splitString[splitString.length - 1] + ",";
                }
                final String RESOURCE_ID = dealEnd(RESOURCE_IDtemp);


                Gson gson = new Gson();
                final String userScore = gson.toJson(securityCasePointList);


                final String MASTER_POLICE_NO = AppValue.getInstance().getmUserInfo().getUSER_ID();
                String PART_POLICE_NOtemp = "";
                for (Group.GroupUSERBean groupUSERBean : listPersons) {
                    PART_POLICE_NOtemp += "" + groupUSERBean.getUser_ID() + ",";
                }
                final String PART_POLICE_NO = dealEnd(PART_POLICE_NOtemp);


                final String WORK_CONTENT = tvWorkContent.getText().toString();
                if (TextUtils.isEmpty(WORK_CONTENT)) {
                    Toasty.warning(mContext, "请选择工作内容！").show();
                    return;
                }
                String UPLOAD_INFOtemo = edReportInformation.getText().toString();
                final String UPLOAD_INFO = "1";

                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setContentText(getString(R.string.confirm_submission)).setConfirmText("确定").setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                submit(mSelectPicturePath, taskTypeID, task_name, id, tvCaseTime.getText().toString(), PERSON_NAME,
                                        MASTER_POLICE_NO, PART_POLICE_NO, REMARKS, RESOURCE_ID, userScore, WORK_CONTENT, UPLOAD_INFO,tvCasePoint.getText().toString());
                            }
                        }).show();

                break;


        }

    }

    /**
     * 提交数据
     */
    public void submit(final List<String> mSelectPicturePath, String task_type, String task_name, String CASE_TYPE, String CASE_TIME, String
            PERSON_NAME, String
                               MASTER_POLICE_NO, String PART_POLICE_NO, String REMARKS, String RESOURCE_ID, String userScore,
                       String WORK_CONTENT, String UPLOAD_INFO,String TOTAL_SCORE
    ) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("task_type", task_type);//案件类型ID
        data.addProperty("task_name", task_name);//案件类型ID

        data.addProperty("CASE_TYPE", CASE_TYPE);//案件类型ID
        data.addProperty("CASE_TIME", CASE_TIME);//案件发生时间
//        data.addProperty("POSITION_NAME", POSITION_NAME);//地点
        data.addProperty("LATITUDE", getLATITUDE());
        data.addProperty("LONGITUDE", getLONGITUDE());


        data.addProperty("MASTER_POLICE_NO", MASTER_POLICE_NO);//主办民警编号
        data.addProperty("PART_POLICE_NO", PART_POLICE_NO);//协警编号
        data.addProperty("REMARKS", REMARKS);//备注

        data.addProperty("RESOURCE_ID", RESOURCE_ID);//
        data.addProperty("userScore", userScore);//分值分配方式
        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            data.addProperty("task_id", myTasksEntity.getID());//备注
        }
//        data.addProperty("CASE_NAME", CASE_NAME);//案件名称
        data.addProperty("PERSON_NAME", PERSON_NAME);//姓名
        data.addProperty("UPLOAD_INFO", UPLOAD_INFO);//上报信息

        data.addProperty("WORK_CONTENT", WORK_CONTENT);//工作内容
        data.addProperty("TOTAL_SCORE", TOTAL_SCORE);//总分
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appbassessBasework(httpSubmit).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);
                    insertToData(mSelectPicturePath);
                    if (caseComeType == CaseComeEnum.TASK.getValue()) {
                        finish();
                    } else if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
                        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("案件申报成功").setContentText("是否继续申报？").setConfirmText
                                ("确定").setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                finish();
                            }
                        }).show();
                    }
                } else {
                    showError(response.getResultMessage());
                    submit.setProgress(0);
                }
                L.d("sucess");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                showError(mContext.getResources().getString(R.string.submint_failed));
                submit.setProgress(0);
            }
        }));
    }

}
