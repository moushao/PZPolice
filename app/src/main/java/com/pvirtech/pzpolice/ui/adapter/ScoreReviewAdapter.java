package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyscoreEntity;
import com.pvirtech.pzpolice.enumeration.MyscoreEnum;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class ScoreReviewAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    List<MyscoreEntity> list;

    private OnRecyclerViewListener onRecyclerViewListener;


    public ScoreReviewAdapter(Context context, List<MyscoreEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_score_review_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MyscoreEntity entity = list.get(i);
        if (!TextUtils.isEmpty(entity.getType())) {
            for (MyscoreEnum myscoreEnum : MyscoreEnum.values()) {
                if (entity.getType().equals(myscoreEnum.getValue().getType())) {
                    entity.setName(myscoreEnum.getValue().getName());
                    break;
                }
            }
        }
        holder.tv_name.setText(entity.getName());
        holder.tv_state.setText(entity.getScore() + "");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView picture;
        TextView tv_name, tv_state;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
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

    public List<MyscoreEntity> getList() {
        return list;
    }

    public void setList(List<MyscoreEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
