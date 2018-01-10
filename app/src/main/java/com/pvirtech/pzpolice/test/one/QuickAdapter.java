package com.pvirtech.pzpolice.test.one;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickAdapter extends BaseQuickAdapter<CaseQueryEntity, BaseViewHolder> {

    public QuickAdapter(ArrayList<CaseQueryEntity> mCaseQueryEntityList) {
        super(R.layout.case_query_item, mCaseQueryEntityList);
    }

    public QuickAdapter(int dataSize, List<CaseQueryEntity> mCaseQueryEntityList) {
        super(R.layout.case_query_item, mCaseQueryEntityList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseQueryEntity item) {
        helper.setText(R.id.tv_title, item.getAjay()).setText(R.id.tv_case_id, mContext.getResources().getString(R.string.case_id) + item.getAjbh()
        ).setText(R.id.tv_case_link, mContext.getResources().getString(R.string.case_link) + item.getAjhj()).setText(R.id.tv_case_address, mContext
                .getResources().getString(R.string.case_address) + item.getAjdd()).setText(R.id.tv_case_time, mContext.getResources().getString(R
                .string.case_time) + item.getAjfssj());

    }


}
