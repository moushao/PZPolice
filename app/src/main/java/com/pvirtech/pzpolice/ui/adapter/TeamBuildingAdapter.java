package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TeamBuildingEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.main.OperationUtils;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class TeamBuildingAdapter extends BaseQuickAdapter<TeamBuildingEntity, BaseViewHolder> {

    public interface OnRecyclerViewListener {
        void onItemClick(TeamBuildingEntity entity);

        boolean onItemLongClick(TeamBuildingEntity entity);

        boolean onClickButton(TeamBuildingEntity entity);
    }

    private Context mContext = null;

    private List<TeamBuildingEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public TeamBuildingAdapter(Context context, List<TeamBuildingEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_team_building, list);
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    public List<TeamBuildingEntity> getmData() {
        return list;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final TeamBuildingEntity item) {

        if (position % 4 == 0) {
            baseViewHolder.setImageResource(R.id.iv_icon, R.mipmap.user_1);
        } else if (position % 4 == 1) {
            baseViewHolder.setImageResource(R.id.iv_icon, R.mipmap.user_2);
        } else if (position % 4 == 2) {
            baseViewHolder.setImageResource(R.id.iv_icon, R.mipmap.user_3);
        } else if (position % 4 == 3) {
            baseViewHolder.setImageResource(R.id.iv_icon, R.mipmap.user_4);
        }
        position++;

        baseViewHolder.setText(R.id.tv_name, item.getName());
        baseViewHolder.setText(R.id.tv_type, item.getType());
        baseViewHolder.setText(R.id.tv_count, "分");

        String totalTime = "";
        if (item.getStart().length() == 16 && item.getEnd().length() == 16) {
            totalTime = OperationUtils.getIntervalTime(item.getStart(), item.getEnd());
        } else if (item.getStart().length() == 10 && item.getEnd().length() == 10) {
            totalTime = OperationUtils.getDays(item.getStart(), item.getEnd())+"天";
        }
        baseViewHolder.setText(R.id.tv_total, item.getType()+mContext.getResources().getString(R.string.time) + totalTime + "");
        baseViewHolder.setText(R.id.tv_time, item.getType()+mContext.getResources().getString(R.string.total_time) + item.getStart() + "到" + item.getEnd());

        if (false) {
            baseViewHolder.setText(R.id.tv_status, "");
        }
        for (ToExamineEnum toExamineEnum : ToExamineEnum.values()) {
            if (toExamineEnum.getToExamineEntity().getState() == item.getAUDIT_STATE()) {
                baseViewHolder.setText(R.id.tv_status,toExamineEnum.getToExamineEntity().getNotice());
            }
        }

        baseViewHolder.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(item);
            }
        });
    }

    public List<TeamBuildingEntity> getList() {
        return list;
    }

    public void setList(List<TeamBuildingEntity> list) {
        this.list = list;
    }

    private String dealDay(double day) {
        try {
            String data = String.valueOf(day);
            if (data.endsWith(".0")) {
                data = data.substring(0, data.length() - 2);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
