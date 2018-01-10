package com.pvirtech.pzpolice.test.one;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;

import java.util.List;

/**
 * Created by pd on 2016/9/29.
 */

public class CaseQueryAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    private List<CaseQueryEntity> mData = null;

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public CaseQueryAdapter(Context context, List<CaseQueryEntity> mData, OnRecyclerViewListener onRecyclerViewListener) {
        this.mData = mData;
        this.onRecyclerViewListener = onRecyclerViewListener;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.case_query_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new CaseAueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CaseAueryViewHolder holder = (CaseAueryViewHolder) viewHolder;
        holder.tv_title.setText(mData.get(i).getAjay());
        holder.tv_case_id.setText(mContext.getResources().getString(R.string.case_id) + mData.get(i).getAjbh());
        holder.tv_case_link.setText(mContext.getResources().getString(R.string.case_link) + mData.get(i).getAjhj());
        holder.tv_case_address.setText(mContext.getResources().getString(R.string.case_address) + mData.get(i).getAjdd());
        holder.tv_case_time.setText(mContext.getResources().getString(R.string.case_time) + mData.get(i).getAjfssj());
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CaseAueryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private View rootView;
        private TextView tv_title, tv_case_id, tv_case_link, tv_case_address, tv_case_time;
        private int position;
        private CardView cardView;

        public CaseAueryViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.root_view);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_case_id = (TextView) itemView.findViewById(R.id.tv_case_id);
            tv_case_link = (TextView) itemView.findViewById(R.id.tv_case_link);
            tv_case_address = (TextView) itemView.findViewById(R.id.tv_case_address);
            tv_case_time = (TextView) itemView.findViewById(R.id.tv_case_time);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
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

//    private int mLastPosition = -1;

//    public void showItemAnim(final View view, final int position) {
//        if (position > mLastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    view.setAlpha(1);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
//            view.startAnimation(animation);
//            mLastPosition = position;
//        }
//    }
}