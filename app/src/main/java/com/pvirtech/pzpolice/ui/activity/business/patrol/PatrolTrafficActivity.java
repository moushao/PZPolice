package com.pvirtech.pzpolice.ui.activity.business.patrol;

import android.content.Intent;
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
import com.pvirtech.licenseplate.mylibrary.Constant;
import com.pvirtech.licenseplate.mylibrary.InputPlateActivity;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TaskAndIntegration;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.ManagementEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
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

public class PatrolTrafficActivity extends BaseCaseActivity {


    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.ll_task_name)
    LinearLayout llTaskName;
    @BindView(R.id.tv_case_time)
    TextView tvCaseTime;
    @BindView(R.id.ll_case_time)
    LinearLayout llCaseTime;
    @BindView(R.id.ed_place)
    EditText edPlace;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.ll_car_number)
    LinearLayout llCarNumber;
    @BindView(R.id.ed_person_name)
    EditText edPersonName;
    @BindView(R.id.tv_input_persons)
    TextView tvInputPersons;
    @BindView(R.id.ll_input)
    LinearLayout llInput;
    @BindView(R.id.ed_bz)
    EditText edBz;
    @BindView(R.id.submit)
    CircularProgressButton submit;

    List<DictionariesEntity> dictionariesEntityList = new ArrayList<>();
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ll_result)
    LinearLayout llResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        setBaseContentView(R.layout.activity_patrol_traffic);
        initTitleView(title);
        super.onCreate(savedInstanceState);
        TAG = "PatrolTrafficActivity";
        initView(caseComeType);
        initCommonView();
    }


    private void initView(int caseComeType) {
        if (caseComeType == CaseComeEnum.TASK.getValue()) {
//            tvCaseType.setText(myTasksEntity.getCASE_NAME());
            tvTaskName.setText(myTasksEntity.getTASK_NAME());
//            tvCaseTime.setText(myTasksEntity.getDATE_STR());
            apptaskScoreway();
        } else if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
//            tvCaseType.setText(title);
            tvCaseTime.setText(TimeUtil.getYMDHM());
            apptaskTaskTypeList(id);
        }

        for (ManagementEnum myEnum : ManagementEnum.values()) {
            dictionariesEntityList.add(myEnum.getValue());
        }
    }


    /**
     * 提交数据
     */
    public void submit(final List<String> mSelectPicturePath, String task_type, String task_name, String CASE_TYPE, String CASE_START_TIME, String
            CASE_POSITION_NAME, String CASE_NAME, String PERSON_NAME, String MASTER_POLICE_NO, String PART_POLICE_NO, String CASE_POSITON_POINT,
                       String REMARKS, String RESOURCE_ID, String userScore, String CAR_NUMBER, String RESULT,String TOTAL_SCORE) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("type", "jt");//案件类型--交通案件
        data.addProperty("task_type", task_type);//案件类型ID
        data.addProperty("task_name", task_name);//案件类型ID
        data.addProperty("CASE_TYPE", CASE_TYPE);//案件类型ID
        data.addProperty("CASE_START_TIME", CASE_START_TIME);//案件发生时间
        data.addProperty("CASE_POSITION_NAME", CASE_POSITION_NAME);//案件地点
        data.addProperty("CASE_NAME", CASE_NAME);//案件名
        data.addProperty("PERSON_NAME", PERSON_NAME);//当事人员名
        data.addProperty("MASTER_POLICE_NO", MASTER_POLICE_NO);//主办警察
        data.addProperty("PART_POLICE_NO", PART_POLICE_NO);//协助警察
        data.addProperty("CASE_POSITON_POINT", CASE_POSITON_POINT);//地点经纬度
        data.addProperty("REMARKS", REMARKS);//备注
        data.addProperty("RESOURCE_ID", RESOURCE_ID);//备注
        data.addProperty("userScore", userScore);//备注
        data.addProperty("CAR_NUMBER", CAR_NUMBER);//车牌号
        data.addProperty("RESULT", RESULT);//车牌号
        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            data.addProperty("task_id", myTasksEntity.getID());//备注
        }
        data.addProperty("TOTAL_SCORE", TOTAL_SCORE);//总分

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appbassessSubmitADM(httpSubmit).subscribeOn(Schedulers.newThread()).observeOn
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

    DictionariesDialog dictionariesDialog = new DictionariesDialog();
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();

    @OnClick({R.id.ll_case_time, R.id.submit, R.id.ll_car_number, R.id.ll_task_name,R.id.ll_result})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.ll_task_name:
                if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
                    dictionariesDialog = new DictionariesDialog();
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

            case R.id.ll_car_number:
                Intent intent = new Intent(mContext, InputPlateActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.ll_result:
                dictionariesDialog.showDialog(mContext, "", tvResult.getText().toString(), dictionariesEntityList, new OnItemSelectedListener() {
                    @Override
                    public void onSelected(DictionariesEntity dictionariesEntity) {
                        tvResult.setText(dictionariesEntity.getNAME());
                    }
                });
                break;
            case R.id.submit:
                String task_name = tvTaskName.getText().toString();
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

                String CASE_POSITION_NAME = edPlace.getText().toString();
               /* if (TextUtils.isEmpty(CASE_POSITION_NAME)) {
                    Toasty.warning(mContext, "请输入违章地点！").show();
                    return;
                }*/


                String CAR_NUMBER = tvCarNumber.getText().toString();
                if (TextUtils.isEmpty(CAR_NUMBER)) {
                    Toasty.warning(mContext, "请输入车牌号！").show();
                    return;
                }
                String PERSON_NAME = edPersonName.getText().toString();
               if (TextUtils.isEmpty(PERSON_NAME)) {
                    Toasty.warning(mContext, "请输入当事人姓名！").show();
                    return;
                }
              /*  if (ListUtils.isEmpty(listPersons)) {
                    Toasty.warning(mContext, "请选择参与人员！").show();
                    return;
                }*/

                String RESULT = tvResult.getText().toString();
                if (TextUtils.isEmpty(RESULT)) {
                    Toasty.warning(mContext, "请选择处置结果！").show();
                    return;
                }


                String strListPersons = "";
                for (Group.GroupUSERBean groupUSERBean : listPersons) {
                    strListPersons += "" + groupUSERBean.getUser_ID() + ",";
                }
                strListPersons = dealEnd(strListPersons);

                String REMARKS = edBz.getText().toString();

                String RESOURCE_ID = "";
                for (String data : mSelectPicturePath) {
                    String[] splitString = data.split("/");
                    RESOURCE_ID = RESOURCE_ID + splitString[splitString.length - 1] + ",";
                }
                RESOURCE_ID = dealEnd(RESOURCE_ID);


                String userScore = "";//分值分配
                Gson gson = new Gson();
                userScore = gson.toJson(securityCasePointList);

                submit(mSelectPicturePath, taskTypeID, task_name, id, tvCaseTime.getText().toString(), CASE_POSITION_NAME, title, PERSON_NAME,
                        AppValue.getInstance().getmUserInfo().getUSER_ID(), strListPersons, getPosition(), REMARKS, RESOURCE_ID, userScore,
                        CAR_NUMBER, RESULT,tvCasePoint.getText().toString());

                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_CAR_PLATE) {//车牌选择
            String str = data.getExtras().getString("data");
            tvCarNumber.setText(str);
        }
    }

}
