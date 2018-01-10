package com.pvirtech.pzpolice.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.test.one.CaseQueryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TaskAdapter extends BaseQuickAdapter<CaseQueryEntity, BaseViewHolder> {

    public TaskAdapter(ArrayList<CaseQueryEntity> mCaseQueryEntityList) {
        super(R.layout.case_query_item, mCaseQueryEntityList);
    }

    public TaskAdapter(int dataSize, List<CaseQueryEntity> mCaseQueryEntityList) {
        super(R.layout.case_query_item, mCaseQueryEntityList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseQueryEntity item) {
        helper.setText(R.id.tv_title, item.getAjay())
                .setText(R.id.tv_case_id, mContext.getResources().getString(R.string.case_id) + item.getAjbh())
                .setText(R.id.tv_case_link, mContext.getResources().getString(R.string.case_link) + item.getAjhj())
                .setText(R.id.tv_case_address, mContext.getResources().getString(R.string.case_address) + item.getAjdd())
                .setText(R.id.tv_case_time, mContext.getResources().getString(R.string.case_time) + item.getAjfssj())
        ;
      /*  .addOnClickListener(R.id.tweetAvatar)
                .addOnClickListener(R.id.tweetName)
                .linkify(R.id.tweetText)*/
//
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().placeholder(R.mipmap.def_head).transform(new GlideCircleTransform
// (mContext)).into((ImageView) helper.getView(R.id.tweetAvatar));
    }


}