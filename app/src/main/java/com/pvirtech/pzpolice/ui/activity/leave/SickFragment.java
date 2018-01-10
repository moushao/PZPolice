package com.pvirtech.pzpolice.ui.activity.leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveRecordEntity;
import com.pvirtech.pzpolice.enumeration.LeaveType;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.third.DictionariesDialog;
import com.pvirtech.pzpolice.ui.adapter.SickAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
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


/**
 * Created by Administrator on 2017/3/26.
 * 假期列表主界面
 * 销假主界面
 */

public class SickFragment extends BaseFragment {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    SickAdapter adapter;
    List<LeaveRecordEntity> mData = new ArrayList<>();
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    String type = null;
    DictionariesDialog dictionariesDialog = new DictionariesDialog();
    List<DictionariesEntity> dictionariesEntityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sick, container, false);
        initInfoTitleView(view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading("");
        indexO = 0;
        getMyRecord(true, indexO, tvInfoDate.getText().toString(), type);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        adapter = new SickAdapter(mContext, mData, new SickAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(LeaveRecordEntity leaveRecordEntity) {
                Intent intent = new Intent(mContext, VacationDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("leaveRecordEntity", leaveRecordEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(final LeaveRecordEntity leaveRecordEntity) {

                return false;
            }

            @Override
            public boolean onClickButton(final LeaveRecordEntity leaveRecordEntity) {
                //销假
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("你确定销假吗?").setConfirmText("确定").setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sickLeave(leaveRecordEntity.getID(), OperationUtils.getSickLeave(leaveRecordEntity.getStart(), leaveRecordEntity
                                        .getEnd()) + "");
                                sDialog.dismissWithAnimation();
                            }
                        }).show();

                return false;
            }
        });
        adapter.openLoadAnimation(PreferenceUtils.getPrefInt(mContext, "RecycleAnim", 0));
        adapter.isFirstOnly(!PreferenceUtils.getPrefBoolean(mContext, "isFirstOnly", false));
        adapter.openLoadAnimation();
        recycleview.setAdapter(adapter);

        indexO = adapter.getData().size();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMyRecord(false, indexO, tvInfoDate.getText().toString(), type);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), type);
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));

        tvInfoDate.setText(TimeUtil.getYM());//设置年月

        DictionariesEntity dictionariesEntity = new DictionariesEntity("", "所有");
        dictionariesEntityList.add(dictionariesEntity);
        for (LeaveType leaveType : LeaveType.values()) {
            DictionariesEntity temp = new DictionariesEntity(leaveType.getValue().getType(), leaveType.getValue().getName());
            dictionariesEntityList.add(temp);
        }
        tvFilter.setText("所有");
    }

    /**
     * 查询我的请假记录
     */
    private void getMyRecord(final boolean blnIsRefresh, final int indexOTemp, String date, String type) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        data.addProperty("date", date);
        if (!TextUtils.isEmpty(type)) {
            data.addProperty("type", type);
        }
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayMyapply(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<LeaveRecordEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<LeaveRecordEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<LeaveRecordEntity> leaveRecordEntityList = response.getData();
                    if (!ListUtils.isEmpty(leaveRecordEntityList)) {
                        dealData(leaveRecordEntityList);
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(leaveRecordEntityList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            leaveRecordEntityList = new ArrayList<>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(leaveRecordEntityList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + leaveRecordEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(leaveRecordEntityList)) {
                            adapter.addData(leaveRecordEntityList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + leaveRecordEntityList.size();
                            if (mCurrentCounter >= TOTAL_COUNT) {
                                adapter.loadMoreEnd(false);
                            }
                        } else {
                            if (mCurrentCounter >= TOTAL_COUNT) {
                                adapter.loadMoreEnd(false);
                            } else {
                                adapter.loadMoreFail();
                            }
                        }
                        swipeLayout.setEnabled(true);
                    }
                } else {
                    if (blnIsRefresh) {
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {
                        adapter.loadMoreFail();
                    }
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (blnIsRefresh) {
                    if (swipeLayout.isRefreshing()) {
                        swipeLayout.setRefreshing(false);
                    }
                } else {
                    adapter.loadMoreFail();
                }
                L.d("Error");
                showError("");
            }
        }));

    }


    /**
     * 本地校验数据
     */
    private void dealData(List<LeaveRecordEntity> list) {
        for (LeaveRecordEntity entity : list) {
            for (LeaveType leaveType : LeaveType.values()) {
                if (entity.getType().equals(leaveType.getValue().getType())) {
                    entity.setName(leaveType.getValue().getName());
                    entity.setIcon(leaveType.getValue().getIcon());
                    break;
                }
            }
        }
    }


    @OnClick({R.id.lin_chose_date, R.id.ll_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_chose_date:
                DatePickerDialog pickerDialog = new DatePickerDialog();
                pickerDialog.showDialogYM(getActivity(), tvInfoDate.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvInfoDate.setText(data);
                        indexO = 0;
                        getMyRecord(true, indexO, tvInfoDate.getText().toString(), type);
                    }
                });
                break;
            case R.id.ll_filter:

                dictionariesDialog.showDialog(mContext, "", tvFilter.getText().toString(), dictionariesEntityList, new OnItemSelectedListener() {
                    @Override
                    public void onSelected(DictionariesEntity dictionariesEntity) {
                        if (dictionariesEntity.getNAME().equals("所有")) {
                            type = null;
                            indexO = 0;
                            tvFilter.setText("所有");
                            getMyRecord(true, indexO, tvInfoDate.getText().toString(), type);
                        } else {
                            type = dictionariesEntity.getID();
                            indexO = 0;
                            getMyRecord(true, indexO, tvInfoDate.getText().toString(), type);
                            tvFilter.setText(dictionariesEntity.getNAME());
                        }
                    }
                });
                break;
        }
    }

    public void sickLeave(int id, String day) {

        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("day", day);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appholidayBackholy(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                } else {
                    Toasty.error(mContext, response.getResultMessage()).show();
                }
                hideLoading();
                L.d("sucess");
                onResume();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                showError("");
            }
        }));


    }
}
