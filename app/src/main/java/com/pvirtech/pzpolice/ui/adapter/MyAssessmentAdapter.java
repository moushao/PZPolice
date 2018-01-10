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
import com.pvirtech.pzpolice.entity.MyAssessmentEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class MyAssessmentAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;
    private List<MyAssessmentEntity> list;
    private OnRecyclerViewListener onRecyclerViewListener;

    public MyAssessmentAdapter(Context context, List<MyAssessmentEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_my_assessment, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MyAssessmentEntity myAssessmentEntity = list.get(i);
        holder.iv_icon.setImageResource(myAssessmentEntity.getIcon());
        holder.tv_title.setText(myAssessmentEntity.getContent());
//        holder.tv_time.setText(myAssessmentEntity.getHasdone() + "/" + myAssessmentEntity.getAll());
//        holder.tv_point.setText(myAssessmentEntity.getPoint());
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
        TextView tv_point;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_point = (TextView)itemView.findViewById(R.id.tv_point);
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

    public List<MyAssessmentEntity> getList() {
        return list;
    }

    public void setList(List<MyAssessmentEntity> list) {
        this.list = list;
    }

    public void removeData() {
        list.clear();
        notifyDataSetChanged();
    }
}
