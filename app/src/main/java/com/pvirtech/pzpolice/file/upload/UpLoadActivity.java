package com.pvirtech.pzpolice.file.upload;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.adapter.UploadAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpLoadActivity extends BaseActivity {
    UploadAdapter adapter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);
        ButterKnife.bind(this);
        mContext = UpLoadActivity.this;
        initTitleView("我的上传");
        TAG = "MyTaskActivity";

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(runnable);


        adapter = new UploadAdapter(mContext, UploadManager.getInstance().getAllTask(), new UploadAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 4));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));

    }
}
