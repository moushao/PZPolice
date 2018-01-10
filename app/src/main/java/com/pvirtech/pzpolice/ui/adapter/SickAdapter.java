package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveRecordEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.main.OperationUtils;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class SickAdapter extends BaseQuickAdapter<LeaveRecordEntity, BaseViewHolder> {

    public interface OnRecyclerViewListener {
        void onItemClick(LeaveRecordEntity leaveRecordEntity);

        boolean onItemLongClick(LeaveRecordEntity leaveRecordEntity);

        boolean onClickButton(LeaveRecordEntity leaveRecordEntity);
    }

    private Context mContext = null;

    private List<LeaveRecordEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public SickAdapter(Context context, List<LeaveRecordEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_sick, list);
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    public List<LeaveRecordEntity> getmData() {
        return list;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LeaveRecordEntity leaveRecordEntity) {

        String totalTime = String.valueOf(leaveRecordEntity.getDay());
        if (totalTime.endsWith(".0")) {
            totalTime = totalTime.substring(0, totalTime.length() - 2);
        }

        for (ToExamineEnum toExamineEnum : ToExamineEnum.values()) {
            if (toExamineEnum.getToExamineEntity().getState() == leaveRecordEntity.getState()) {
                baseViewHolder.setText(R.id.tv_status, toExamineEnum.getToExamineEntity().getNotice());
            }
        }
        baseViewHolder.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(leaveRecordEntity);
            }
        });

        int date = OperationUtils.getSickLeave(leaveRecordEntity.getStart(), leaveRecordEntity.getEnd());
        if (date > 0) {
            baseViewHolder.setText(R.id.tv_total, totalTime + "天(剩余" + date + "天)");
            if (ToExamineEnum.STATE9.getToExamineEntity().getState() == leaveRecordEntity.getState()) {//如果是可以销假的状态
                baseViewHolder.setText(R.id.tv_status, "销假　　");
                baseViewHolder.setOnClickListener(R.id.tv_status, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRecyclerViewListener.onClickButton(leaveRecordEntity);
                    }
                });
            }

        } else {
            baseViewHolder.setText(R.id.tv_total, totalTime + "天");
        }
        baseViewHolder.setText(R.id.tv_time, leaveRecordEntity.getStart());
        baseViewHolder.setText(R.id.tv_end_time, leaveRecordEntity.getEnd());
        baseViewHolder.setText(R.id.tv_reason, leaveRecordEntity.getReason());
//        baseViewHolder.getChildClickViewIds(R.id.tv_status).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        baseViewHolder.setText(R.id.tv_type, leaveRecordEntity.getName());

        if (0 != leaveRecordEntity.getIcon()) {
            baseViewHolder.setImageResource(R.id.iv_icon, leaveRecordEntity.getIcon());
        }


    }

    public List<LeaveRecordEntity> getList() {
        return list;
    }

    public void setList(List<LeaveRecordEntity> list) {
        this.list = list;
    }
}
