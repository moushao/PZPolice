package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkDay;
import com.pvirtech.pzpolice.enumeration.DayStatus;
import com.pvirtech.pzpolice.enumeration.WorkStatus;

import java.util.Date;
import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class CalendarAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<WorkDay> mData = null;
    private int notCal;
    private OnRecyclerViewListener onRecyclerViewListener;
    Date date;
    String selectedDay = "";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public CalendarAdapter(Context context, List<WorkDay> mData, OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_layout, null);
   /*     int width = (ScreenUtils.getScreenWidth(mContext) - 80) / 7;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        view.setLayoutParams(lp);*/
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        WorkDay workDay = mData.get(i);
        holder.text.setText(workDay.getText());

        if (workDay.getDayStatus() == DayStatus.NORMAL.getValue()) {
            holder.text.setTextColor(0xFF8696A5);
            holder.ll_number_view.setBackgroundResource(R.drawable.day_normoal_background);
        } else if (workDay.getDayStatus() == DayStatus.TODAY.getValue()) {
            holder.text.setTextColor(0xFF457BF4);
            holder.ll_number_view.setBackgroundResource(R.drawable.day_normoal_background);
        } else if (workDay.getDayStatus() == DayStatus.SELECTED.getValue()) {
            holder.text.setTextColor(0xFFFAFBFE);
            holder.ll_number_view.setBackgroundResource(R.drawable.day_selected_background);
        }
        if (workDay.getDayStatus() == DayStatus.EMPTY.getValue()) {
            holder.text.setTextColor(0xFF4586ED);
            holder.ll_number_view.setBackgroundResource(R.drawable.day_week_background);
            holder.workStatus.setVisibility(View.INVISIBLE);
        }
        if (workDay.getDayStatus() == DayStatus.WEEK.getValue()) {
            holder.workStatus.setVisibility(View.INVISIBLE);
            holder.text.setTextColor(0xFF4586ED);
            holder.ll_number_view.setBackgroundResource(R.drawable.day_week_background);
        }
        /*long day = TimeUtil.compareDate(workDay.getDate() + " 00:00:00", getYMD() + " 00:00:00");
        if (day > 0) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_white);
        }*/
        if (WorkStatus.EMPTY.getValue() == workDay.getType()) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_white);
        } else if (WorkStatus.MORMAL.getValue() == workDay.getType()) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_blue);
        } else if (WorkStatus.LEAVE.getValue() == workDay.getType()) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_yellow);
        } else if (WorkStatus.TARDINESS.getValue() == workDay.getType()) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_red);
        }



      /* if (day <= -7) {//如果超过七天
            holder.workStatus.setBackgroundResource(R.drawable.work_status_unnormal_background);
        } else if (day > -7 && day <= 0) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_leave_background);
        } else {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_empty_background);
        }
        if (workDay.getType() == 1) {
            holder.workStatus.setBackgroundResource(R.drawable.work_status_nornaml_background);
        }*/
        holder.position = i;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView text;
        TextView workStatus;
        LinearLayout calendar_root_view;
        LinearLayout ll_number_view;

        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            calendar_root_view = (LinearLayout) itemView.findViewById(R.id.calendar_root_view);
            text = (TextView) itemView.findViewById(R.id.text);
            workStatus = (TextView) itemView.findViewById(R.id.work_status);
            ll_number_view = (LinearLayout) itemView.findViewById(R.id.ll_number_view);
            calendar_root_view.setOnClickListener(this);
            calendar_root_view.setOnLongClickListener(this);
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


    public List<WorkDay> getmData() {
        return mData;
    }

    public void setmData(List<WorkDay> mData) {
        this.mData = mData;
    }


}