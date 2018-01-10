package com.pvirtech.pzpolice.ui.activity.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.ScoreEntity;
import com.pvirtech.pzpolice.entity.ScoreTeamEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.activity.work.MyAssessmentActivity;
import com.pvirtech.pzpolice.ui.adapter.ScoreboardPersonlAdapter;
import com.pvirtech.pzpolice.ui.adapter.ScoreboardTeamAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ezy.ui.view.NoticeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 积分榜主页
 */
public class ScoreboardFragment extends BaseFragment {

    ScoreboardPersonlAdapter adapter;
    ScoreboardTeamAdapter scoreboardTeamAdapter;
    List<ScoreEntity> list;
    List<ScoreTeamEntity> scoreTeamEntityList;
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    List<String> arrayListNotices = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.tv_team)
    TextView tvTeam;
    @BindView(R.id.tv_porice_name)
    TextView tvPoriceName;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.notice)
    NoticeView notice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        mContext = getActivity();
        initView(view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        indexO = 0;
        getMyRecord(true, indexO);
        apprankMyscore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        tvPoriceName.setText(AppValue.getInstance().getmUserInfo().getNAME());
        department.setText(AppValue.getInstance().getmUserInfo().getUNIT_CODE());
        list = new ArrayList<>();
        adapter = new ScoreboardPersonlAdapter(mContext, list, new ScoreboardPersonlAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(ScoreEntity entity) {
                Intent intent = new Intent(mContext, MyAssessmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.INTENT_BUNDLE, entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ScoreEntity entity) {
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
                getMyRecord(false, indexO);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));


        scoreboardTeamAdapter = new ScoreboardTeamAdapter(mContext, scoreTeamEntityList, new ScoreboardTeamAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(ScoreEntity entity) {
            }

            @Override
            public boolean onItemLongClick(ScoreEntity entity) {
                return false;
            }
        });
        scoreboardTeamAdapter.openLoadAnimation(PreferenceUtils.getPrefInt(mContext, "RecycleAnim", 0));
        scoreboardTeamAdapter.isFirstOnly(!PreferenceUtils.getPrefBoolean(mContext, "isFirstOnly", false));
        scoreboardTeamAdapter.openLoadAnimation();


        arrayListNotices.add("李警官被评为三季度“执法之星");
        arrayListNotices.add("王警官被评为三季度“执法之星");
        arrayListNotices.add("张警官被评为三季度“执法之星");
        notice.start(arrayListNotices);

    }

    private void getMyRecord(final boolean blnIsRefresh, final int indexOTemp) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apprankList(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<ScoreEntity>>>() {


            @Override
            public void accept(@NonNull HttpResult<List<ScoreEntity>> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<ScoreEntity> scoreEntityList = response.getData();
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(scoreEntityList)) {
                            adapter.setEnableLoadMore(true);
                            int position = indexOTemp;
                            for (ScoreEntity entity : scoreEntityList) {
                                position++;
                                entity.setPosition(position);
                            }

                        } else {
                            scoreEntityList = new ArrayList<ScoreEntity>();
                            adapter.setEnableLoadMore(false);
                        }
                        adapter.setPosition(0);
                        adapter.setNewData(scoreEntityList);
                        mCurrentCounter = adapter.getData().size();
                        indexO = indexO + scoreEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(scoreEntityList)) {
                            int position = indexOTemp;
                            for (ScoreEntity entity : scoreEntityList) {
                                position++;
                                entity.setPosition(position);
                            }
                            adapter.addData(scoreEntityList);
                            mCurrentCounter = adapter.getData().size();
                            adapter.loadMoreComplete();
                            indexO = indexO + scoreEntityList.size();
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
     * 获取team 的积分
     */
    private void getMyRecordTeam(final boolean blnIsRefresh, final int indexOTemp) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("indexO", indexOTemp);
        data.addProperty("indexT", Constant.PAGE_SIZE);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apprankGrouprank(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<ScoreTeamEntity>>>() {


            @Override
            public void accept(@NonNull HttpResult<List<ScoreTeamEntity>> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<ScoreTeamEntity> scoreTeamEntityList = response.getData();
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(scoreTeamEntityList)) {
                            int position = indexOTemp;
                            for (ScoreTeamEntity entity : scoreTeamEntityList) {
                                position++;
                                entity.setPosition(position);
                            }
                            scoreboardTeamAdapter.setEnableLoadMore(true);
                        } else {
                            scoreTeamEntityList = new ArrayList<ScoreTeamEntity>();
                            scoreboardTeamAdapter.setEnableLoadMore(false);
                        }
                        scoreboardTeamAdapter.setPosition(0);
                        scoreboardTeamAdapter.setNewData(scoreTeamEntityList);
                        mCurrentCounter = scoreboardTeamAdapter.getData().size();
                        indexO = indexO + scoreTeamEntityList.size();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    } else {//加载更多功能
                        if (!ListUtils.isEmpty(scoreTeamEntityList)) {
                            int position = indexOTemp;
                            for (ScoreTeamEntity entity : scoreTeamEntityList) {
                                position++;
                                entity.setPosition(position);
                            }
                            scoreboardTeamAdapter.addData(scoreTeamEntityList);
                            mCurrentCounter = scoreboardTeamAdapter.getData().size();
                            scoreboardTeamAdapter.loadMoreComplete();
                            indexO = indexO + scoreTeamEntityList.size();
                            if (mCurrentCounter >= TOTAL_COUNT) {
                                scoreboardTeamAdapter.loadMoreEnd(false);
                            }
                        } else {
                            if (mCurrentCounter >= TOTAL_COUNT) {
                                scoreboardTeamAdapter.loadMoreEnd(false);
                            } else {
                                scoreboardTeamAdapter.loadMoreFail();
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
                        scoreboardTeamAdapter.loadMoreFail();
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
                    scoreboardTeamAdapter.loadMoreFail();
                }
                L.d("Error");
                showError("");
            }
        }));

    }

    private void apprankMyscore() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apprankMyscore(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    tvPoint.setText(response.getData().toString());
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showError("");
            }
        }));

    }


    @OnClick({R.id.tv_personal, R.id.tv_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_personal:
                tvPersonal.setTextColor(mContext.getResources().getColor(R.color.text_orange));
                tvTeam.setTextColor(mContext.getResources().getColor(R.color.text_black));
                recyclerView.setAdapter(adapter);
                swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        indexO = 0;
                        getMyRecord(true, indexO);
                    }
                });
                adapter.setNewData(null);
                mCurrentCounter = 0;
                indexO = 0;
                getMyRecord(true, indexO);
                break;
            case R.id.tv_team:
                tvTeam.setTextColor(mContext.getResources().getColor(R.color.text_orange));
                tvPersonal.setTextColor(mContext.getResources().getColor(R.color.text_black));
                recyclerView.setAdapter(scoreboardTeamAdapter);
                swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        indexO = 0;
                        getMyRecordTeam(true, indexO);
                    }
                });
                scoreboardTeamAdapter.setNewData(null);
                mCurrentCounter = 0;
                indexO = 0;
                getMyRecordTeam(true, indexO);
                break;
        }
    }
}
