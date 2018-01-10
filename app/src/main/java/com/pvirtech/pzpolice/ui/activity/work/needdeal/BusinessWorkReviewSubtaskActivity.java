package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.ui.adapter.TitleFragmentPagerAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看任务及子任务详情
 */
public class BusinessWorkReviewSubtaskActivity extends BaseActivity {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    BusinessWorkEntity businessWorkEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_work_review_subtask);
        ButterKnife.bind(this);
        initTitleView("任务及子任务详情");
        mContext = BusinessWorkReviewSubtaskActivity.this;
        TAG = "BusinessWorkReviewSubtaskActivity";


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        businessWorkEntity = bundle.getParcelable("data");


        BusinessWorkReviewSubtaskContentFragment businessWorkReviewSubtaskContentFragment = new BusinessWorkReviewSubtaskContentFragment();
        businessWorkReviewSubtaskContentFragment.setBusinessWorkEntity(businessWorkEntity);

        BusinessWorkReviewSubtaskTrajectoryFragment businessWorkReviewSubtaskTrajectoryFragment = new BusinessWorkReviewSubtaskTrajectoryFragment();
        businessWorkReviewSubtaskTrajectoryFragment.setTaksId(businessWorkEntity.getID());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(businessWorkReviewSubtaskContentFragment);
        fragments.add(businessWorkReviewSubtaskTrajectoryFragment);
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"任务内容", "任务轨迹"});
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);


    }


}
