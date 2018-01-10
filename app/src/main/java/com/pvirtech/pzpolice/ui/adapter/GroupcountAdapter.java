package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.GroupcountEntity;
import com.pvirtech.pzpolice.main.OperationUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pd on 2017/5/12.
 */

public class GroupcountAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;
    private List<GroupcountEntity> list;
    private OnRecyclerViewListener onRecyclerViewListener;
    int[] colaors_random;

    public GroupcountAdapter(Context context, List<GroupcountEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
        colaors_random = OperationUtils.getRandomColor(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_group_count, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        GroupcountEntity entity = list.get(i);
        holder.tv_name.setText(entity.getGROUP_NAME());
        if (entity.getCOMPLETE() >= entity.getTOTAL()) {
            holder.tv_state.setText("已完成(" + entity.getCOMPLETE() + "/" + entity.getTOTAL() + ")");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.text_title));
        } else {
            holder.tv_state.setText("未完成(" + entity.getCOMPLETE() + "/" + entity.getTOTAL() + ")");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
        holder.profile_image.setFillColor(colaors_random[i % colaors_random.length]);
        holder.position = i;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<GroupcountEntity> getList() {
        return list;
    }

    public void setList(List<GroupcountEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CircleImageView profile_image;
        TextView tv_name, tv_state;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
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
