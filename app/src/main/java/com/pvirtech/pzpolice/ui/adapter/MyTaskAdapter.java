package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyTasksEntity;

import java.util.List;

/**
 * Created by pd on 2017/5/14.
 */
public class MyTaskAdapter extends BaseQuickAdapter<MyTasksEntity, BaseViewHolder> {
    public interface OnRecyclerViewListener {
        void onItemClick(MyTasksEntity item);

        boolean onItemLongClick(MyTasksEntity item);
    }

    private Context mContext = null;
    private List<MyTasksEntity> list = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public MyTaskAdapter(Context context, List<MyTasksEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_my_task, list);
        mContext = context;
        this.list = list;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MyTasksEntity item) {

        helper.setText(R.id.tv_daily_tasks_name, item.getTASK_NAME()).setText(R.id.tv_daily_tasks_time, item.getDATE_STR() + "").setText(R.id
                .tv_daily_tasks_content, item.getTASK_DESC());

        if (item.getSTATE() == 1) {
            helper.setText(R.id.tv_daily_tasks_completion, "已完成(" + item.getCOMPLETED_COUNT() + "/" + item.getTOTAL_COUNT() + ")");
            helper.setTextColor(R.id.tv_daily_tasks_completion, mContext.getResources().getColor(R.color.text_title));
        } else {
            helper.setText(R.id.tv_daily_tasks_completion, "未完成(" + item.getCOMPLETED_COUNT() + "/" + item.getTOTAL_COUNT() + ")");
            helper.setTextColor(R.id.tv_daily_tasks_completion, mContext.getResources().getColor(R.color.text_orange));
        }

        helper.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(item);
            }
        });

    }

    public List<MyTasksEntity> getList() {
        return list;
    }

    public void setList(List<MyTasksEntity> list) {
        this.list = list;
    }

}
