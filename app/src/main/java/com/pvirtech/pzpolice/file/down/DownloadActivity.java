package com.pvirtech.pzpolice.file.down;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

public class DownloadActivity extends BaseActivity {
    private Context mContext;
    private DownloadManager downloadManager;
    Handler handler = new Handler();
    Runnable runnable;
    /*test*/
    @BindView(R.id.task_listView)
    ListView task_listView;
    DownBaseAdapter mBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initTitleView("我的下载");
        mContext = DownloadActivity.this;
        List<DownloadInfo> tasks = DownloadManager.getInstance().getAllTask();

        mBaseAdapter = new DownBaseAdapter(tasks, mContext);
        task_listView.setAdapter(mBaseAdapter);
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                mBaseAdapter.notifyDataSetChanged();
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


}
