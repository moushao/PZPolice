package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.DetailTrackEntity;
import com.pvirtech.pzpolice.entity.MyInitiatedEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.adapter.TeamBuildingReviewAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 队伍建设考核  领导审核界面
 */
public class MyInitiatedReviewActivity extends BaseActivity {


    TeamBuildingReviewAdapter adapter;
    List<DetailTrackEntity.TrailBean> list = new ArrayList();
    String state = "";
    String type = "";
    DetailTrackEntity detailTrackEntity = new DetailTrackEntity();
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_initiated_review);
        ButterKnife.bind(this);
        initInfoTitleView("详情");
        mContext = MyInitiatedReviewActivity.this;
        TAG = "TeamAssessmentActivity";
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        MyInitiatedEntity teamBuildingEntity = bundle.getParcelable("data");
        if (null != teamBuildingEntity.getClassX()) {
            getData(teamBuildingEntity.getClassX(), teamBuildingEntity.getID());

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
                    String totalTime = "";
                    if (detailTrackEntity.getStart().length() == 10 && detailTrackEntity.getEnd().length() == 10) {
                        totalTime = OperationUtils.getDays(detailTrackEntity.getStart(), detailTrackEntity.getEnd()) + "天";
                    }
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
}
