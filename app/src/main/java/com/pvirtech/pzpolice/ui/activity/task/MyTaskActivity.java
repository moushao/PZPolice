package com.pvirtech.pzpolice.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.entity.TaskPreviewEntity;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.TaskTypeEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.ui.adapter.MyTaskAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.PreferenceUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.pvirtech.pzpolice.http.RetrofitHttp.provideClientApi;

/**
 * 我的任务主界面
 */
public class MyTaskActivity extends BaseActivity {
    List<MyTasksEntity> list = new ArrayList<>();
    MyTaskAdapter adapter;
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    String TYPE = "0";  //交办任务type=1，日常任务=0
    String STATE = null;//全部 STATE为空
    boolean overtime = false;
    TaskPreviewEntity taskPreviewEntity;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.bt_all)
    Button btAll;
    @BindView(R.id.under_line_bt_all)
    View underLineBtAll;
    @BindView(R.id.bt_not_finish)
    Button btNotFinish;
    @BindView(R.id.under_line_bt_not_finish)
    View underLineBtNotFinish;
    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.under_line_bt_finish)
    View underLineBtFinish;
    @BindView(R.id.bt_over_time)
    Button btOverTime;
    @BindView(R.id.under_line_bt_over_time)
    View underLineBtOverTime;
    @BindView(R.id.daily_tasks_recyclerview)
    RecyclerView dailyTasksRecyclerview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String count = "";
            switch (item.getItemId()) {
                case R.id.item1:
                    TYPE = TaskTypeEnum.DAILIY.getValue().getID();//日常任务
                    indexO = 0;
                    getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);

                    if (null != taskPreviewEntity && null != taskPreviewEntity.getDAILYCOUNT()) {
                        count = "(" + taskPreviewEntity.getDAILYCOUNT().getCOMPLETE() + "/" + (taskPreviewEntity.getDAILYCOUNT().getCOMPLETE() +
                                taskPreviewEntity.getDAILYCOUNT().getNOTCOMPLETE()) + ")";
                    }
                    tvTaskName.setText("日常任务" + count);
                    return true;
                case R.id.item2:
                    TYPE = TaskTypeEnum.ASSIGNED.getValue().getID();//交办任务
                    indexO = 0;
                    getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                    if (null != taskPreviewEntity && null != taskPreviewEntity.getASSIGNCOUNT()) {
                        count = "(" + taskPreviewEntity.getASSIGNCOUNT().getCOMPLETE() + "/" + (taskPreviewEntity.getASSIGNCOUNT().getCOMPLETE() +
                                taskPreviewEntity.getASSIGNCOUNT().getNOTCOMPLETE()) + ")";
                    }
                    tvTaskName.setText("交办任务" + count);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        mContext = MyTaskActivity.this;
        initTitleView(mContext.getResources().getString(R.string.my_task));
        TAG = "MyTaskActivity";
        initData();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        indexO = 0;
        showLoading("");
        apptaskIndex(tvInfoDate.getText().toString());
        getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            TYPE = intent.getStringExtra("data");
            if (TextUtils.isEmpty(TYPE)) {
                TYPE = "0";
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (TYPE.equals(TaskTypeEnum.DAILIY.getValue().getID())) {
            navigation.setSelectedItemId(R.id.item1);
        } else if (TYPE.equals(TaskTypeEnum.ASSIGNED.getValue().getID())) {
            navigation.setSelectedItemId(R.id.item2);
        }

        adapter = new MyTaskAdapter(mContext, list, new MyTaskAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(MyTasksEntity entity) {
                if (entity.getSTATE() == 1 || entity.getSTATE() == -1) {
                    Intent intent = new Intent(mContext, MyTaskReviewActivity.class);
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
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", businessWorkEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent = OperationUtils.getCaseActivityIntent(mContext, entity.getCASE_TYPE(), intent);
                    if (null != intent.getClass()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.CASE_COME_TYPE, CaseComeEnum.TASK.getValue());
                        bundle.putParcelable("data", entity);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
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
                getMyRecord(false, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                indexO = 0;
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                if (null == taskPreviewEntity) {
                    apptaskIndex(tvInfoDate.getText().toString());
                }

            }
        });
        dailyTasksRecyclerview.setAdapter(adapter);
        dailyTasksRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));

