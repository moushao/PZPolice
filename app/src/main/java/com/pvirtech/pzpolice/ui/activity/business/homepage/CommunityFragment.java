package com.pvirtech.pzpolice.ui.activity.business.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.CommuityAlarmEnum;
import com.pvirtech.pzpolice.enumeration.CommuityTypeEnum;
import com.pvirtech.pzpolice.enumeration.CommunityBaseCaseEnum;
import com.pvirtech.pzpolice.enumeration.CommunityCaseEnum;
import com.pvirtech.pzpolice.enumeration.CommunityInvestigationEnum;
import com.pvirtech.pzpolice.enumeration.CommunityOtherCaseEnum;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.adapter.PerformanceAppraisalAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/26.
 * 业务考核   社区
 */

public class CommunityFragment extends BaseFragment {
    PerformanceAppraisalAdapter adapterType;
    PerformanceAppraisalAdapter adapterTask;
    List<PerformanceAppraisalEntity> listType = new ArrayList<>();
    List<PerformanceAppraisalEntity> listCase = new ArrayList<>();
    List<PerformanceAppraisalEntity> listBaseCaseEnum = new ArrayList<>();
    List<PerformanceAppraisalEntity> listCriminalInvestigationEnum = new ArrayList<>();
    List<PerformanceAppraisalEntity> listCommuityAlarmEnum = new ArrayList<>();
    List<PerformanceAppraisalEntity> listOtherCaseEnum = new ArrayList<>();
    @BindView(R.id.recycleviewType)
    RecyclerView recycleviewType;
    @BindView(R.id.recycleviewTask)
    RecyclerView recycleviewTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        initView(view);
        initView();
        return view;
    }

    private void initView() {
        /**
         * 处理任务类型概览
         */
        for (CommuityTypeEnum myEnum : CommuityTypeEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            entity.setChecked(false);
            listType.add(entity);
        }
        adapterType = new PerformanceAppraisalAdapter(mContext, listType, new
                PerformanceAppraisalAdapter.OnRecyclerViewListener() {
                    @Override
                    public void onItemClick(int position) {
                        List<PerformanceAppraisalEntity> data = adapterType.getList();
                        int type = data.get(position).getID();

                        for (PerformanceAppraisalEntity performanceAppraisalEntity : listType) {
                            performanceAppraisalEntity.setChecked(false);
                        }

                        listType.get(position).setChecked(true);

                        adapterType.notifyDataSetChanged();
                        if (type == CommuityTypeEnum.CASE.getValue().getID()) {
                            /**
                             * 案件办理
                             */
                            dealCaseAdapter(listCase);
                        } else if (type == CommuityTypeEnum.BASE.getValue().getID()) {
                            /**
                             * 基础工作
                             */
                            dealCaseAdapter(listBaseCaseEnum);
                        } else if (type == CommuityTypeEnum.CRIMINAL_INVESTIGATION.getValue().getID()) {
                            /**
                             * 刑侦基础
                             */
                            dealCaseAdapter(listCriminalInvestigationEnum);
                        } else if (type == CommuityTypeEnum.ALARM.getValue().getID()) {
                            /**
                             * 接处警
                             */
                            dealCaseAdapterAlarm(listCommuityAlarmEnum);

                        } else if (type == CommuityTypeEnum.OTHER.getValue().getID()) {
                            /**
                             * 其他工作
                             */
                            dealCaseAdapter(listOtherCaseEnum);
                        }
                    }

                    @Override
                    public boolean onItemLongClick(int position) {
                        return false;
                    }
                });
        recycleviewType.setAdapter(adapterType);
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleviewType.setLayoutManager(new GridLayoutManager(mContext, 2));


        /**
         * 行政案件办理
         */
        for (CommunityCaseEnum myEnum : CommunityCaseEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listCase.add(entity);
        }
        /**
         * 基础工作
         */
        for (CommunityBaseCaseEnum myEnum : CommunityBaseCaseEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listBaseCaseEnum.add(entity);
        }
        /**
         * 接处警
         */
        for (CommuityAlarmEnum myEnum : CommuityAlarmEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listCommuityAlarmEnum.add(entity);
        }

        /**
         * 刑侦基础
         */
        for (CommunityInvestigationEnum myEnum : CommunityInvestigationEnum.values()) {
            listCriminalInvestigationEnum.add(myEnum.getValue());
        }

        /**
         * 其他工作
         */
        for (CommunityOtherCaseEnum myEnum : CommunityOtherCaseEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listOtherCaseEnum.add(entity);
        }

        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.HORIZONTAL));
        recycleviewTask.setLayoutManager(new GridLayoutManager(mContext, 2));
    }

    private void dealCaseAdapter(List<PerformanceAppraisalEntity> list) {
        adapterTask = new PerformanceAppraisalAdapter(mContext, list, new
                PerformanceAppraisalAdapter.OnRecyclerViewListener() {
                    @Override
                    public void onItemClick(int position) {
                        List<PerformanceAppraisalEntity> data = adapterTask.getList();
                        int type = data.get(position).getID();
                        Intent intent = new Intent();
                        intent.putExtra("id", data.get(position).getID() + "");
                        intent.putExtra(Constant.CASE_COME_TYPE, CaseComeEnum.AUTONOMY.getValue());
                        intent = OperationUtils.getCaseActivityIntent(mContext, type, intent);
                        if (null != intent.getClass()) {
                            startActivity(intent);
                        }

                    }

                    @Override
                    public boolean onItemLongClick(int position) {
                        return false;
                    }
                });
        recycleviewTask.setAdapter(adapterTask);
    }

    private void dealCaseAdapterAlarm(List<PerformanceAppraisalEntity> list) {
        adapterTask = new PerformanceAppraisalAdapter(mContext, list, new
                PerformanceAppraisalAdapter.OnRecyclerViewListener() {
                    @Override
                    public void onItemClick(int position) {
                        List<PerformanceAppraisalEntity> data = adapterTask.getList();
                        int type = data.get(position).getID();
                        Intent intent = new Intent();
                        intent.putExtra("id", data.get(position).getID() + "");
                        intent.putExtra(Constant.CASE_COME_TYPE, CaseComeEnum.AUTONOMY.getValue());
                        intent.putExtra(Constant.TITLE, data.get(position).getContent());
                        intent = OperationUtils.getCaseActivityIntent(mContext, type, intent);
                        if (null != intent.getClass()) {
                            startActivity(intent);
                        }

                    }

                    @Override
                    public boolean onItemLongClick(int position) {
                        return false;
                    }
                });
        recycleviewTask.setAdapter(adapterTask);
    }

}
