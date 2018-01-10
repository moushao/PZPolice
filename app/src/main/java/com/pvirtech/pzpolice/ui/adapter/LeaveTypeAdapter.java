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
import com.pvirtech.pzpolice.entity.LeaveEntity;
import com.pvirtech.pzpolice.enumeration.LeaveType;

import java.util.ArrayList;


/**
 * Created by youpengda on 2017/2/9.
 */

public class LeaveTypeAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private ArrayList<LeaveEntity> mData;

    private OnRecyclerViewListener onRecyclerViewListener;


    public LeaveTypeAdapter(Context context, ArrayList<LeaveEntity> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_leave_time, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    public ArrayList<LeaveEntity> getmData() {
        return mData;
    }

    public void setmData(ArrayList<LeaveEntity> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        LeaveEntity mLeaveEntity = mData.get(i);
        holder.iv_icon.setImageResource(mLeaveEntity.getIcon());
        holder.tv_title.setText(mLeaveEntity.getName());


        /**
         * 处理假期天数
         */
        if (mLeaveEntity.getType().equals(LeaveType.OFF_LEAVE.getValue().getType()) || mLeaveEntity.getType().equals(LeaveType.COMPASSIONATE_LEAVE
                .getValue().getType())) {
            holder.tv_time.setText("请申请");
        } else {
            String day = String.valueOf(mData.get(i).getDay());
            if (day.endsWith(".0")) {
                day = day.substring(0, day.length() - 2);
            }
            if (day.equals("0")) {
                holder.tv_time.setText("无");
                if (mData.get(i).getTotal() > 0) {
                    holder.tv_time.setText("已休完");
                }
            } else {
                holder.tv_time.setText("剩余" + day + "天");
            }
        }

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
}
