package com.pvirtech.pzpolice.ui.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TaskDistributeEntity;
import com.pvirtech.pzpolice.entity.TaskDistributeTaskEntity;
import com.pvirtech.pzpolice.entity.TaskDistributeUsers;
import com.pvirtech.pzpolice.enumeration.TaskTypeEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 任务发布  主界面
 */
public class TaskDistributeActivity extends BaseActivity {

    ArrayList<Group.GroupUSERBean> list = new ArrayList<>();
    String TASK_TYPE, CASE_TYPE;
    List<TaskDistributeEntity> firstList = new ArrayList<>();
    List<TaskDistributeEntity> secondList = new ArrayList<>();
    List<TaskDistributeEntity> thirdList = new ArrayList<>();
    List<TaskDistributeEntity> fourthList = new ArrayList<>();
    List<TaskDistributeTaskEntity> fifthList = new ArrayList<>();
    List<TaskDistributeTaskEntity> TaskDistributeTaskEntityList = new ArrayList<>();


    List<DictionariesEntity> dictionariesEntityList = new ArrayList<>();
    String TYPE = "";
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
    DictionariesDialog dictionariesDialog = new DictionariesDialog();
    String score_WAY = "";

    String GROUP_ID = "";

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_first_type)
    TextView tvFirstType;
    @BindView(R.id.ll_first_type)
    LinearLayout llFirstType;
    @BindView(R.id.tv_second_type)
    TextView tvSecondType;
    @BindView(R.id.ll_second_type)
    LinearLayout llSecondType;
    @BindView(R.id.tv_third_type)
    TextView tvThirdType;
    @BindView(R.id.ll_third_type)
    LinearLayout llThirdType;
    @BindView(R.id.tv_fourth_type)
    TextView tvFourthType;
    @BindView(R.id.ll_fourth_type)
    LinearLayout llFourthType;
    @BindView(R.id.tv_fifth_type)
    TextView tvFifthType;
    @BindView(R.id.ll_fifth_type)
    LinearLayout llFifthType;
    @BindView(R.id.ed_target)
    EditText edTarget;
    @BindView(R.id.tv_limited_time)
    TextView tvLimitedTime;
    @BindView(R.id.ll_limited_time)
    LinearLayout llLimitedTime;
    @BindView(R.id.ed_single)
    EditText edSingle;
    @BindView(R.id.ll_single)
    LinearLayout llSingle;
    @BindView(R.id.ed_limit)
    EditText edLimit;
    @BindView(R.id.ll_limit)
    LinearLayout llLimit;
    @BindView(R.id.ed_unfinished)
    EditText edUnfinished;
    @BindView(R.id.ed_evaluation_method)
    EditText edEvaluationMethod;
    @BindView(R.id.tv_executor_person)
    TextView tvExecutorPerson;
    @BindView(R.id.ll_executor_person)
    LinearLayout llExecutorPerson;
    @BindView(R.id.tv_executor_persons)
    TextView tvExecutorPersons;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.cb_isgroup)
    CheckBox cbIsgroup;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_distribute);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initTitleView(getString(R.string.task_publishing));
        mContext = TaskDistributeActivity.this;
        TAG = "TaskDistributeActivity";
        tvLimitedTime.setText(TimeUtil.getYMD());
        appbassessMenu("0", "4");
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sublimePickerFragmentUtils = null;
        dictionariesDialog = null;
    }

    private void initView() {
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
            dictionariesEntityList.add(taskTypeEnum.getValue());
        }
        tvType.setText(dictionariesEntityList.get(0).getNAME());
        TYPE = dictionariesEntityList.get(0).getID();
    }


    @OnClick({R.id.ll_limited_time, R.id.submit, R.id.ll_executor_person, R.id.ll_first_type, R.id.ll_second_type, R.id.ll_third_type, R.id
            .ll_fourth_type, R.id.ll_fifth_type, R.id.ll_type, R.id.cb_isgroup})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_limited_time:
                sublimePickerFragmentUtils.show(false, true, false, getSupportFragmentManager(),
                        new SublimePickerFragment.Callback() {
                            @Override
                            public void onCancelled() {
                                System.out.println("");
                            }

                            @Override
                            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                                    .RecurrenceOption recurrenceOption, String recurrenceRule) {
                                Date date = selectedDate.getFirstDate().getTime();
                                String startTime = TimeUtil.getDateToYMD(date);
                                tvLimitedTime.setText(startTime);
                            }
                        });
                break;


            case R.id.ll_executor_person:
                Intent intent = new Intent(mContext, SelectPersonActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_first_type:
                if (ListUtils.isEmpty(TaskDistributeTaskEntityList) || ListUtils.isEmpty(firstList)) {
                    appbassessMenu("0", "4");
                    return;
                }
                dictionariesDialog.showDialogTaskDistributeEntity(mContext, "", tvFirstType.getText().toString(), firstList, new DictionariesDialog
                        .OnItemSelectedListenerTaskDistributeEntity() {
                    @Override
                    public void onSelected(TaskDistributeEntity taskDistributeEntity) {
                        dealSecond(taskDistributeEntity);
                        GROUP_ID = taskDistributeEntity.getId();
                    }
                });
                break;
            case R.id.ll_second_type:
                if (ListUtils.isEmpty(TaskDistributeTaskEntityList) || ListUtils.isEmpty(firstList)) {
                    appbassessMenu("0", "4");
                    return;
                }
                dictionariesDialog.showDialogTaskDistributeEntity(mContext, "", tvSecondType.getText().toString(), secondList, new
                        DictionariesDialog.OnItemSelectedListenerTaskDistributeEntity() {
                            @Override
                            public void onSelected(TaskDistributeEntity taskDistributeEntity) {
                                thirdList = taskDistributeEntity.getSubMenu();
                                CASE_TYPE = taskDistributeEntity.getId() + "";
                                tvSecondType.setText(taskDistributeEntity.getName());

                                if (!ListUtils.isEmpty(thirdList) && thirdList.size() >= 1) {
                                    llThirdType.setVisibility(View.VISIBLE);
                                    CASE_TYPE = thirdList.get(0).getId() + "";
                                    tvThirdType.setText(thirdList.get(0).getName());
                                    fourthList = thirdList.get(0).getSubMenu();
                                    if (!ListUtils.isEmpty(fourthList) && fourthList.size() >= 1) {
                                        llFourthType.setVisibility(View.VISIBLE);
                                        CASE_TYPE = fourthList.get(0).getId() + "";
                                        tvFourthType.setText(fourthList.get(0).getName());
                                        getTask(fifthList, TaskDistributeTaskEntityList, fourthList.get(0).getId());

                                    } else {
                                        getTask(fifthList, TaskDistributeTaskEntityList, thirdList.get(0).getId());
                                        llFourthType.setVisibility(View.GONE);
                                    }
                                } else {
                                    getTask(fifthList, TaskDistributeTaskEntityList, taskDistributeEntity.getId());
                                    llThirdType.setVisibility(View.GONE);
                                    llFourthType.setVisibility(View.GONE);
                                }
                            }
                        });
                break;

            case R.id.ll_third_type:
                if (ListUtils.isEmpty(TaskDistributeTaskEntityList) || ListUtils.isEmpty(firstList)) {
                    appbassessMenu("0", "4");
                    return;
                }
                dictionariesDialog.showDialogTaskDistributeEntity(mContext, "", tvThirdType.getText().toString(), thirdList, new DictionariesDialog
                        .OnItemSelectedListenerTaskDistributeEntity() {
                    @Override
                    public void onSelected(TaskDistributeEntity taskDistributeEntity) {
                        fourthList = taskDistributeEntity.getSubMenu();
                        CASE_TYPE = taskDistributeEntity.getId() + "";
                        tvThirdType.setText(taskDistributeEntity.getName());

                        if (!ListUtils.isEmpty(fourthList) && fourthList.size() >= 1) {
                            llFourthType.setVisibility(View.VISIBLE);
                            CASE_TYPE = fourthList.get(0).getId() + "";
                            tvFourthType.setText(fourthList.get(0).getName());
                            getTask(fifthList, TaskDistributeTaskEntityList, fourthList.get(0).getId());

                        } else {
                            getTask(fifthList, TaskDistributeTaskEntityList, taskDistributeEntity.getId());
                            llFourthType.setVisibility(View.GONE);
                        }
                    }
                });
                break;


            case R.id.ll_fourth_type:
                if (ListUtils.isEmpty(TaskDistributeTaskEntityList) || ListUtils.isEmpty(firstList)) {
                    appbassessMenu("0", "4");
                    return;
                }
                dictionariesDialog.showDialogTaskDistributeEntity(mContext, "", tvFourthType.getText().toString(), fourthList, new
                        DictionariesDialog.OnItemSelectedListenerTaskDistributeEntity() {
                            @Override
                            public void onSelected(TaskDistributeEntity taskDistributeEntity) {
                                CASE_TYPE = taskDistributeEntity.getId() + "";
                                tvFourthType.setText(taskDistributeEntity.getName());
                                getTask(fifthList, TaskDistributeTaskEntityList, taskDistributeEntity.getId());
                            }
                        });
                break;

            case R.id.ll_fifth_type://任务名称
                if (ListUtils.isEmpty(TaskDistributeTaskEntityList) || ListUtils.isEmpty(firstList)) {
                    appbassessMenu("0", "4");
                    return;
                }
                TASK_TYPE = "";
                score_WAY = "";
                dictionariesDialog.showDialogTaskDistributeTaskEntity(mContext, "", tvFifthType.getText().toString(), fifthList, new
                        DictionariesDialog.OnItemSelectedListenerTaskDistributeTask() {
                            @Override
                            public void onSelected(TaskDistributeTaskEntity entity) {
                                TASK_TYPE = entity.getId() + "";
                                score_WAY = entity.getScore_WAY();
                                tvFifthType.setText(entity.getName());
                                //UI处理
                                edTarget.setText(entity.getTarget());
                                edSingle.setText(entity.getSigle_SCORE());
                                edLimit.setText(entity.getTotal_SCORE());
                                edUnfinished.setText(entity.getDeduct_SCORE());
                                edEvaluationMethod.setText(entity.getDescription());
                            }
                        });

                break;

            case R.id.ll_type:

                dictionariesDialog.showDialog(mContext, "", tvType.getText().toString(), dictionariesEntityList, new OnItemSelectedListener() {
                    @Override
                    public void onSelected(DictionariesEntity dictionariesEntity) {
                        tvType.setText(dictionariesEntity.getNAME());
                        TYPE = dictionariesEntity.getID();
                    }
                });
                break;

            case R.id.cb_isgroup:
                if (cbIsgroup.isChecked()) {
                    llExecutorPerson.setVisibility(View.GONE);
                    tvExecutorPersons.setVisibility(View.GONE);
                } else {
                    llExecutorPerson.setVisibility(View.VISIBLE);
                    tvExecutorPersons.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.submit:
                final String strChoseTaskType = tvFirstType.getText().toString();
                if (TextUtils.isEmpty(strChoseTaskType)) {
                    Toasty.warning(mContext, getString(R.string.chose_task_type)).show();
                    return;
                }

                final String strTaskName = tvFifthType.getText().toString();
                if (TextUtils.isEmpty(strTaskName)) {
                    Toasty.warning(mContext, getString(R.string.select_task_name)).show();
                    return;
                }

                //完成时限
                final String leaveTime = tvLimitedTime.getText().toString();
                if (!TimeUtil.isOverdue(leaveTime)) {
                    Toasty.warning(mContext, "完成时限不能在今天之前！").show();
                    return;
                }

                final String target = edTarget.getText().toString();
                if (TextUtils.isEmpty(target)) {
                    Toasty.warning(mContext, "请输入完成目标").show();
                    return;
                }

                final String single = edSingle.getText().toString();
                if (TextUtils.isEmpty(single)) {
                    Toasty.warning(mContext, "请输入单任务分值").show();
                    return;
                }

                final String limit = edLimit.getText().toString();
                if (TextUtils.isEmpty(limit)) {
                    Toasty.warning(mContext, "请输入总分值上限").show();
                    return;
                }

                final String unfinished = edUnfinished.getText().toString();
                if (TextUtils.isEmpty(unfinished)) {
                    Toasty.warning(mContext, "请输入未完成扣分").show();
                    return;
                }

                final String evaluationMethod = edEvaluationMethod.getText().toString();
                if (TextUtils.isEmpty(evaluationMethod)) {
                    Toasty.warning(mContext, "请输入考核办法").show();
                    return;
                }


                List<TaskDistributeUsers> groupUsers = new ArrayList<>();
                for (Group.GroupUSERBean groupUSERBean : list) {
                    boolean isHave = false;
                    for (TaskDistributeUsers temp : groupUsers) {
                        if (temp.getGROUP_ID().equals(groupUSERBean.getGroup_ID())) {
                            isHave = true;
                            String add = "";
                            if (TextUtils.isEmpty(temp.getEXCUTE_USER_NO())) {
                                add = groupUSERBean.getUser_ID();
                            } else {
                                add = temp.getEXCUTE_USER_NO() + "," + groupUSERBean.getUser_ID();
                            }
                            temp.setEXCUTE_USER_NO(add);
                        }
                    }
                    if (!isHave) {
                        TaskDistributeUsers temp = new TaskDistributeUsers();
                        temp.setGROUP_ID(groupUSERBean.getGroup_ID());
                        temp.setEXCUTE_USER_NO(groupUSERBean.getUser_ID());
                        groupUsers.add(temp);
                    }
                }
                final String USERARRAY = new Gson().toJson(groupUsers);

                if (!cbIsgroup.isChecked() && ListUtils.isEmpty(list)) {
                    Toasty.warning(mContext, "请选择执行人").show();
                    return;
                }

                String content = "发布" + tvFifthType.getText().toString() + "任务，确认提交？";
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE).setContentText(content)
                        .setConfirmText("确定").setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                submit(TASK_TYPE, score_WAY, CASE_TYPE, strTaskName, evaluationMethod, target, single, limit, unfinished,
                                        leaveTime, cbIsgroup.isChecked(), TYPE, GROUP_ID, USERARRAY);
                            }
                        }).show();

                break;
        }
    }


    private void appbassessMenu(String id, String num) {
        showLoading("");
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("num", num);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskTaskCaseType(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    Object data = response.getData();
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("Case");
                    firstList = getTaskDistributeEntity(jsonArray);
                    JSONArray jsonArrayTask = jsonObject.getJSONArray("Task");
                    TaskDistributeTaskEntityList = JSON.parseArray(jsonArrayTask.toJSONString(), TaskDistributeTaskEntity.class);
                    hideLoading();
                    dealSecond(firstList.get(0));//初始化显示
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

    private List<TaskDistributeEntity> getTaskDistributeEntity(JSONArray jsonArray) {
        List<TaskDistributeEntity> taskDistributeEntityList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            TaskDistributeEntity temp = new TaskDistributeEntity();
            temp.setId(jsonObject.getString("id"));
            temp.setLevel(jsonObject.getInteger("level"));
            temp.setName(jsonObject.getString("name"));
            temp.setParent_ID(jsonObject.getString("parent_ID"));
            JSONArray jsonArray1 = jsonObject.getJSONArray("subMenu");

            List<TaskDistributeEntity> child = getTaskDistributeEntity(jsonArray1);
            temp.setSubMenu(child);
            taskDistributeEntityList.add(temp);

        }

        return taskDistributeEntityList;
    }

    private void getTask(List<TaskDistributeTaskEntity> fifthList, List<TaskDistributeTaskEntity>
            TaskDistributeTaskEntityList, String parent_ID) {
        fifthList.clear();
        for (TaskDistributeTaskEntity entity : TaskDistributeTaskEntityList) {
            if (entity.getParent_ID().equals(parent_ID)) {
                fifthList.add(entity);
            }
        }
        if (!ListUtils.isEmpty(fifthList) && fifthList.size() >= 1) {
            TaskDistributeTaskEntity temp = fifthList.get(0);
            TASK_TYPE = temp.getId() + "";
            score_WAY = temp.getScore_WAY();
            tvFifthType.setText(temp.getName());
            edEvaluationMethod.setText(temp.getDescription());

            //UI处理
            edTarget.setText(temp.getTarget());
            edSingle.setText(temp.getSigle_SCORE());
            edLimit.setText(temp.getTotal_SCORE());
            edUnfinished.setText(temp.getDeduct_SCORE());
            edEvaluationMethod.setText(temp.getDescription());

        } else {
            TASK_TYPE = "";
            score_WAY = "";
            tvFifthType.setText("");
            edEvaluationMethod.setText("");
        }
    }

    public void submit(String TASK_TYPE, String score_WAY, String CASE_TYPE, String TASK_NAME, String TASK_DESC, String TOTAL_COUNT, String
            SIGLE_SCORE, String MAX_SCORE, String DEDUCT_SCORE, String TASK_END_TIME, boolean isGroup, String TYPE, String
                               GROUP_ID, String USERARRAY) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("TASK_TYPE", TASK_TYPE);//任务类型
        data.addProperty("score_WAY", score_WAY);//分值分配方式
        data.addProperty("CASE_TYPE", CASE_TYPE);//任务类型

        data.addProperty("TASK_NAME", TASK_NAME);//任务名称
        data.addProperty("TASK_DESC", TASK_DESC);//考核办法
        data.addProperty("TOTAL_COUNT", TOTAL_COUNT);//数量
        data.addProperty("SIGLE_SCORE", SIGLE_SCORE);//单个分数
        data.addProperty("MAX_SCORE", MAX_SCORE);//最大分
        data.addProperty("DEDUCT_SCORE", DEDUCT_SCORE);//未完成扣分
        data.addProperty("TASK_END_TIME", TASK_END_TIME);////任务截止时间

        if (isGroup) {
            data.addProperty("isGroup", "true");//执行人
            data.addProperty("GROUP_ID", GROUP_ID);//执行人
        } else {
            data.addProperty("isGroup", "false");
            data.addProperty("USERARRAY", USERARRAY);//
        }
        data.addProperty("TYPE", TYPE);//执行人

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskPbtask(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setContentText("任务发布成功，是否继续发布任务？")
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
                    showError(response.getResultMessage());
                    submit.setProgress(0);
                }
                L.d("sucess");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                showError("");
                submit.setProgress(0);
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_SELECT_PERSON) {
            list = data.getParcelableArrayListExtra(Constant.PERSON_SELECTED);
            String person = "";
            for (Group.GroupUSERBean groupUSERBean : list) {
                person = person + groupUSERBean.getName() + ",";
            }
            if (!TextUtils.isEmpty(person)) {
                person = person.substring(0, person.length() - 1);
            }
            tvExecutorPerson.setText("(" + list.size() + ")人");
            tvExecutorPersons.setText(person);
            System.out.println("");
        }
    }

    private void dealSecond(TaskDistributeEntity taskDistributeEntity) {
        secondList = taskDistributeEntity.getSubMenu();
        CASE_TYPE = taskDistributeEntity.getId() + "";
        tvFirstType.setText(taskDistributeEntity.getName());
        GROUP_ID = taskDistributeEntity.getId();
        if (!ListUtils.isEmpty(secondList) && secondList.size() >= 1) {
            llSecondType.setVisibility(View.VISIBLE);
            CASE_TYPE = secondList.get(0).getId() + "";
            tvSecondType.setText(secondList.get(0).getName());
            thirdList = secondList.get(0).getSubMenu();
            if (!ListUtils.isEmpty(thirdList) && thirdList.size() >= 1) {
                llThirdType.setVisibility(View.VISIBLE);
                CASE_TYPE = thirdList.get(0).getId() + "";
                tvThirdType.setText(thirdList.get(0).getName());
                fourthList = thirdList.get(0).getSubMenu();
                if (!ListUtils.isEmpty(fourthList) && fourthList.size() >= 1) {
                    llFourthType.setVisibility(View.VISIBLE);
                    CASE_TYPE = fourthList.get(0).getId() + "";
                    tvFourthType.setText(fourthList.get(0).getName());
                    getTask(fifthList, TaskDistributeTaskEntityList, fourthList.get(0).getId());

                } else {
                    getTask(fifthList, TaskDistributeTaskEntityList, thirdList.get(0).getId());
                    llFourthType.setVisibility(View.GONE);
                }
            } else {
                getTask(fifthList, TaskDistributeTaskEntityList, secondList.get(0).getId());
                llThirdType.setVisibility(View.GONE);
                llFourthType.setVisibility(View.GONE);
            }
        } else {
            getTask(fifthList, TaskDistributeTaskEntityList, taskDistributeEntity.getId());
            llSecondType.setVisibility(View.GONE);
            llThirdType.setVisibility(View.GONE);
            llFourthType.setVisibility(View.GONE);
        }
    }
}
