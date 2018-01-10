package com.pvirtech.pzpolice.ui.activity.work.assessment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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
import com.pvirtech.pzpolice.entity.TeamAssessmentRecordEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.adapter.TeamAssenssmentRecordAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/3/26.
 * 假期列表主界面
 * 销假主界面
 */

public class TeamAssessmentRecordFragment extends BaseFragment {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    TeamAssenssmentRecordAdapter adapter;
    List<TeamAssessmentRecordEntity> mData = new ArrayList<>();
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_team_assessment_record, container, false);
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
        adapter = new TeamAssenssmentRecordAdapter(mContext, mData, new TeamAssenssmentRecordAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(TeamAssessmentRecordEntity entity) {

            }

            @Override
            public boolean onItemLongClick(TeamAssessmentRecordEntity entity) {
                return false;
            }

            @Override
            public boolean onClickButton(TeamAssessmentRecordEntity entity) {
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
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
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
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptbuildList(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<TeamAssessmentRecordEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<TeamAssessmentRecordEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<TeamAssessmentRecordEntity> leaveRecordEntityList = response.getData();

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

}
