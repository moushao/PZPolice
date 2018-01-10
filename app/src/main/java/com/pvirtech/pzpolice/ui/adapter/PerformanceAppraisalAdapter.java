package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class PerformanceAppraisalAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;
    private List<PerformanceAppraisalEntity> list;
    private OnRecyclerViewListener onRecyclerViewListener;

    public PerformanceAppraisalAdapter(Context context, List<PerformanceAppraisalEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_performance_appraisal, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        PerformanceAppraisalEntity performanceAppraisalEntity = list.get(i);
        holder.iv_icon.setImageResource(performanceAppraisalEntity.getIcon());
        holder.tv_title.setText(performanceAppraisalEntity.getContent());

        if (list.get(i).isChecked()) {
            holder.rootView.setBackgroundResource(R.drawable.time_declare_pressed);
        } else {
            holder.rootView.setBackgroundResource(R.drawable.time_declare_normal);
        }
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
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

    public List<PerformanceAppraisalEntity> getList() {
        return list;
    }

    public void setList(List<PerformanceAppraisalEntity> list) {
        this.list = list;
    }

    public void removeData() {
        list.clear();
        notifyDataSetChanged();
    }
}
