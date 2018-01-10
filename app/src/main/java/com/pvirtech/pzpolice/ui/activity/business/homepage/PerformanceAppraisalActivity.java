package com.pvirtech.pzpolice.ui.activity.business.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.PerformanceAppraisalActivityContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 业务考核
 */
public class PerformanceAppraisalActivity extends BaseActivity implements PerformanceAppraisalActivityContract.View {
    PerformanceAppraisalActivityContract.Presenter presenter;

    List<Fragment> fragments = new ArrayList<>();
    CommunityFragment communityFragment = new CommunityFragment();
    InvestigationFragment investigationFragment = new InvestigationFragment();
    PatrolFragment patrolFragment = new PatrolFragment();
    OfficeFragment officeFragment = new OfficeFragment();


    FragmentManager fm = PerformanceAppraisalActivity.this.getSupportFragmentManager();
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_appraisal);
        ButterKnife.bind(this);
        initInfoTitleView(this.getResources().getString(R.string.performance_appraisal));
        mContext = PerformanceAppraisalActivity.this;
        TAG = "NeedToeDealActivity";

        fragments.add(communityFragment);
        fragments.add(investigationFragment);
        fragments.add(patrolFragment);
        fragments.add(officeFragment);


        /**
         * 底部导航栏
         */
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.community, mContext.getResources().getString(R.string.business_case))
                .setActiveColorResource(R.color.colorPrimary)).addItem(new BottomNavigationItem(R.mipmap.business_community, mContext.getResources
                ().getString(R.string.business_community)).setActiveColorResource(R.color.colorPrimary)).addItem(new BottomNavigationItem(R.mipmap
                .business_office, mContext.getResources().getString(R.string.business_office)).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.business_patrol, mContext.getResources().getString(R.string.business_patrol))
                        .setActiveColorResource(R.color.colorPrimary)).setFirstSelectedPosition(0).initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (fragments != null) {
                    if (position < fragments.size()) {
                        switchFragment(position);
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        switchFragment(0);
    }

    private void switchFragment(int index) {
        ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                if (!fragments.get(index).isAdded()) {
                    ft.add(R.id.layFrame, fragments.get(index));
                }
                ft.show(fragments.get(index));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    @Override
    public void submitting() {

    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitFailed() {

    }

    @Override
    public void showWarning(String data) {

    }

    @Override
    public void viewShowLoading(String msg) {

    }

    @Override
    public void viewHideLoading() {

    }

    @Override
    public void viewShowError(String msg) {

    }
}
