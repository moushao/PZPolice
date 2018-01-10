package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.activity.task.MyTaskReviewActivity;
import com.pvirtech.pzpolice.ui.adapter.MyInitiatedBusinessAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by pd on 2017/5/12.
 * 低权限，我发起的代办事项里，查看已完成的交办任务和日常任务
 */

public class MyInitiatedBusinessFragment extends BaseFragment {
    List<MyTasksEntity> list = new ArrayList<>();
    MyInitiatedBusinessAdapter adapter;
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    String TYPE = "0";  //交办任务type=1，日常任务=0
    String STATE = "1";//只查询已完成的
    String endDate = TimeUtil.getYM();
    boolean overtime = false;
    @BindView(R.id.daily_tasks_recyclerview)
    RecyclerView dailyTasksRecyclerview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_myinitiated_business, container, false);
        initView(view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        indexO = 0;
        showLoading("");
        getMyRecord(true, indexO, endDate, TYPE, STATE, overtime);
    }

    private void initView() {
        adapter = new MyInitiatedBusinessAdapter(mContext, list, new MyInitiatedBusinessAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(MyTasksEntity entity) {
                Intent intent = new Intent(mContext, MyTaskReviewActivity.class);
                Bundle bundle = new Bundle();
                BusinessWorkEntity businessWorkEntity = new BusinessWorkEntity();
                businessWorkEntity.setAUDIT_STATE(entity.getAUDIT_STATE());
                businessWorkEntity.setDATE_STR(entity.getDATE_STR());
                businessWorkEntity.setID(entity.getID());
                businessWorkEntity.setNAME(entity.getNAME());
                businessWorkEntity.setTASK_DESC(entity.getTASK_DESC());
                businessWorkEntity.setTASK_NAME(entity.getTASK_NAME());
                businessWorkEntity.setTASK_NO(entity.getTASK_NO());
                businessWorkEntity.setTASK_TYPE(entity.getCASE_TYPE());
                businessWorkEntity.setTOTAL_SCORE(entity.getTOTAL_SCORE());
                businessWorkEntity.setComplete(entity.getCOMPLETED_COUNT());
                businessWorkEntity.setTotal(entity.getTOTAL_COUNT());
                businessWorkEntity.setCREATE_TIME(entity.getCREATE_TIME());
                bundle.putParcelable("data", businessWorkEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(MyTasksEntity entity) {
                return false;
            }

        });
        adapter.openLoadAnimation(PreferenceUtils.getPrefInt(mContext, "RecycleAnim", 0));
        adapter.isFirstOnly(!PreferenceUtils.getPrefBoolean(mContext, "isFirstOnly", false));
        adapter.openLoadAnimation();
        dailyTasksRecyclerview.setAdapter(adapter);

        indexO = adapter.getData().size();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMyRecord(false, indexO, endDate, TYPE, STATE, overtime);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO, endDate, TYPE, STATE, overtime);

            }
        });
        dailyTasksRecyclerview.setAdapter(adapter);
        dailyTasksRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));
    }


    /**
     * 查询代办事项
     */
    private void getMyRecord(final boolean blnIsRefresh, int indexOTemp, String endDate, String TYPE, String STATE, boolean overtime) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
//        data.addProperty("endDate", endDate);
//        data.addProperty("TYPE", TYPE);
        data.addProperty("STATE", STATE);
        data.addProperty("overtime", overtime);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        RetrofitHttp.provideClientApi().apptaskGetTaskByInfo(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MyTasksEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<MyTasksEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<MyTasksEntity> tempList = response.getData();

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
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
