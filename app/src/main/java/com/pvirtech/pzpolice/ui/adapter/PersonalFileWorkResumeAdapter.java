package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileWorkFragmentEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class PersonalFileWorkResumeAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<PersonalFileWorkFragmentEntity.ResumesBean> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public PersonalFileWorkResumeAdapter(Context context, List<PersonalFileWorkFragmentEntity.ResumesBean> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    public List<PersonalFileWorkFragmentEntity.ResumesBean> getList() {
        return list;
    }

    public void setList(List<PersonalFileWorkFragmentEntity.ResumesBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_personal_file_work_resume, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.work_unit.setText(list.get(i).getWORK_COMPANY_NAME());
        holder.work_time.setText(list.get(i).getWORK_TIME());
//        if (mData.get(i).isChecked()) {
//            holder.rootView.setBackgroundResource(R.drawable.time_declare_pressed);
//        } else {
//            holder.rootView.setBackgroundResource(R.drawable.time_declare_normal);
//        }
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView work_unit;
        TextView work_time;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            work_unit = (TextView) itemView.findViewById(R.id.work_unit);
            work_time = (TextView) itemView.findViewById(R.id.work_time);
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
