package com.pvirtech.pzpolice.ui.activity.leave;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.third.RefreshEvent;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.LeaveContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * 请假主界面
 */
public class LeaveActivity extends BaseActivity implements LeaveContract.View {
    LeaveContract.Presenter presenter;

    List<Fragment> fragments = new ArrayList<>();
    LeaveFragment leaveFragment = new LeaveFragment();
    SickFragment sickFragment = new SickFragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    switchFragment(0);
                    return true;
                case R.id.item2:
                    switchFragment(1);
                    return true;

            }
            return false;
        }

    };

    android.support.v4.app.FragmentManager fm = LeaveActivity.this.getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction ft;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        ButterKnife.bind(this);
        mContext = LeaveActivity.this;
        initTitleView(mContext.getResources().getString(R.string.leave));
        TAG = "LeaveActivity";

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragments.add(leaveFragment);
        fragments.add(sickFragment);
        switchFragment(0);
    }


    @Override
    public void selectedTotalTime(String data) {

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
        System.out.println("aa");
        Toasty.warning(mContext, data).show();
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


    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (TAG.equals(event.getMessage())) {
            navigation.setSelectedItemId(R.id.item2);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
