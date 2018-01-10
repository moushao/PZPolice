package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.CaseInfoEntity;
import com.pvirtech.pzpolice.entity.SubtaskEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 子任务  任务详情
 */

public class CaseInfoActivity extends BaseActivity {
    SubtaskEntity subtaskEntity;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.ll_task_name)
    LinearLayout llTaskName;
    @BindView(R.id.tv_case_time)
    TextView tvCaseTime;
    @BindView(R.id.ll_case_time)
    LinearLayout llCaseTime;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ed_case_name)
    EditText edCaseName;
    @BindView(R.id.ll_case_name)
    LinearLayout llCaseName;
    @BindView(R.id.ed_person_name)
    EditText edPersonName;
    @BindView(R.id.ll_person_name)
    LinearLayout llPersonName;
    @BindView(R.id.tv_join_person)
    TextView tvJoinPerson;
    @BindView(R.id.ll_join_person)
    LinearLayout llJoinPerson;
    @BindView(R.id.ed_bz)
    EditText edBz;
    @BindView(R.id.ll_bz)
    LinearLayout llBz;
    @BindView(R.id.tv_join_persons)
    TextView tvJoinPersons;
    @BindView(R.id.tv_mp)
    TextView tvMp;
    @BindView(R.id.ll_mp)
    LinearLayout llMp;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.ll_car_number)
    LinearLayout llCarNumber;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ll_result)
    LinearLayout llResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_info);
        ButterKnife.bind(this);
        initTitleView("任务详情");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        hideView();
        mContext = CaseInfoActivity.this;
        TAG = "SchoolPoliceActivity";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        subtaskEntity = bundle.getParcelable("data");
        String task_id = bundle.getString("task_id");
        getData(subtaskEntity.getId(), task_id, subtaskEntity.getCASE_TYPE() + "");
        toolbar.setTitle(subtaskEntity.getNAME());
        tvTaskName.setText(subtaskEntity.getNAME() + "");
    }

    private void getData(String id, String task_id, String type) {
        showLoading("");
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("task_id", task_id);
        data.addProperty("type", type);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appbassessCaseInfoNormalCase(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<CaseInfoEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<CaseInfoEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    hideLoading();
                    setText(response.getData());
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d(TAG, throwable.getMessage());
                showError("");
            }
        }));

    }


    private void hideView() {
        llPersonName.setVisibility(View.GONE);
        llCaseTime.setVisibility(View.GONE);
        llCarNumber.setVisibility(View.GONE);
        llResult.setVisibility(View.GONE);
        llAddress.setVisibility(View.GONE);
        llCaseName.setVisibility(View.GONE);
        llTitle.setVisibility(View.GONE);
        llContent.setVisibility(View.GONE);
        llMoney.setVisibility(View.GONE);
    }

    private void setText(CaseInfoEntity entity) {


        if (!TextUtils.isEmpty(entity.getPERSON_NAME())) {
            edPersonName.setText(entity.getPERSON_NAME());
            llPersonName.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getCASE_START_TIME())) {
            tvCaseTime.setText(entity.getCASE_START_TIME());
            llCaseTime.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(entity.getCAR_NO())) {
            tvCarNumber.setText(entity.getCAR_NO());
            llCarNumber.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(entity.getRESULT())) {
            tvResult.setText(entity.getRESULT());
            llResult.setVisibility(View.VISIBLE);
        }

        if (!ListUtils.isEmpty(entity.getPART_POLICE_NO())) {
            String strUserList = "";
            for (String data : entity.getPART_POLICE_NO()) {
                strUserList += "" + data + ",";
            }
            if (!TextUtils.isEmpty(strUserList)) {
                strUserList = strUserList.substring(0, strUserList.length() - 1);
            }
            tvJoinPerson.setText(entity.getPART_POLICE_NO().size() + "(人)");
            tvJoinPersons.setText(strUserList);
            llJoinPerson.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getREMARKS())) {
            edBz.setText(entity.getREMARKS());
            llBz.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getCASE_POSITION_NAME())) {
            edAddress.setText(entity.getCASE_POSITION_NAME());
            llAddress.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(entity.getCASE_NAME())) {
            edCaseName.setText(entity.getCASE_NAME());
            llCaseName.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(entity.getTITLE())) {
            tvTitle.setText(entity.getTITLE());
            llTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getCONTENT())) {
            tvContent.setText(entity.getCONTENT());
            llContent.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getPOSITION_NAME())) {
            edAddress.setText(entity.getPOSITION_NAME());
            llAddress.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getCASE_TIME())) {
            tvCaseTime.setText(entity.getCASE_TIME());
            llCaseTime.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getMASTER_POLICE_NO())) {
            tvMp.setText(entity.getMASTER_POLICE_NO());
            llMp.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getMASTER_USER_NAME())) {
            tvMp.setText(entity.getMASTER_USER_NAME());
            llMp.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getWORK_CONTENT())) {
            tvContent.setText(entity.getWORK_CONTENT());
            llContent.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getMONEY_COUNT())) {
            tvMoney.setText(entity.getMONEY_COUNT());
            llMoney.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getCAR_NAME()) && !TextUtils.isEmpty(entity.getCAR_NUM())) {
            tvContent.setText(entity.getCAR_NAME() + entity.getCAR_NUM());
            llContent.setVisibility(View.VISIBLE);
        }

    }

}
