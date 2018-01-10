package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.DeclareEntity;

import java.util.List;



/**
 * Created by Administrator on 2017/4/1.
 */

public class DeclareEntityAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<DeclareEntity.DAYRECORDBean> mData;

    private OnRecyclerViewListener onRecyclerViewListener;


    public DeclareEntityAdapter(Context context, List<DeclareEntity.DAYRECORDBean> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_declare_entity, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    public List<DeclareEntity.DAYRECORDBean> getmData() {
        return mData;
    }

    public void setmData(List<DeclareEntity.DAYRECORDBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        DeclareEntity.DAYRECORDBean entity = mData.get(i);
        holder.tv_time.setText(entity.getStartTime());
        holder.tv_time02.setText(entity.getEndTime());
        holder.tv_position.setText(entity.getLocation());
        holder.tv_type.setText(entity.getType());
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_type,tv_time, tv_time02;
        TextView tv_position;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_time02 = (TextView) itemView.findViewById(R.id.tv_time02);
            tv_position = (TextView) itemView.findViewById(R.id.tv_position);
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
