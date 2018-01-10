package com.pvirtech.pzpolice.ui.activity.work.assessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.sublimepickerlibrary.datepicker.SelectedDate;
import com.example.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TeamAssessmentEntity;
import com.pvirtech.pzpolice.entity.TeamAssessmentReasonEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.third.SublimePickerFragment;
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.activity.work.SelectPersonActivity;
import com.pvirtech.pzpolice.ui.adapter.TeamAssessmentAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pd on 2017/7/19.
 */

public class TeamAssessmentFragment extends BaseFragment {


    @BindView(R.id.team_ass_recyclerView)
    RecyclerView recycleview;
    TeamAssessmentAdapter adapter;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_reason)
    LinearLayout llReason;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    @BindView(R.id.tv_person_notice)
    TextView tvPersonNotice;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;

    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.ed_points)
    EditText edPoints;
    @BindView(R.id.ed_bz)
    EditText edBz;

    private List<TeamAssessmentEntity> mDataLists = new ArrayList<>();
    SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
    ArrayList<Group.GroupUSERBean> list = new ArrayList<>();
    List<TeamAssessmentReasonEntity> listReason = new ArrayList<>();
    String leaveReason;
    int state = 1;
    private static final int DECIMAL_DIGITS = 1;//小数的位数

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_team_assessment, container, false);
        initInfoTitleView(view);
        TAG = "TeamAssessmentActivity";
        initsTeamAssement();
        initView();
        getReasons();
        return view;
    }

    private void initView() {
        tvTime.setText(TimeUtil.getYMD());
        tvPersonNotice.setText("");
        adapter = new TeamAssessmentAdapter(mContext, mDataLists, new TeamAssessmentAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                for (TeamAssessmentEntity TeamAssessmentEntity : mDataLists) {
                    TeamAssessmentEntity.setChecked(false);
                }
                state = mDataLists.get(position).getState();
                mDataLists.get(position).setChecked(true);
                adapter.notifyDataSetChanged();
                initReasonsVew();

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
//        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 2));

        edPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                        edPoints.setText(s);
                        edPoints.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edPoints.setText(s);
                    edPoints.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edPoints.setText(s.subSequence(0, 1));
                        edPoints.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initsTeamAssement() {
        TeamAssessmentEntity plus_point = new TeamAssessmentEntity(R.mipmap.bonus_points, "加分项", 1);
        plus_point.setChecked(true);
        mDataLists.add(plus_point);
        TeamAssessmentEntity diminuer_point = new TeamAssessmentEntity(R.mipmap.descending_item, "扣分项", 0);
        mDataLists.add(diminuer_point);
    }

    private List<TeamAssessmentReasonEntity> getReasons(int state) {
        List<TeamAssessmentReasonEntity> teamAssessmentReasonEntityList = new ArrayList<>();
        for (TeamAssessmentReasonEntity entity : listReason) {
            if (state == entity.getFLAG()) {
                teamAssessmentReasonEntityList.add(entity);
            }
        }
        return teamAssessmentReasonEntityList;
    }

    private void initReasonsVew() {
        List<TeamAssessmentReasonEntity> teamAssessmentReasonEntityList = getReasons(state);
        if (!ListUtils.isEmpty(teamAssessmentReasonEntityList) && teamAssessmentReasonEntityList.size() >= 1) {
            TeamAssessmentReasonEntity entity = teamAssessmentReasonEntityList.get(0);
            leaveReason = entity.getID();
            tvReason.setText(entity.getREASON());
            if (entity.getBASE_SCORE() > 0) {
                edPoints.setText(entity.getBASE_SCORE() + "");
            } else {
                edPoints.setText("");
                edPoints.setHint(R.string.please_input_score);
            }
        }
    }

    @OnClick({R.id.ll_my_info, R.id.ll_time, R.id.ll_reason, R.id.ll_point, R.id.ll_person, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                sublimePickerFragmentUtils.show(false, true, false, getFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        System.out.println("");
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                            .RecurrenceOption recurrenceOption, String recurrenceRule) {
                        Date date = selectedDate.getFirstDate().getTime();
                        String time = TimeUtil.getDateToYMD(date);
                        tvTime.setText(time);
                    }
                });
                break;
            case R.id.ll_reason:
                DictionariesDialog dialog = new DictionariesDialog();

                dialog.showTeamAssessmentDialog(mContext, "", tvReason.getText().toString(), getReasons(state), new DictionariesDialog
                        .OnTeamAssessmentItemSelectedListener() {
                    @Override
                    public void onSelected(TeamAssessmentReasonEntity entity) {
                        leaveReason = entity.getID();
                        tvReason.setText(entity.getREASON());
                        if (entity.getBASE_SCORE() > 0) {
                            edPoints.setText(entity.getBASE_SCORE() + "");
                        } else {
                            edPoints.setText("");
                            edPoints.setHint(R.string.please_input_score);
                        }
                    }
                });
                break;
            case R.id.ll_point:
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
                if (!TextUtils.isEmpty(strUserList)) {
                    strUserList = strUserList.substring(0, strUserList.length() - 1);
                } else {
                    Toasty.warning(mContext, "请选择人员").show();
                    return;
                }
                if (TextUtils.isEmpty(tvReason.getText().toString())) {
                    Toasty.warning(mContext, "请选择事由").show();
                    return;
                }

                final String strUserListData = strUserList;

                String notice = "";
                if (state == 1) {
                    notice = "因" + tvReason.getText().toString() + "加" + edPoints.getText().toString() + "分，确定提交?";
                } else if (state == 0) {
                    notice = "因" + tvReason.getText().toString() + "减" + edPoints.getText().toString() + "分，确定提交?";
                }
                final String remarks = edBz.getText().toString();

                new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                        .setContentText(notice).setConfirmText("确定")
                        .setCancelText
                                ("取消").setConfirmClickListener(new SweetAlertDialog
                        .OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        submit(tvTime.getText().toString(), edPoints.getText().toString(), leaveReason, strUserListData, state, remarks);
                    }
                }).show();


                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            tvPersonNotice.setText(list.size() + "(人)");
            tvPerson.setText(person);
        }
    }

    private void getReasons() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptbuildRu(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<TeamAssessmentReasonEntity>>>() {


            @Override
            public void accept(@NonNull HttpResult<List<TeamAssessmentReasonEntity>> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    listReason = response.getData();
                    hideLoading();
                    initReasonsVew();
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


    public void submit(String date, String score, String reason, String user, int state, String remarks) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("date", date);
        data.addProperty("score", score);
        data.addProperty("reason", reason);
        data.addProperty("user", user);
        data.addProperty("state", state);
        data.addProperty("remarks", remarks);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptbuildTba(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {


            @Override
            public void accept(@NonNull HttpResult response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);

                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setContentText("队建考核提交成功，是否继续提交？")
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
                            getActivity().finish();
                        }
                    }).show();

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
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)).show();
                submit.setProgress(0);
            }
        }));

    }

}
