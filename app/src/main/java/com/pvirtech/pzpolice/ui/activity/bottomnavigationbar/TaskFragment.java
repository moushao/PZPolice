package com.pvirtech.pzpolice.ui.activity.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.GroupcountEntity;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.entity.TaskPreviewEntity;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.CommuitySecurityEnum;
import com.pvirtech.pzpolice.enumeration.CommunityCaseEnum;
import com.pvirtech.pzpolice.enumeration.TaskTypeEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.third.view.CircleProgress;
import com.pvirtech.pzpolice.third.view.CircleProgressProgress;
import com.pvirtech.pzpolice.ui.activity.business.community.NormalCaseActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.SecurityCaseActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.TrafficPunishmentActivity;
import com.pvirtech.pzpolice.ui.activity.task.MyTaskActivity;
import com.pvirtech.pzpolice.ui.activity.task.MyTaskReviewActivity;
import com.pvirtech.pzpolice.ui.adapter.GroupcountAdapter;
import com.pvirtech.pzpolice.ui.adapter.TaskFragmentAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 任务主页
 */
public class TaskFragment extends BaseFragment {

    @BindView(R.id.circleprogress)
    CircleProgress circleprogress;
    @BindView(R.id.circleprogress2)
    CircleProgress circleprogress2;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    String TAG = "TaskFragment";
    TaskFragmentAdapter adapter;
    GroupcountAdapter groupcountAdapter;
    List<MyTasksEntity> list = new ArrayList<>();
    List<GroupcountEntity> groupcountEntities = new ArrayList<>();
    TaskPreviewEntity taskPreviewEntity = new TaskPreviewEntity();
    @BindView(R.id.tv_notice)
    TextView tvNotice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        initInfoTitleView(view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        apptaskIndex();
        if (OperationUtils.getRoleLeader()) {
            tvNotice.setText("本月各小组任务完成数量统计");
            apptaskGroupcount();
        } else {
            apptaskDateDeadTask();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        if (OperationUtils.getRoleLeader()) {
            groupcountAdapter = new GroupcountAdapter(mContext, groupcountEntities, new GroupcountAdapter.OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position) {
                }

                @Override
                public boolean onItemLongClick(int position) {
                    return false;
                }
            });
            recycleview.setAdapter(groupcountAdapter);
        } else {
            adapter = new TaskFragmentAdapter(mContext, list, new TaskFragmentAdapter.OnRecyclerViewListener() {
                @Override
                public void onItemClick(MyTasksEntity entity) {
                    if (entity.getSTATE() == 1) {
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
                        Intent intent = null;
                        for (CommuitySecurityEnum commuitySecurityEnum : CommuitySecurityEnum.values()) {//治安案件
                            if (entity.getCASE_TYPE() == commuitySecurityEnum.getValue().getID()) {
                                intent = new Intent(mContext, SecurityCaseActivity.class);
                                break;
                            }
                        }

                        for (CommunityCaseEnum communityCaseEnum : CommunityCaseEnum.values()) {//案件办理案件
                            if (entity.getCASE_TYPE() == CommunityCaseEnum.TRAFFIC.getValue().getID()) {//交通案件
                                intent = new Intent(mContext, TrafficPunishmentActivity.class);
                                break;
                            } else if (entity.getCASE_TYPE() == communityCaseEnum.getValue().getID()) {
                                intent = new Intent(mContext, NormalCaseActivity.class);
                                break;
                            }
                        }
                        if (null != intent) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constant.CASE_COME_TYPE, CaseComeEnum.TASK.getValue());
                            bundle.putParcelable("data", entity);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }
            });
            recycleview.setAdapter(adapter);
        }
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

    @OnClick({R.id.circleprogress, R.id.circleprogress2, R.id.ll_unnormal})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.circleprogress:
                intent = new Intent(mContext, MyTaskActivity.class);
                intent.putExtra("data", TaskTypeEnum.DAILIY.getValue().getID());
                startActivity(intent);
                break;
            case R.id.circleprogress2:
                intent = new Intent(mContext, MyTaskActivity.class);
                intent.putExtra("data", TaskTypeEnum.ASSIGNED.getValue().getID());
                startActivity(intent);
                break;
            case R.id.ll_unnormal:
                apptaskIndex();
                if (OperationUtils.getRoleLeader()) {
                    apptaskGroupcount();
                } else {
                    apptaskDateDeadTask();
                }
                break;
        }
    }


    /**
     * 第一页任务查询 ，统计任务个数
     */
    private void apptaskIndex() {
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskIndex(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<TaskPreviewEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<TaskPreviewEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (null == response.getData()) {
                        showHttpNoResult();
                    } else {
                        taskPreviewEntity = response.getData();
                        dealData(response.getData());
                        hideHttpView();
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                    showHttpError();
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

    /**
     * 高权限的人预览任务
     */
    private void apptaskGroupcount() {
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskGroupcount(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<GroupcountEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<GroupcountEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (null == response.getData()) {
                        showHttpNoResult();
                    } else {
                        groupcountEntities = response.getData();
                        hideHttpView();
                        groupcountAdapter.setList(groupcountEntities);
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                    showHttpError();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showHttpError();
            }
        }));
    }

    /**
     * 取的即将到期任务
     */
    private void apptaskDateDeadTask() {
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskDateDeadTask(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MyTasksEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<MyTasksEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (null == response.getData()) {
                        showHttpNoResult();
                    } else {
                        list = response.getData();
                        adapter.setList(list);
                        hideHttpView();
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                    showHttpError();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showHttpError();
            }
        }));
    }

    private void dealData(TaskPreviewEntity data) {

        /**
         * 处理任务进度
         */

        TaskPreviewEntity.DAILYCOUNTBean dailycountBean = data.getDAILYCOUNT();
        TaskPreviewEntity.ASSIGNCOUNTBean assigncountBean = data.getASSIGNCOUNT();

        List<CircleProgressProgress> circleProgressProgressList = new ArrayList<>();
        CircleProgressProgress circleProgressProgress = new CircleProgressProgress(dailycountBean.getCOMPLETE(),
                0xFF58CDF8);
        CircleProgressProgress circleProgressProgress2 = new CircleProgressProgress(dailycountBean.getNOTCOMPLETE(),
                0xFFF3F3F3);
        circleProgressProgressList.add(circleProgressProgress);
        circleProgressProgressList.add(circleProgressProgress2);
        circleprogress.setList(circleProgressProgressList);
        circleprogress.setImageView(R.mipmap.my_task);
        circleprogress.setStrContent("日常任务");
        circleprogress.invalidateView();


        List<CircleProgressProgress> circleProgressProgressList2 = new ArrayList<>();
        CircleProgressProgress circleProgressProgress3 = new CircleProgressProgress(assigncountBean.getCOMPLETE(), 0xFF5EC812);
        CircleProgressProgress circleProgressProgress4 = new CircleProgressProgress(assigncountBean.getNOTCOMPLETE
                (), 0xFFF3F3F3);
        circleProgressProgressList2.add(circleProgressProgress3);
        circleProgressProgressList2.add(circleProgressProgress4);
        circleprogress2.setList(circleProgressProgressList2);
        circleprogress2.setImageView(R.mipmap.my_task);
        circleprogress2.setStrContent("交办任务");
        circleprogress2.invalidateView();
    }
}
