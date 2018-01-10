package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.SubtaskEntity;

import java.util.List;

/**
 * Created by pd on 2017/4/24.
 */

public class BusinessWorkReviewSubtaskContentAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<SubtaskEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public BusinessWorkReviewSubtaskContentAdapter(Context context, List<SubtaskEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_business_work_review_subtask_content, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    public List<SubtaskEntity> getList() {
        return list;
    }

    public void setList(List<SubtaskEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        SubtaskEntity subtaskEntity = list.get(i);
        holder.tv_id.setText(subtaskEntity.getCASE_NO());
//        holder.tv_point.setText(subtaskEntity.getId());
        holder.tv_time.setText(subtaskEntity.getCASE_START_TIME());
        holder.tv_type.setText(subtaskEntity.getNAME());
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tv_id, tv_point, tv_time, tv_type;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_point = (TextView) itemView.findViewById(R.id.tv_point);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
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
}