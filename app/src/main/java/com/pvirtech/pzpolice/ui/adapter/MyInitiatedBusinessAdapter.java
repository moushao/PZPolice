package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;
import com.pvirtech.pzpolice.main.AppValue;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class MyInitiatedBusinessAdapter extends BaseQuickAdapter<MyTasksEntity, BaseViewHolder> {


    public interface OnRecyclerViewListener {
        void onItemClick(MyTasksEntity item);

        boolean onItemLongClick(MyTasksEntity item);
    }

    private Context mContext = null;
    private List<MyTasksEntity> list = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public MyInitiatedBusinessAdapter(Context context, List<MyTasksEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_myinitiated_business, list);
        mContext = context;
        this.list = list;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MyTasksEntity item) {
        if (position % 4 == 0) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_1);
        } else if (position % 4 == 1) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_2);
        } else if (position % 4 == 2) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_3);
        } else if (position % 4 == 3) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_4);
        }
        String myName = "";
        try {
            myName = AppValue.getInstance().getmUserInfo().getNAME();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(myName)) {
            myName = "";
        }
        helper.setText(R.id.tv_name, myName).setText(R.id.tv_type, item.getTASK_NAME()).setText(R.id
                .tv_count, getdata(item
                .getTOTAL_SCORE
                        () + "分"))
                .setText(R.id.tv_total, mContext.getResources().getString(R.string.assessment_methods) + getdata(item.getTASK_DESC())).setText(R.id
                        .tv_time,
                mContext.getResources().getString(R.string.assessment_time) + item.getDATE_STR());


        if (item.getSTATE() == 1) {
            helper.setText(R.id.tv_reason, mContext
                    .getResources()
                    .getString(R.string.assessment_situation) + "已完成(" + item.getCOMPLETED_COUNT() + "/" + item.getTOTAL_COUNT() + ")");
        } else {
            helper.setText(R.id.tv_reason, mContext
                    .getResources()
                    .getString(R.string.assessment_situation) + "未完成(" + item.getCOMPLETED_COUNT() + "/" + item.getTOTAL_COUNT() + ")");
        }
        //设置状态
        for (ToExamineEnum myEnum : ToExamineEnum.values()) {
            if (myEnum.getToExamineEntity().getState() == item.getAUDIT_STATE())
                helper.setText(R.id.tv_status, myEnum.getToExamineEntity().getNotice());
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

    public String getdata(String data) {
        if (TextUtils.isEmpty(data)) {
            data = "";
        }
        return data;
    }
}
