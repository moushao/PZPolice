package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.TaskTrajectory;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.adapter.BusinessWorkReviewAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 队伍建设考核  领导审核界面
 */
public class BusinessWorkReviewActivity extends BaseActivity {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    BusinessWorkReviewAdapter adapter;
    List<TaskTrajectory> list = new ArrayList();
    Bundle bundle;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(R.id.cancle)
    CircularProgressButton cancle;
    BusinessWorkEntity businessWorkEntity;
    @BindView(R.id.ll_botton)
    LinearLayout llBotton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_work_review);
        ButterKnife.bind(this);
        initInfoTitleView("任务详情");
        mContext = BusinessWorkReviewActivity.this;
        TAG = "TeamAssessmentActivity";
        initView();
        getTaskTrail(businessWorkEntity.getID());
    }

    private void initView() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        businessWorkEntity = bundle.getParcelable("data");
        if (businessWorkEntity.getAUDIT_STATE() == ToExamineEnum.STATE13.getToExamineEntity().getState()) {
            llBotton.setVisibility(View.GONE);
        }
        tvNumber.setText(businessWorkEntity.getTASK_NO());
        tvDepartment.setText(businessWorkEntity.getNAME());
        tvInfoName.setText(businessWorkEntity.getNAME());
        tvTotalTime.setText(businessWorkEntity.getTASK_DESC());
        tvTime.setText(businessWorkEntity.getDATE_STR());
        tvReason.setText("已完成(" + businessWorkEntity.getComplete() + "/" + businessWorkEntity
                .getTotal() + ")");

        adapter = new BusinessWorkReviewAdapter(mContext, list, new BusinessWorkReviewAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
    }


    @OnClick({R.id.cancle, R.id.submit, R.id.ll_informatino})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定驳回吗?")
                        .setContentText("你将要驳回该申请").setConfirmText("确定")
                        .setCancelText("取消").setConfirmClickListener(new SweetAlertDialog
                        .OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        submit(businessWorkEntity.getID() + "", businessWorkEntity.getAUDIT_STATE
                                () + "", "0");
                    }
                }).show();

                break;
            case R.id.submit:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定通过吗?")
                        .setContentText("你将要通过该申请").setConfirmText("确定")
                        .setCancelText("取消").setConfirmClickListener(new SweetAlertDialog
                        .OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        submit(businessWorkEntity.getID() + "", businessWorkEntity.getAUDIT_STATE
                                () + "", "1");
                    }
                }).show();

                break;
            case R.id.ll_informatino:
                Intent intent = new Intent(mContext, BusinessWorkReviewSubtaskActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private String dealID(int id) {
        String data = String.valueOf(id);
        if (data.length() == 1) {
            data = "000" + data;
        } else if (data.length() == 2) {
            data = "00" + data;
        } else if (data.length() == 3) {
            data = "0" + data;
        }
        return data;
    }

    private void submit(String id, String state, String pass) {
        showLoading("");
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("state", state);
        data.addProperty("pass", pass);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);


        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskTaskapproal(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    hideLoading();
                    finish();
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

    private void getTaskTrail(String id) {
        showLoading("");
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskTaskTrail(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<TaskTrajectory>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<TaskTrajectory>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (!ListUtils.isEmpty(response.getData())) {
                        list = response.getData();
                    }
                    TaskTrajectory taskTrajectory = new TaskTrajectory();
                    taskTrajectory.setAUDIT_STATE(ToExamineEnum.STATE_1.getToExamineEntity().getState());
                    taskTrajectory.setAUDIT_USER(businessWorkEntity.getNAME());
                    taskTrajectory.setAUDIT_TIME(businessWorkEntity.getCREATE_TIME());
                    list.add(taskTrajectory);
                    adapter.setList(list);
                    hideLoading();
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
}
