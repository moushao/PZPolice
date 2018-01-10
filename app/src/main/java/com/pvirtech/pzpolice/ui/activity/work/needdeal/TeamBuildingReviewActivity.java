package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.DetailTrackEntity;
import com.pvirtech.pzpolice.entity.TeamBuildingEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.ui.adapter.TeamBuildingReviewAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;

import java.util.ArrayList;
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
 * 队伍建设考核  领导审核界面
 */
public class TeamBuildingReviewActivity extends BaseActivity {

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

    TeamBuildingReviewAdapter adapter;
    List<DetailTrackEntity.TrailBean> list = new ArrayList();
    String state = "";
    String type = "";
    List<DictionariesEntity> listReason = new ArrayList<>();

    DetailTrackEntity detailTrackEntity = new DetailTrackEntity();
    @BindView(R.id.submit)
    CircularProgressButton submit;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(R.id.cancle)
    CircularProgressButton cancle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.ll_botton)
    LinearLayout llBotton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_building_review);
        ButterKnife.bind(this);
        initInfoTitleView("详情");
        mContext = TeamBuildingReviewActivity.this;
        TAG = "TeamAssessmentActivity";
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TeamBuildingEntity teamBuildingEntity = bundle.getParcelable("data");
        if (null != teamBuildingEntity.getClassX()) {
            getData(teamBuildingEntity.getClassX(), teamBuildingEntity.getID());
            if (teamBuildingEntity.getAUDIT_STATE() == ToExamineEnum.STATE13.getToExamineEntity().getState()) {
                llBotton.setVisibility(View.GONE);
            }
        }
        type = teamBuildingEntity.getClassX();
        toolbar.setTitle(teamBuildingEntity.getType() + "详情");
        adapter = new TeamBuildingReviewAdapter(mContext, list, new TeamBuildingReviewAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

    private void getData(String type, String id) {
        showLoading("");
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("type", type);
        data.addProperty("id", id);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleDetail(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<DetailTrackEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<DetailTrackEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    detailTrackEntity = response.getData();

                    if (!TextUtils.isEmpty(detailTrackEntity.getDept())) {
                        tvDepartment.setText(detailTrackEntity.getDept());
                    }
                    if (!TextUtils.isEmpty(detailTrackEntity.getNo())) {
                        tvNumber.setText(detailTrackEntity.getNo());
                    }
                    if (!TextUtils.isEmpty(detailTrackEntity.getType())) {
                        tvReason.setText(detailTrackEntity.getType());
                    }
                    tvTime.setText(detailTrackEntity.getStart() + "到" + detailTrackEntity.getEnd());
                    String totalTime = OperationUtils.getDays(detailTrackEntity.getStart(), detailTrackEntity.getEnd()) + "天";
                    tvTotalTime.setText(totalTime);
                    state = detailTrackEntity.getState() + "";

                    tvInfoName.setText(detailTrackEntity.getName());
                    tvInfoDepartment.setText(detailTrackEntity.getDept());

                    DetailTrackEntity.TrailBean trailBean = new DetailTrackEntity.TrailBean();
                    trailBean.setAUDIT_STATE(-1);
                    trailBean.setAUDIT_TIME(detailTrackEntity.getCREATE_TIME());
                    trailBean.setAUDIT_USER(detailTrackEntity.getName());
                    list = detailTrackEntity.getTrail();
                    list.add(trailBean);
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

    public void submit(String type, String state, String id, int pass, String reasonId) {
        showLoading("");

        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("type", type);
        data.addProperty("state", state);
        data.addProperty("id", id);
        data.addProperty("pass", pass);
        data.addProperty("TDREASON", reasonId);

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleApproal(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    hideLoading();
                    finish();
                } else {
                    showError(response.getResultMessage());
                }
                L.d("sucess");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)).show();
                showError("");
            }
        }));

    }

    @OnClick({R.id.cancle, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                getReason();
               /* new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定驳回吗?").setContentText("你将要驳回该申请").setConfirmText("确定")
                        .setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        submit(type, state, detailTrackEntity.getID(), 0);
                    }
                }).show();*/

                break;
            case R.id.submit:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定通过吗?").setContentText("你将要通过该申请").setConfirmText("确定")
                        .setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        submit(type, state, detailTrackEntity.getID(), 1, "");
                    }
                }).show();

                break;
        }
    }


    /**
     * 取得驳回理由
     */
    private void getReason() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayTdreson(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<DictionariesEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<DictionariesEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    listReason = response.getData();
                    hideLoading();
                    if (!ListUtils.isEmpty(listReason)) {
                        tvReason.setText(listReason.get(0).getNAME());
                        showReject();
                    } else {
                        submit(type, state, detailTrackEntity.getID(), 0, "");
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

    /**
     * 显示驳回的理由
     */
    private void showReject() {
        DictionariesDialog dialog = new DictionariesDialog();
        dialog.showDialog(mContext, "", tvReason.getText().toString(), listReason, new
                OnItemSelectedListener() {
                    @Override
                    public void onSelected(DictionariesEntity dictionariesEntity) {
                        submit(type, state, detailTrackEntity.getID(), 0,
                                dictionariesEntity.getNAME());
                    }
                });
    }

}
