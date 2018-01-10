package com.pvirtech.pzpolice.ui.activity.log;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.pvirtech.multiimageselectorlibrary.MultiImageSelector;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 工作日志 主界面
 */

public class WorkLogActivity extends BaseActivity{
    List<Fragment> fragments = new ArrayList<>();
    WriteWorkLogFragment writeWorkLogFragment = new WriteWorkLogFragment();
    ReadWorkLogFragment readWorkLogFragment = new ReadWorkLogFragment();
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

    android.support.v4.app.FragmentManager fm = WorkLogActivity.this.getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_log);
        ButterKnife.bind(this);
        initTitleView("工作日志");
        mContext = WorkLogActivity.this;
        TAG = "WorkLogActivity";

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragments.add(writeWorkLogFragment);
        fragments.add(readWorkLogFragment);
        switchFragment(0);
    }


    private void switchFragment(int index) {
        ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                if (!fragments.get(index).isAdded()) {
                    ft.add(R.id.content, fragments.get(index));
                }
                ft.show(fragments.get(index));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    private ArrayList<String> mSelectPath = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.pvirtech.multiimageselectorlibrary.Constant.REQUEST_IMAGE) {
            if (resultCode == com.pvirtech.multiimageselectorlibrary.Constant.RESULT_IMAGE) {
                ArrayList<String> list = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                mSelectPath.addAll(list);
                writeWorkLogFragment.setmSelectPath(mSelectPath);
            }
        }
        if (resultCode == com.cjt2325.cameralibrary.Constant.TAKE_PHOTO) {
            ArrayList<String> list = data.getStringArrayListExtra(com.cjt2325.cameralibrary.Constant.PHOTOS);
            mSelectPath.addAll(list);
            writeWorkLogFragment.setmSelectPath(mSelectPath);
        }
    }
}
