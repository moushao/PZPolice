package com.pvirtech.pzpolice.ui.activity.work.needdeal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.adapter.TitleFragmentPagerAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待办事项
 */
public class NeedToeDealActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_toe_deal);
        ButterKnife.bind(this);
        initTitleView("待办事项");
        mContext = NeedToeDealActivity.this;
        TAG = "NeedToeDealActivity";
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TeamBuildingFragment());
        fragments.add(new BusinessWorkFragment());
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"队伍建设", "业务工作"});
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
    }
}
