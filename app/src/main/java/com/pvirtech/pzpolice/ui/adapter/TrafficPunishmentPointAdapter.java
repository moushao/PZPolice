package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.TrafficPunishmentPointEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class TrafficPunishmentPointAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<TrafficPunishmentPointEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public TrafficPunishmentPointAdapter(Context context, List<TrafficPunishmentPointEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_traffic_punishment_point, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv_traffic_punishment_point_name.setText(list.get(i).getName());
        holder.tv_traffic_punishment_point_points.setText(list.get(i).getPoint()+"");

        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_traffic_punishment_point_name;
        TextView tv_traffic_punishment_point_points;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_traffic_punishment_point_name = (TextView) itemView.findViewById(R.id.tv_traffic_punishment_point_name);
            tv_traffic_punishment_point_points = (TextView) itemView.findViewById(R.id.tv_traffic_punishment_point_points);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

    public List<TrafficPunishmentPointEntity> getList() {
        return list;
    }

    public void setList(List<TrafficPunishmentPointEntity> list) {
        this.list = list;
    }
}
