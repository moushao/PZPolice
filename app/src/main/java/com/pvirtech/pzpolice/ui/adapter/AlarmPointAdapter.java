package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.AlarmPointEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class AlarmPointAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<AlarmPointEntity> mData;

    private OnRecyclerViewListener onRecyclerViewListener;


    public AlarmPointAdapter(Context context, List<AlarmPointEntity> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_alarm_point, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv_alarm_point_name.setText(mData.get(i).getName());
        holder.tv_alarm_point_points.setText(mData.get(i).getPoint());
        if (mData.get(i).isChecked()) {
            holder.rootView.setBackgroundResource(R.drawable.time_declare_pressed);
        } else {
            holder.rootView.setBackgroundResource(R.drawable.time_declare_normal);
        }
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_alarm_point_name;
        TextView tv_alarm_point_points;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_alarm_point_name = (TextView) itemView.findViewById(R.id.tv_alarm_point_name);
            tv_alarm_point_points = (TextView) itemView.findViewById(R.id.tv_alarm_point_points);
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

//    private String dealDay(double day) {
//        try {
//            String data = String.valueOf(day);
//            if (data.endsWith(".0")) {
//                data = data.substring(0, data.length() - 2);
//            }
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
