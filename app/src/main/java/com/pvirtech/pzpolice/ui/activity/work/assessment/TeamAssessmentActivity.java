package com.pvirtech.pzpolice.ui.activity.work.assessment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;


/**
 * 队伍考核
 */
public class TeamAssessmentActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    List<Fragment> fragments = new ArrayList<>();
    TeamAssessmentFragment teamAssessmentFragment = new TeamAssessmentFragment();
    TeamAssessmentRecordFragment teamAssessmentRecordFragment = new TeamAssessmentRecordFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.team_assessment:
                    switchFragment(0);
                    toolbar.setTitle(mContext.getString(R.string.team_assessment));
                    return true;
                case R.id.team_assessment_recrod:
                    switchFragment(1);
                    toolbar.setTitle(mContext.getString(R.string.team_assessment_recrod));
                    return true;

            }
            return false;
        }

    };

    FragmentManager fm = TeamAssessmentActivity.this.getSupportFragmentManager();
    FragmentTransaction ft;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_assessment);
        ButterKnife.bind(this);
        initTitleView(mContext.getResources().getString(R.string.team_assessment));
        TAG = "TeamAssessmentActivity";

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragments.add(teamAssessmentFragment);
        fragments.add(teamAssessmentRecordFragment);
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

}
