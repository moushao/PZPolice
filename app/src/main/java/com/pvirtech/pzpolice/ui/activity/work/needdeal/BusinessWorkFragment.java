package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.adapter.BusinessWorkAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/5.
 * 领导  代办事项，业务工作
 */

public class BusinessWorkFragment extends BaseFragment {
    BusinessWorkAdapter adapter;
    List<BusinessWorkEntity> list = new ArrayList<>();

    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    String type = "all";
    BottomNavigationView navigation;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.tv_filter)
    TextView tvFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_business_work_layout, container, false);
        initView(view);
        initView();
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        indexO = 0;
        showLoading("");
        getMyRecord(true);
    }

    private void initView() {
        tvFilter.setText("所有");

        adapter = new BusinessWorkAdapter(mContext, list, new BusinessWorkAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(BusinessWorkEntity entity) {
                Intent intent = new Intent(mContext, BusinessWorkReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(BusinessWorkEntity entity) {
                return false;
            }

        });
        adapter.openLoadAnimation(PreferenceUtils.getPrefInt(mContext, "RecycleAnim", 0));
        adapter.isFirstOnly(!PreferenceUtils.getPrefBoolean(mContext, "isFirstOnly", false));
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

        indexO = adapter.getData().size();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMyRecord(false);
            }
        }, recyclerView);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.ll_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_filter:
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fm = getFragmentManager();
            fm.executePendingTransactions();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.need_to_deal:
                    indexO = 0;
                    getMyRecordNeedDeal(true, indexO, type);
                    return true;
                case R.id.already_deal:
                    indexO = 0;
                    getMyRecordDown(true, indexO, type);
                    return true;

            }
            return false;
        }

    };

    private void getMyRecord(boolean blnIsRefresh) {
        if (navigation.getSelectedItemId() == R.id.need_to_deal) {
            getMyRecordNeedDeal(blnIsRefresh, indexO, type);
        } else if (navigation.getSelectedItemId() == R.id.already_deal) {
            getMyRecordDown(blnIsRefresh, indexO, type);
        }
    }

    /**
     * 查询代办事项
     */
    private void getMyRecordNeedDeal(final boolean blnIsRefresh, final int indexOTemp, String type) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        data.addProperty("type", type);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleBlist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<BusinessWorkEntity>>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<List<BusinessWorkEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<BusinessWorkEntity> tempList = response.getData();
                    if (!ListUtils.isEmpty(tempList)) {
                        for (BusinessWorkEntity temp : tempList) {
                            temp.setAUDIT_STATE(ToExamineEnum.STATE1.getToExamineEntity().getState());
                        }
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(tempList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            tempList = new ArrayList<>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(tempList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + tempList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(tempList)) {
                            adapter.addData(tempList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + tempList.size();
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
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
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
     * 查询代办事项----已完成的
     */
    private void getMyRecordDown(final boolean blnIsRefresh, final int indexOTemp, String type) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        data.addProperty("type", type);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleHblist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<BusinessWorkEntity>>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<List<BusinessWorkEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<BusinessWorkEntity> tempList = response.getData();
                    if (!ListUtils.isEmpty(tempList)) {
                        for (BusinessWorkEntity temp : tempList) {
                            temp.setAUDIT_STATE(ToExamineEnum.STATE13.getToExamineEntity().getState());
                        }
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(tempList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            tempList = new ArrayList<BusinessWorkEntity>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(tempList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + tempList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(tempList)) {
                            adapter.addData(tempList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + tempList.size();
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
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
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

}
