package com.pvirtech.pzpolice.file.down;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pd on 2016/8/31.
 */
public class DownBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<DownloadInfo> data = new ArrayList<>();

    public DownBaseAdapter(List<DownloadInfo> data, Context context) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DownloadInfo getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (view == null) {
            view = mInflater.inflate(R.layout.list_down_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.tv_task_name = (TextView) view.findViewById(R.id.task_name);
            holder.tv_task_status = (TextView) view.findViewById(R.id.task_status);
            holder.pb_task_progress = (ProgressBar) view.findViewById(R.id.task_progress);
            view.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) view.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final DownloadInfo task = data.get(i);
        holder.tv_task_name.setText(task.getFileName());
        if (task.getState() == FileState.NONE) {
            holder.tv_task_status.setText("无状态");
        } else if (task.getState() == FileState.WAITING) {
            holder.tv_task_status.setText("等待");
        } else if (task.getState() == FileState.DOING) {
            holder.tv_task_status.setText("下载中");
        } else if (task.getState() == FileState.PAUSE) {
            holder.tv_task_status.setText("暂停");
        } else if (task.getState() == FileState.FINISH) {
            holder.tv_task_status.setText("完成");
        } else if (task.getState() == FileState.ERROR) {
            holder.tv_task_status.setText("错误");
        }
//        int progress = (int) (task.getDownloadLength() * 100 / task.getTotalLength());
        holder.pb_task_progress.setProgress(1);
        /**为Button添加点击事件*/
        holder.tv_task_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;

    }


   static class ViewHolder {
        private TextView tv_task_name;
        public TextView tv_task_status;
        public ProgressBar pb_task_progress;
    }
}
