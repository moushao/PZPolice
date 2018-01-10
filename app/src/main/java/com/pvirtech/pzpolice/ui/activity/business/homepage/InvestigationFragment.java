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
import com.pvirtech.pzpolice.enumeration.InvestigationAlarmEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationBaseCaseEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationOtherCaseEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationTypeEnum;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.adapter.PerformanceAppraisalAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/26.
 * 业务考核   案侦
 */

public class InvestigationFragment extends BaseFragment {
    PerformanceAppraisalAdapter adapterType;
    PerformanceAppraisalAdapter adapterTask;
    List<PerformanceAppraisalEntity> listType = new ArrayList<>();
    List<PerformanceAppraisalEntity> listBaseCaseEnum = new ArrayList<>();
    List<PerformanceAppraisalEntity> listOtherCaseEnum = new ArrayList<>();
    List<PerformanceAppraisalEntity> listInvestigationAlarmEnum = new ArrayList<>();
    @BindView(R.id.recycleviewType)
    RecyclerView recycleviewType;
    @BindView(R.id.recycleviewTask)
    RecyclerView recycleviewTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_investigation, container, false);
        initView(view);
        initView();
        return view;
    }

    private void initView() {
        /**
         * 处理任务类型概览
         */
        for (InvestigationTypeEnum myEnum : InvestigationTypeEnum
                .values()) {
            myEnum.getValue().setChecked(false);
            PerformanceAppraisalEntity entity = myEnum.getValue();
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

                        if (InvestigationTypeEnum.ALARM.getValue().getID() != listType.get(position).getID() && InvestigationTypeEnum.CASE.getValue
                                ().getID() != listType.get(position).getID()) {
                            listType.get(position).setChecked(true);
                        }

                        adapterType.notifyDataSetChanged();
                        if (type == InvestigationTypeEnum.ALARM.getValue().getID()) {
                            /**
                             * 接处警
                             */
                            dealCaseAdapter(listInvestigationAlarmEnum);
                          /*  dealCaseAdapter(new ArrayList<PerformanceAppraisalEntity>());
                            Intent intent = new Intent();
                            intent.putExtra("id", data.get(position).getID() + "");
                            intent.putExtra(Constant.CASE_COME_TYPE, CaseComeEnum.AUTONOMY.getValue());
                            intent = OperationUtils.getCaseActivityIntent(mContext, InvestigationTypeEnum.ALARM.getValue().getID(), intent);
                            startActivity(intent);*/
                        } else if (type == InvestigationTypeEnum.CASE.getValue().getID()) {
                            /**
                             * 案件办理
                             */
                            dealCaseAdapter(new ArrayList<PerformanceAppraisalEntity>());
                            Intent intent = new Intent();
                            intent.putExtra("id", data.get(position).getID() + "");
                            intent.putExtra(Constant.CASE_COME_TYPE, CaseComeEnum.AUTONOMY.getValue());
                            intent = OperationUtils.getCaseActivityIntent(mContext, InvestigationTypeEnum.CASE.getValue().getID(), intent);
                            startActivity(intent);
                        } else if (type == InvestigationTypeEnum.BASE.getValue().getID()) {
                            /**
                             * 基础工作
                             */
                            dealCaseAdapter(listBaseCaseEnum);

                        } else if (type == InvestigationTypeEnum.OTHER.getValue().getID()) {
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
         * 基础工作
         */
        for (InvestigationBaseCaseEnum myEnum : InvestigationBaseCaseEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listBaseCaseEnum.add(entity);
        }


        /**
         * 其他工作
         */
        for (InvestigationOtherCaseEnum myEnum : InvestigationOtherCaseEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listOtherCaseEnum.add(entity);
        }
        /**
         *案侦-接处警
         */
        for (InvestigationAlarmEnum myEnum : InvestigationAlarmEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listInvestigationAlarmEnum.add(entity);
        }


        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
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
