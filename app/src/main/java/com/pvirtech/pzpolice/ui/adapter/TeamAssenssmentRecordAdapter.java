package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TeamAssessmentRecordEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class TeamAssenssmentRecordAdapter extends BaseQuickAdapter<TeamAssessmentRecordEntity, BaseViewHolder> {

    public interface OnRecyclerViewListener {
        void onItemClick(TeamAssessmentRecordEntity entity);

        boolean onItemLongClick(TeamAssessmentRecordEntity entity);

        boolean onClickButton(TeamAssessmentRecordEntity entity);
    }

    private Context mContext = null;

    private List<TeamAssessmentRecordEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public TeamAssenssmentRecordAdapter(Context context, List<TeamAssessmentRecordEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_team_assessment_record, list);
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    public List<TeamAssessmentRecordEntity> getmData() {
        return list;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final TeamAssessmentRecordEntity leaveRecordEntity) {
        baseViewHolder.setText(R.id.tv_time, leaveRecordEntity.getCHECK_TIME());
        baseViewHolder.setText(R.id.tv_reason, leaveRecordEntity.getREASON());
        baseViewHolder.setText(R.id.ed_points, leaveRecordEntity.getSCORE() + "");
        String strUserlist = "";
        for (String temp : leaveRecordEntity.getUserlist()) {
            strUserlist = temp + ",";
        }
        if (strUserlist.endsWith(",")) {
            strUserlist = strUserlist.substring(0, strUserlist.length() - 1);
        }
        baseViewHolder.setText(R.id.tv_person_notice, strUserlist );
        baseViewHolder.setText(R.id.ed_bz, leaveRecordEntity.getREMARKS());

    }

    public List<TeamAssessmentRecordEntity> getList() {
        return list;
    }

    public void setList(List<TeamAssessmentRecordEntity> list) {
        this.list = list;
    }
}
