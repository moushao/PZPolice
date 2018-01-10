package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.pvirtech.pzpolice.entity.MyInitiatedEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.flipshare.FlipShareView;
import com.pvirtech.pzpolice.third.flipshare.ShareItem;
import com.pvirtech.pzpolice.ui.adapter.MyInitiatedAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pd on 2017/5/12.
 * 我发起的，待办事项，查看队伍建设列表
 */

public class MyInitiatedTeamFragment extends BaseFragment {
    List<MyInitiatedEntity> list = new ArrayList<>();
    MyInitiatedAdapter adapter;
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    String type = "all";

    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.root_view)
    LinearLayout rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_myinitiated_team, container, false);
        initView(view);
        initView();
        showLoading("");
        getMyRecord(true, indexO, type);
        return view;
    }

    private void initView() {
        tvFilter.setText("所有");

        adapter = new MyInitiatedAdapter(mContext, list, new MyInitiatedAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(MyInitiatedEntity entity) {
                Intent intent = new Intent(mContext, MyInitiatedReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(MyInitiatedEntity entity) {
                return false;
            }

            @Override
            public boolean onClickButton(MyInitiatedEntity entity) {
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
                getMyRecord(false, indexO, type);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO, type);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

    @OnClick({R.id.ll_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_filter:
                try {
                    FlipShareView share = new FlipShareView.Builder(getActivity(), llFilter).addItem(new ShareItem("所有", Color.WHITE,
                            0xff83c6c2,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("销假单", Color.WHITE, 0xff3f81c1,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("请假单", Color.WHITE, 0xff4f68b0,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).addItem(new ShareItem("工作时间", Color.WHITE, 0xffa686ba,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.type))).setBackgroundColor(0x60000000).create();
                    share.setOnFlipClickListener(new FlipShareView.OnFlipClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            switch (position) {//all,worktime,holiday,sickday
                                case 0:
                                    if (!type.equals("all")) {
                                        tvFilter.setText("所有");
                                        showLoading("");
                                        indexO = 0;
                                        type = "all";
                                        getMyRecord(true, indexO, type);
                                    }
                                    break;
                                case 1:
                                    if (!type.equals("sickday")) {
                                        tvFilter.setText("销假单");
                                        showLoading("");
                                        indexO = 0;
                                        type = "sickday";
                                        getMyRecord(true, indexO, type);
                                    }
                                    break;
                                case 2:
                                    if (!type.equals("holiday")) {
                                        tvFilter.setText("请假单");
                                        showLoading("");
                                        indexO = 0;
                                        type = "holiday";
                                        getMyRecord(true, indexO, type);
                                    }
                                    break;
                                case 3:
                                    if (!type.equals("worktime")) {
                                        tvFilter.setText("工作时间");
                                        showLoading("");
                                        indexO = 0;
                                        type = "worktime";
                                        getMyRecord(true, indexO, type);
                                    }
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

    /**
     * 查询代办事项
     */
    private void getMyRecord(final boolean blnIsRefresh, final int indexOTemp, String type) {
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
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appscheduleMylist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MyInitiatedEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<MyInitiatedEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<MyInitiatedEntity> teamBuildingEntityList = response.getData();
                    if (response.getTotal() != 0) {
                        TOTAL_COUNT = response.getTotal();
                    }
                    hideLoading();
                    if (blnIsRefresh) {//是下拉刷新操作
                        if (!ListUtils.isEmpty(teamBuildingEntityList)) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            teamBuildingEntityList = new ArrayList<MyInitiatedEntity>();
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
