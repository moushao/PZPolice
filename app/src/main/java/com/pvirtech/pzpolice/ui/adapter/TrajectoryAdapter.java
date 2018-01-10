package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TaskTrajectory;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.third.view.StageView;

import java.util.List;

/**
 * Created by pd on 2017/5/9.
 */

public class TrajectoryAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<TaskTrajectory> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public TrajectoryAdapter(Context context, List<TaskTrajectory> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_trajectory_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (i == 0) {
            holder.stageview.setState(1);
        } else if (i == list.size() - 1) {
            holder.stageview.setState(2);
        } else {
            holder.stageview.setState(3);
        }
        if (list.size() == 1) {
            holder.stageview.setState(4);
        }
        holder.stageview.invalidateView();

        TaskTrajectory trailBean = list.get(i);
        for (ToExamineEnum toExamineEnum : ToExamineEnum.values()) {
            if (toExamineEnum.getToExamineEntity().getState() == trailBean.getAUDIT_STATE()) {
                holder.tv_state.setText(toExamineEnum.getToExamineEntity().getNotice());
            }
        }
        holder.tv_notice.setText(trailBean.getAUDIT_USER());
        holder.tv_time.setText(trailBean.getAUDIT_TIME());
        holder.position = i;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        LinearLayout rootView;
        int position;
        TextView tv_state, tv_notice, tv_time;
        StageView stageview;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_notice = (TextView) itemView.findViewById(R.id.tv_notice);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            stageview = (StageView) itemView.findViewById(R.id.stageview);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }


    public List<TaskTrajectory> getList() {
        return list;
    }

    public void setList(List<TaskTrajectory> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
