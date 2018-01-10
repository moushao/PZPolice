package com.pvirtech.pzpolice.ui.activity.leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveRecordEntity;
import com.pvirtech.pzpolice.entity.VacationDetailsEntity;
import com.pvirtech.pzpolice.enumeration.LeaveType;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.adapter.VacationDetailsAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.pvirtech.pzpolice.main.OperationUtils.getSickLeave;


/**
 * 休假详情界面
 */
public class VacationDetailsActivity extends BaseActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    VacationDetailsAdapter adapter;
    List<VacationDetailsEntity> list;
    @BindView(R.id.submit)
    CircularProgressButton submit;
    LeaveRecordEntity leaveRecordEntity;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        ButterKnife.bind(this);
        initInfoTitleView("休假详情");
        mContext = VacationDetailsActivity.this;
        TAG = "TeamAssessmentActivity";
        initView();
        appholidayProcess(leaveRecordEntity.getID());

    }

    private void initView() {
        submit.setVisibility(View.GONE);
        /**
         * 初始化  填充数据
         */
        Intent intent = getIntent();
        leaveRecordEntity = intent.getParcelableExtra("leaveRecordEntity");
        ivIcon.setImageResource(leaveRecordEntity.getIcon());

        for (LeaveType leaveType : LeaveType.values()) {
            if (leaveRecordEntity.getType().equals(leaveType.getValue().getType())) {
                tvType.setText(leaveType.getValue().getName());
            }
        }


        tvTotal.setText("剩余" + getSickLeave(leaveRecordEntity.getStart(), leaveRecordEntity.getEnd()) + "天");
        tvTime.setText(leaveRecordEntity.getStart() + "到" + leaveRecordEntity.getEnd());
        tvReason.setText(leaveRecordEntity.getReason());

        /**
         * 是否显示销假的按钮
         */
        if (leaveRecordEntity.getState() == 9) {
//            boolean blnIsShow = OperationUtils.getBeOverdue(leaveRecordEntity.getEnd());
            int date = OperationUtils.getSickLeave(leaveRecordEntity.getStart(), leaveRecordEntity.getEnd());
            if (date > 0) {//日期在今天之前
                submit.setVisibility(View.VISIBLE);
            }
        }
        if (leaveRecordEntity.getState() == 11) {
            submit.setVisibility(View.GONE);
        }

        list = new ArrayList<>();
        adapter = new VacationDetailsAdapter(mContext, list, new VacationDetailsAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        /*recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));*/
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
        recycleview.setAdapter(adapter);
    }


    public void submit(int id, String day) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("day", day);

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayBackholy(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(100);
                    finish();
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


    /**
     * 销假的轨迹数据
     */
    private void appholidayProcess(int id) {
        showLoading("");

        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayProcess(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<VacationDetailsEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<VacationDetailsEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    list = response.getData();
                    if (ListUtils.isEmpty(list)) {
                        list = new ArrayList<>();
                    }
                    VacationDetailsEntity vacationDetailsEntity = new VacationDetailsEntity(1, leaveRecordEntity.getCreate_time(), "请等待上级审核");
                    list.add(vacationDetailsEntity);
                    hideLoading();
                    if (!ListUtils.isEmpty(list)) {
                        adapter.setList(list);
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

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                day = getSickLeave(leaveRecordEntity.getStart(), leaveRecordEntity.getEnd());
                if (day <= 0) {
                    Toasty.warning(mContext, "该假期时间已过，不能销假!").show();
                    return;
                }
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定销假吗?").setConfirmText("确定").setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                submit(leaveRecordEntity.getID(), day + "");
                            }
                        }).show();
                break;
        }
    }
}
