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

import java.util.ArrayList;


/**
 * Created by youpengda on 2017/2/9.
 */

public class VacationArrangeAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private ArrayList<LeaveEntity> mData;

    private OnRecyclerViewListener onRecyclerViewListener;


    public VacationArrangeAdapter(Context context, ArrayList<LeaveEntity> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_vacation_arrange, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.iv_icon.setImageResource(mData.get(i).getIcon());
        holder.tv_title.setText(mData.get(i).getName());
        holder.tv_time.setText("默认" +dealDay(mData.get(i).getDay()) + "天");
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
