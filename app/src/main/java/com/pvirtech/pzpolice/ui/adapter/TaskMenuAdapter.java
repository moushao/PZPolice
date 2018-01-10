package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.Icon;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by youpengda on 2017/2/9.
 */

public class TaskMenuAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<Icon> mData = null;

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public TaskMenuAdapter(Context context, List<Icon> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.mData = mData;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_icon_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Icon icon = mData.get(i);
        holder.icon.setImageResource(icon.getIcon());
        holder.name.setText(icon.getName());
        holder.position = i;
        holder.badge.setBadgeNumber(icon.getCount());
//        showItemAnim(holder.cardView, i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView icon;
        TextView name;
        LinearLayout rootView;
        int position;
        Badge badge;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            name = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
            badge = new QBadgeView(mContext).bindTarget(itemView.findViewById(R.id.root_view));
            badge.setBadgeGravity(Gravity.END | Gravity.TOP);
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