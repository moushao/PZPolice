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
import com.pvirtech.pzpolice.enumeration.PatrolTypeEnum;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.adapter.PerformanceAppraisalAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/26.
 * 业务考核   巡逻
 */

public class PatrolFragment extends BaseFragment {
    PerformanceAppraisalAdapter adapterType;
    List<PerformanceAppraisalEntity> listType = new ArrayList<>();
    @BindView(R.id.recycleviewType)
    RecyclerView recycleviewType;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_patrol, container, false);
        initView(view);
        initView();
        return view;
    }

    private void initView() {
        /**
         * 处理任务类型概览
         */
        for (PatrolTypeEnum myEnum : PatrolTypeEnum.values()) {
            PerformanceAppraisalEntity entity = myEnum.getValue();
            listType.add(entity);
        }
        adapterType = new PerformanceAppraisalAdapter(mContext, listType, new PerformanceAppraisalAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                List<PerformanceAppraisalEntity> data = adapterType.getList();
                int type = data.get(position).getID();
                /*
                for (PerformanceAppraisalEntity performanceAppraisalEntity : listType) {
                    performanceAppraisalEntity.setChecked(false);
                }

                listType.get(position).setChecked(true);*/
                adapterType.notifyDataSetChanged();

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
        recycleviewType.setAdapter(adapterType);
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleviewType.setLayoutManager(new GridLayoutManager(mContext, 2));

    }

}
