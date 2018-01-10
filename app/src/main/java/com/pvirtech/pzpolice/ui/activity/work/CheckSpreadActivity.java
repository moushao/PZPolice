package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 申报-基础类-检查宣传类  主界面
 */
public class CheckSpreadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_spread);
        ButterKnife.bind(this);
        initTitleView("检查宣传");
        mContext = CheckSpreadActivity.this;
        TAG = "CheckSpreadActivity";


    }
}
