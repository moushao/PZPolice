package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.enumeration.ToExamineEnum;

import java.util.List;


/**
 * Created by pd on 2016/9/29.
 */

public class BusinessWorkAdapter extends BaseQuickAdapter<BusinessWorkEntity, BaseViewHolder> {
    public interface OnRecyclerViewListener {
        void onItemClick(BusinessWorkEntity item);

        boolean onItemLongClick(BusinessWorkEntity item);
    }

    private Context mContext = null;
    private List<BusinessWorkEntity> list = null;
    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public BusinessWorkAdapter(Context context, List<BusinessWorkEntity> list, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_business_work, list);
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, final BusinessWorkEntity item) {
        if (position % 4 == 0) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_1);
        } else if (position % 4 == 1) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_2);
        } else if (position % 4 == 2) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_3);
        } else if (position % 4 == 3) {
            helper.setImageResource(R.id.iv_icon, R.mipmap.user_4);
        }
        helper.setText(R.id.tv_name, item.getNAME()).setText(R.id.tv_type, item.getTASK_NAME()).setText(R.id.tv_count, getdata(item.getTOTAL_SCORE
                () + "分"))
                .setText(R.id.tv_total, mContext.getResources().getString(R.string.assessment_methods) + getdata(item.getTASK_DESC())).setText(R.id
                        .tv_time,
                mContext.getResources().getString(R.string.assessment_time) + item.getDATE_STR()).setText(R.id.tv_reason, mContext
                .getResources()
                .getString(R.string.assessment_situation) + "已完成(" + item.getComplete() + "/" + item.getTotal() + ")");

        for (ToExamineEnum toExamineEnum : ToExamineEnum.values()) {
            if (toExamineEnum.getToExamineEntity().getState() == item.getAUDIT_STATE()) {
                helper.setText(R.id.tv_status, toExamineEnum.getToExamineEntity().getNotice());
            }
        }
        helper.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(item);
            }
        });

    }

    public List<BusinessWorkEntity> getList() {
        return list;
    }

    public void setList(List<BusinessWorkEntity> list) {
        this.list = list;
    }

    public String getdata(String data) {
        if (TextUtils.isEmpty(data)) {
            data = "";
        }
        return data;
    }
}