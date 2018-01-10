package com.pvirtech.pzpolice.ui.activity.log;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkLogEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.ui.adapter.WorkLogAdapter;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.pvirtech.pzpolice.R.id.tv_info_date;

/**
 * Created by Administrator on 2017/3/26.
 * 日常任务主界面
 */

public class ReadWorkLogFragment extends BaseFragment {

    ArrayList<WorkLogEntity> list = new ArrayList<>();
    WorkLogAdapter adapter;
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;

    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_department)
    TextView tvInfoDepartment;
    @BindView(tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_read_work_log, container, false);
        initInfoTitleView(view);
        initView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        indexO = 0;
        getMyRecord(true, indexO, tvInfoDate.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        adapter = new WorkLogAdapter(mContext, list, new WorkLogAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(WorkLogEntity leaveRecordEntity) {
                Intent intent = new Intent(mContext, ReviewWorkLogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.INTENT_BUNDLE, leaveRecordEntity);
                intent.putExtras(bundle);
                startActivity(intent);
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
                getMyRecord(false, indexO, tvInfoDate.getText().toString());
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO, tvInfoDate.getText().toString());
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));

        tvInfoDate.setText(TimeUtil.getYM());//设置年月
    }


    private void getMyRecord(final boolean blnIsRefresh, final int indexOTemp, String selectdate) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        data.addProperty("selectdate", selectdate);

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appWorkLogGetWorkLogByDate(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<WorkLogEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<WorkLogEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<WorkLogEntity> workLogEntityList = response.getData();
                    if (!ListUtils.isEmpty(workLogEntityList)) {
//                        dealData(workLogEntityList);
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(workLogEntityList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            workLogEntityList = new ArrayList<>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(workLogEntityList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + workLogEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(workLogEntityList)) {
                            adapter.addData(workLogEntityList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + workLogEntityList.size();
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


    @OnClick({R.id.lin_chose_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_chose_date:
                DatePickerDialog pickerDialog = new DatePickerDialog();
                pickerDialog.showDialogYM(getActivity(), tvInfoDate.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvInfoDate.setText(data);
                        indexO = 0;
                        getMyRecord(true, indexO, tvInfoDate.getText().toString());
                    }
                });
                break;
        }
    }
}
