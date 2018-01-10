package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkLogEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class WorkLogAdapter extends BaseQuickAdapter<WorkLogEntity, BaseViewHolder> {

    public interface OnRecyclerViewListener {
        void onItemClick(WorkLogEntity leaveRecordEntity);

  /*      boolean onItemLongClick(WorkLogEntity leaveRecordEntity);

        boolean onClickButton(WorkLogEntity leaveRecordEntity);*/
    }

    private Context mContext = null;

    private List<WorkLogEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public WorkLogAdapter(Context context, List<WorkLogEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_work_log, list);
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    public List<WorkLogEntity> getmData() {
        return list;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WorkLogEntity entity) {
        baseViewHolder.setText(R.id.tv_content, entity.getCONTENT()).setText(R.id.tv_time, entity.getCREATE_TIME());
        baseViewHolder.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(entity);
            }
        });
    }

    public List<WorkLogEntity> getList() {
        return list;
    }

    public void setList(List<WorkLogEntity> list) {
        this.list = list;
    }
}
