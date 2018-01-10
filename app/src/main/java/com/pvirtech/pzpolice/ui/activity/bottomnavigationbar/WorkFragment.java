package com.pvirtech.pzpolice.ui.activity.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.Icon;
import com.pvirtech.pzpolice.entity.WokFragmentNotifyEntitiy;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.activity.business.homepage.PerformanceAppraisalActivity;
import com.pvirtech.pzpolice.ui.activity.leave.LeaveActivity;
import com.pvirtech.pzpolice.ui.activity.log.WorkLogActivity;
import com.pvirtech.pzpolice.ui.activity.personalfile.PersonalFileActivity;
import com.pvirtech.pzpolice.ui.activity.task.MyTaskActivity;
import com.pvirtech.pzpolice.ui.activity.work.DutyFromActivity;
import com.pvirtech.pzpolice.ui.activity.work.FileManagementActivity;
import com.pvirtech.pzpolice.ui.activity.work.MyAssessmentActivity;
import com.pvirtech.pzpolice.ui.activity.work.MyEquipmentActivity;
import com.pvirtech.pzpolice.ui.activity.work.TaskDistributeActivity;
import com.pvirtech.pzpolice.ui.activity.work.assessment.TeamAssessmentActivity;
import com.pvirtech.pzpolice.ui.activity.work.VacationArrangeActivity;
import com.pvirtech.pzpolice.ui.activity.work.needdeal.MyInitiatedActivity;
import com.pvirtech.pzpolice.ui.activity.work.needdeal.NeedToeDealActivity;
import com.pvirtech.pzpolice.ui.activity.worktime.WorkTimeCalendlarActivity;
import com.pvirtech.pzpolice.ui.adapter.TaskMenuAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;

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
 * 工作主页
 */
public class WorkFragment extends BaseFragment {
    @BindView(R.id.task)
    RecyclerView task;
    @BindView(R.id.recycleview_common)
    RecyclerView recycleviewCommon;
    @BindView(R.id.notice)
    NoticeView notice;


    private List<Icon> mData = null;
    private List<Icon> mDataCommon = null;
    List<String> arrayListNotices = new ArrayList<>();
    TaskMenuAdapter taskMenuAdapter;
    TaskMenuAdapter recycleviewCommonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        initInfoTitleView(view);

        arrayListNotices.add("11月28日消防演练");
        arrayListNotices.add("11月2日-11月5日清明保障");
        arrayListNotices.add("领导视察，启动重要安保");
        notice.start(arrayListNotices);

        /**
         * 第一行菜单
         */
        Icon myTask;
        if (OperationUtils.getRoleLeader()) {//领导界面
            myTask = new Icon(mContext.getResources().getString(R.string.task_publishing), R.mipmap.my_task);//任务发布
        } else {
            myTask = new Icon(mContext.getResources().getString(R.string.my_task), R.mipmap.my_task);//我的任务
        }

        Icon assessment = new Icon(mContext.getResources().getString(R.string.my_assessment), R.mipmap.my_assessment);//我的考核
        Icon equipment = new Icon(mContext.getResources().getString(R.string.my_equipment), R.mipmap.my_equipment);//我的装备
        Icon needToBeDeal = new Icon(mContext.getResources().getString(R.string.need_to_be_deal), R.mipmap.need_to_be_deal);//待办
        mData = new ArrayList<>();
        mData.add(myTask);
        mData.add(assessment);
        mData.add(equipment);
        mData.add(needToBeDeal);

