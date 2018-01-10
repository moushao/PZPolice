package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.adapter.TitleFragmentPagerAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.fragment.TaskContentFragment;
import com.pvirtech.pzpolice.ui.fragment.TaskTrajectoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);
        initTitleView("任务详情");
        mContext = TaskDetailsActivity.this;
        TAG = "TaskDetailsActivity";
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TaskContentFragment());
        fragments.add(new TaskTrajectoryFragment());
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"任务内容", "任务轨迹"});
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
    }
}
