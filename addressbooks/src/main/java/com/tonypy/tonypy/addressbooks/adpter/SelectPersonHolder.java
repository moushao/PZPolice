package com.tonypy.tonypy.addressbooks.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tonypy.tonypy.addressbooks.R;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class SelectPersonHolder extends RecyclerView.ViewHolder {
    public TextView descView;
    public LinearLayout ll_person;

    public SelectPersonHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        descView = (TextView) itemView.findViewById(R.id.tv_desc);
        ll_person = (LinearLayout) itemView.findViewById(R.id.ll_person);
    }
}