//        tvTaskName.setText("日常任务");
        hideUnderLine();
        underLineBtAll.setVisibility(View.VISIBLE);
        tvInfoDate.setText(TimeUtil.getYM());
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
        data.addProperty("endDate", endDate);
        data.addProperty("TYPE", TYPE);
        data.addProperty("STATE", STATE);
        data.addProperty("overtime", overtime);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        if (blnIsRefresh) {
        } else {
            swipeLayout.setEnabled(false);
        }
        Disposable disposable = RetrofitHttp.provideClientApi().apptaskGetTaskByInfo(httpSubmit).subscribeOn(Schedulers.io()).observeOn
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
        addDisposable(disposable);

    }

    /**
     * 第一页任务查询 ，统计任务个数
     */
    private void apptaskIndex(String endDate) {
        JsonObject data = new JsonObject();
        data.addProperty("endDate", endDate);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(provideClientApi().apptaskIndex(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<TaskPreviewEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<TaskPreviewEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (null == response.getData()) {
                    } else {
                        taskPreviewEntity = response.getData();
                        String count = "";
                        if (TYPE.equals(TaskTypeEnum.DAILIY.getValue().getID())) {
                            if (null != taskPreviewEntity && null != taskPreviewEntity.getDAILYCOUNT()) {
                                count = "(" + taskPreviewEntity.getDAILYCOUNT().getCOMPLETE() + "/" + (taskPreviewEntity.getDAILYCOUNT().getCOMPLETE
                                        () +
                                        taskPreviewEntity.getDAILYCOUNT().getNOTCOMPLETE()) + ")";
                            }
                            tvTaskName.setText(TaskTypeEnum.DAILIY.getValue().getNAME() + count);
                        } else if (TYPE.equals(TaskTypeEnum.ASSIGNED.getValue().getID())) {
                            if (null != taskPreviewEntity && null != taskPreviewEntity.getASSIGNCOUNT()) {
                                count = "(" + taskPreviewEntity.getASSIGNCOUNT().getCOMPLETE() + "/" + (taskPreviewEntity.getASSIGNCOUNT()
                                        .getCOMPLETE() +
                                        taskPreviewEntity.getASSIGNCOUNT().getNOTCOMPLETE()) + ")";
                            }
                            tvTaskName.setText(TaskTypeEnum.ASSIGNED.getValue().getNAME() + count);
                        }

                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d(TAG, throwable.getMessage());
                showError("");
                showHttpError();
            }
        }));
    }

    @OnClick({R.id.lin_chose_date, R.id.bt_all, R.id.bt_not_finish, R.id.bt_finish, R.id.bt_over_time})
    public void onViewClicked(View view) {
        hideUnderLine();
        switch (view.getId()) {
            case R.id.lin_chose_date:
                DatePickerDialog pickerDialog = new DatePickerDialog();
                pickerDialog.showDialogYM(this, tvInfoDate.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvInfoDate.setText(data);
                        indexO = 0;
                        showLoading("");
                        getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                        apptaskIndex(tvInfoDate.getText().toString());
                    }
                });


                break;
            case R.id.bt_all:
                underLineBtAll.setVisibility(View.VISIBLE);
                indexO = 0;
                STATE = null;
                overtime = false;
                showLoading("");
                clearHttp();
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                break;
            case R.id.bt_not_finish:
                underLineBtNotFinish.setVisibility(View.VISIBLE);
                showLoading("");
                indexO = 0;
                STATE = "0";
                overtime = false;
                clearHttp();
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                break;
            case R.id.bt_finish:
                underLineBtFinish.setVisibility(View.VISIBLE);
                showLoading("");
                indexO = 0;
                STATE = "1";
                overtime = false;
                clearHttp();
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                break;
            case R.id.bt_over_time:
                underLineBtOverTime.setVisibility(View.VISIBLE);
                showLoading("");
                indexO = 0;
                STATE = "-1";
                overtime = true;
                clearHttp();
                getMyRecord(true, indexO, tvInfoDate.getText().toString(), TYPE, STATE, overtime);
                break;
        }
    }

    private void hideUnderLine() {
        underLineBtAll.setVisibility(View.INVISIBLE);
        underLineBtNotFinish.setVisibility(View.INVISIBLE);
        underLineBtFinish.setVisibility(View.INVISIBLE);
        underLineBtOverTime.setVisibility(View.INVISIBLE);
    }

}