        taskMenuAdapter = new TaskMenuAdapter(mContext, mData, new TaskMenuAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                String strSelected = mData.get(position).getName();
                if (strSelected.equals(mContext.getResources().getString(R.string.my_task))) {
                    intent = new Intent(mContext, MyTaskActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.task_publishing))) {
                    intent = new Intent(mContext, TaskDistributeActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.my_assessment))) {
                    intent = new Intent(mContext, MyAssessmentActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.my_equipment))) {
                    intent = new Intent(mContext, MyEquipmentActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.need_to_be_deal))) {
                    if (OperationUtils.getRoleLeaderAndSheriff()) {//领导界面
                        intent = new Intent(mContext, NeedToeDealActivity.class);
                    } else {
                        intent = new Intent(mContext, MyInitiatedActivity.class);
                    }
                    startActivity(intent);
                }

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        task.setLayoutManager(new LinearLayoutManager(mContext));
        /*task.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        task.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));*/
        task.setLayoutManager(new GridLayoutManager(mContext, 4));
        task.setAdapter(taskMenuAdapter);

        /**
         *第二行菜单
         */
        Icon workLog = new Icon(mContext.getResources().getString(R.string.work_log), R.mipmap.work_log);//工作日志
        Icon leave = new Icon(mContext.getResources().getString(R.string.leave), R.mipmap.leave);//请销假
        Icon workTime = new Icon(mContext.getResources().getString(R.string.work_time), R.mipmap.work_time);//工作时间
        Icon dutyFrom = new Icon(mContext.getResources().getString(R.string.duty_from), R.mipmap.duty_from);//值班表
        Icon TeamAssessment = new Icon(mContext.getResources().getString(R.string.team_assessment), R.mipmap.team_assessment);//队伍考核
        Icon performanceAppraisal = new Icon(mContext.getResources().getString(R.string.performance_appraisal), R.mipmap.performance_appraisal);//业绩考核
        Icon fileManagement = new Icon(mContext.getResources().getString(R.string.file_management), R.mipmap.file_management);//档案管理
        Icon vacationArrange = new Icon(mContext.getResources().getString(R.string.vacation_arrange), R.mipmap.vacation_arrange);//假期安排

        mDataCommon = new ArrayList<>();
        mDataCommon.add(workLog);
        if (!OperationUtils.getRoleLeader()) {//!领导界面
            mDataCommon.add(leave);
        }
        mDataCommon.add(workTime);
        mDataCommon.add(dutyFrom);
        if (OperationUtils.getRoleLeader()) {//领导界面
            mDataCommon.add(TeamAssessment);
        }
        if (!OperationUtils.getRoleLeader()) {//!领导界面
            mDataCommon.add(performanceAppraisal);
        }
        mDataCommon.add(fileManagement);
        if (OperationUtils.getRoleLeader()) {//领导界面
            mDataCommon.add(vacationArrange);
        }

        recycleviewCommonAdapter = new TaskMenuAdapter(mContext, mDataCommon, new TaskMenuAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                String strSelected = mDataCommon.get(position).getName();
                if (strSelected.equals(mContext.getResources().getString(R.string.work_log))) {
                    intent = new Intent(mContext, WorkLogActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.leave))) {
                    intent = new Intent(mContext, LeaveActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.work_time))) {
                    intent = new Intent(mContext, WorkTimeCalendlarActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.duty_from))) {
                    intent = new Intent(mContext, DutyFromActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.team_assessment))) {
                    intent = new Intent(mContext, TeamAssessmentActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.performance_appraisal))) {
                    intent = new Intent(mContext, PerformanceAppraisalActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.file_management))) {
                    intent = new Intent(mContext, FileManagementActivity.class);
                    startActivity(intent);
                } else if (strSelected.equals(mContext.getResources().getString(R.string.vacation_arrange))) {
                    intent = new Intent(mContext, VacationArrangeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleviewCommon.setLayoutManager(new LinearLayoutManager(mContext));
        /*recycleviewCommon.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewCommon.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));*/
        recycleviewCommon.setLayoutManager(new GridLayoutManager(mContext, 4));
        recycleviewCommon.setAdapter(recycleviewCommonAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        apprankMyscore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.ll_my_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_my_info:
                /**
                 * 查看我的个人信息
                 */
                Intent intent = new Intent(mContext, PersonalFileActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }


    private void apprankMyscore() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appcommonNum(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<WokFragmentNotifyEntitiy>>() {
            @Override
            public void accept(@NonNull HttpResult<WokFragmentNotifyEntitiy> result) throws Exception {
                for (Icon icon : mData) {
                    if (icon.getName().equals(mContext.getResources().getString(R.string.my_task))) {//我的任务
                        icon.setCount(result.getData().getMytask());
                    }
                    if (icon.getName().equals(mContext.getResources().getString(R.string.need_to_be_deal))) {//我的考核
                        icon.setCount(result.getData().getSche());
                    }
                }
                taskMenuAdapter.notifyDataSetChanged();

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }));

    }


}
