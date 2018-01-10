package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.pvirtech.pzpolice.entity.TeamBuildingEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.flipshare.FlipShareView;
import com.pvirtech.pzpolice.third.flipshare.ShareItem;
import com.pvirtech.pzpolice.ui.adapter.TeamBuildingAdapter;
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
 * 代办事项，队伍建设
 */

public class TeamBuildingFragment extends BaseFragment {
    List<TeamBuildingEntity> list = new ArrayList<>();
    TeamBuildingAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_team_building_layout, container, false);
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

        adapter = new TeamBuildingAdapter(mContext, list, new TeamBuildingAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(TeamBuildingEntity entity) {
                Intent intent = new Intent(mContext, TeamBuildingReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(TeamBuildingEntity entity) {
                return false;
            }

            @Override
            public boolean onClickButton(TeamBuildingEntity entity) {
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
                try {
                    FlipShareView share = new FlipShareView.Builder(getActivity(), llFilter).addItem(new ShareItem("所有", Color.WHITE, 0xff83c6c2,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("销假单", Color.WHITE, 0xff3f81c1,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("请假单", Color.WHITE, 0xff4f68b0,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("工作时间", Color.WHITE, 0xffa686ba,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).setBackgroundColor(0x60000000).create();
                    share.setOnFlipClickListener(new FlipShareView.OnFlipClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            switch (position) {//all,worktime,holiday,sickday
                                case 0:
                                    tvFilter.setText("所有");
                                    showLoading("");
                                    indexO = 0;
                                    type = "all";
                                    getMyRecord(true);
                                    break;
                                case 1:
                                    tvFilter.setText("销假单");
                                    showLoading("");
                                    indexO = 0;
                                    type = "sickday";
                                    getMyRecord(true);
                                    break;
                                case 2:
                                    tvFilter.setText("请假单");
                                    showLoading("");
                                    indexO = 0;
                                    type = "holiday";
                                    getMyRecord(true);
                                    break;
                                case 3:
                                    tvFilter.setText("工作时间");
                                    showLoading("");
                                    indexO = 0;
                                    type = "worktime";
                                    getMyRecord(true);
                                    break;
                            }
                        }

                        @Override
                        public void dismiss() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleAlist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<TeamBuildingEntity>>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<List<TeamBuildingEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<TeamBuildingEntity> teamBuildingEntityList = response.getData();
                    if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                        for (TeamBuildingEntity temp : teamBuildingEntityList) {
                            temp.setAUDIT_STATE(ToExamineEnum.STATE1.getToExamineEntity().getState());
                        }
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            teamBuildingEntityList = new ArrayList<TeamBuildingEntity>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(teamBuildingEntityList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + teamBuildingEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                            adapter.addData(teamBuildingEntityList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + teamBuildingEntityList.size();
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
     * 查询代办事项-->已审批的
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
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleHalist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<TeamBuildingEntity>>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<List<TeamBuildingEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<TeamBuildingEntity> teamBuildingEntityList = response.getData();
                    if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                        for (TeamBuildingEntity temp : teamBuildingEntityList) {
                            temp.setAUDIT_STATE(ToExamineEnum.STATE13.getToExamineEntity().getState());
                        }
                    }
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            teamBuildingEntityList = new ArrayList<TeamBuildingEntity>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setNewData(teamBuildingEntityList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + teamBuildingEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                            adapter.addData(teamBuildingEntityList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + teamBuildingEntityList.size();
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
