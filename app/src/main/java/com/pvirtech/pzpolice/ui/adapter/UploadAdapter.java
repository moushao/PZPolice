package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.enumeration.FileEnum;
import com.pvirtech.pzpolice.file.upload.UploadInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class UploadAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;

    List<UploadInfo> list;

    private OnRecyclerViewListener onRecyclerViewListener;

    final int mGridWidth;

    public UploadAdapter(Context context, List<UploadInfo> list, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.list = list;
        int width = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }
        mGridWidth = width / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_upload_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        UploadInfo entity = list.get(i);
        File file = new File(entity.getTargetPath());
        Picasso.with(mContext).load(file).resize(mGridWidth, mGridWidth)
                .placeholder(R.mipmap.being_loaded).centerCrop().into(holder.picture);
        holder.tv_name.setText(entity.getFileName());
        if (entity.getState() == FileEnum.WAITING.getValue()) {
            holder.tv_state.setText("等待中");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.text_black));
        } else if (entity.getState() == FileEnum.DOING.getValue()) {
            holder.tv_state.setText("正在上传");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.text_black));
        } else if (entity.getState() == FileEnum.DOWN.getValue()) {
            holder.tv_state.setText("已上传");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.text_title));
        } else if (entity.getState() == FileEnum.ERROR.getValue()) {
            holder.tv_state.setText("出错");
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.text_orange));
        }

        holder.position = i;
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

    public List<UploadInfo> getList() {
        return list;
    }

    public void setList(List<UploadInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
