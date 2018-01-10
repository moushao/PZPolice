package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.main.OperationUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by youpengda on 2017/2/9.
 */

public class TaskFragmentAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(MyTasksEntity entity);
    }

    private Context mContext = null;
    private List<MyTasksEntity> list;
    private OnRecyclerViewListener onRecyclerViewListener;
    int[] colaors_random;

    public TaskFragmentAdapter(Context context, List<MyTasksEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
        colaors_random = OperationUtils.getRandomColor(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_task_fragment, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MyTasksEntity entity = list.get(i);
        holder.tv_daily_tasks_name.setText(entity.getTASK_NAME());
        holder.tv_daily_tasks_time.setText(entity.getDATE_STR());
        holder.tv_daily_tasks_content.setText(entity.getTASK_DESC());
        if (entity.getSTATE() == 1) {
            holder.tv_daily_tasks_completion.setText("已完成(" + entity.getCOMPLETED_COUNT() + "/" + entity.getTOTAL_COUNT() +
                    ")");
            holder.tv_daily_tasks_completion.setTextColor(mContext.getResources().getColor(R.color.text_title));
        } else {
            holder.tv_daily_tasks_completion.setText("未完成(" + entity.getCOMPLETED_COUNT() + "/" + entity.getTOTAL_COUNT() +
                    ")");
            holder.tv_daily_tasks_completion.setTextColor(mContext.getResources().getColor(R.color.text_orange));
        }

        holder.profile_image.setFillColor(colaors_random[i % colaors_random.length]);
        holder.position = i;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<MyTasksEntity> getList() {
        return list;
    }

    public void setList(List<MyTasksEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CircleImageView profile_image;
        TextView tv_daily_tasks_name, tv_daily_tasks_time, tv_daily_tasks_content, tv_daily_tasks_completion;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_daily_tasks_name = (TextView) itemView.findViewById(R.id.tv_daily_tasks_name);
            tv_daily_tasks_time = (TextView) itemView.findViewById(R.id.tv_daily_tasks_time);
            tv_daily_tasks_content = (TextView) itemView.findViewById(R.id.tv_daily_tasks_content);
            tv_daily_tasks_completion = (TextView) itemView.findViewById(R.id.tv_daily_tasks_completion);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(list.get(position));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
//                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }
}
