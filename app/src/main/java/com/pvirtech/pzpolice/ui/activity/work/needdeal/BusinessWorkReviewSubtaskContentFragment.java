package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.SubtaskEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.adapter.BusinessWorkReviewSubtaskContentAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pd on 2017/4/24.
 * 子任务列表
 */

public class BusinessWorkReviewSubtaskContentFragment extends BaseFragment {
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
    @BindView(R.id.ll_informatino)
    LinearLayout llInformatino;
    BusinessWorkEntity businessWorkEntity;
    List<SubtaskEntity> list = new ArrayList<>();
    BusinessWorkReviewSubtaskContentAdapter adapter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_business_work_review_subtask_content,
                container, false);
        initView(view);
        initView();
        getData(businessWorkEntity.getID(), businessWorkEntity.getTASK_TYPE() + "");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        tvNumber.setText(businessWorkEntity.getTASK_NO());
        tvDepartment.setText(businessWorkEntity.getNAME());
        tvTotalTime.setText(businessWorkEntity.getTASK_DESC());
        tvTime.setText(businessWorkEntity.getDATE_STR());
        tvReason.setText("已完成(" + businessWorkEntity.getComplete() + "/" + businessWorkEntity.getTotal() + ")");

        adapter = new BusinessWorkReviewSubtaskContentAdapter(mContext, list, new
                BusinessWorkReviewSubtaskContentAdapter.OnRecyclerViewListener() {
                    @Override
                    public void onItemClick(int position) {
                        SubtaskEntity subtaskEntity = list.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("task_id",businessWorkEntity.getID());
                        bundle.putParcelable("data", subtaskEntity);
                        Intent intent = new Intent(mContext, CaseInfoActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public boolean onItemLongClick(int position) {
                        return false;
                    }
                });
        recycleview.setAdapter(adapter);
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));

    }


    private void getData(String id, String task_type) {
        showLoading("");
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("task_type", task_type);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskTaskcase(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<SubtaskEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<SubtaskEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    list = response.getData();
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public BusinessWorkEntity getBusinessWorkEntity() {
        return businessWorkEntity;
    }

    public void setBusinessWorkEntity(BusinessWorkEntity businessWorkEntity) {
        this.businessWorkEntity = businessWorkEntity;
    }
}
