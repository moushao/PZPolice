package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.DutyFromActivityContract;

import butterknife.ButterKnife;

/**
 * 值班表
 */
public class DutyFromActivity extends BaseActivity implements DutyFromActivityContract.View {
    DutyFromActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_from);
        ButterKnife.bind(this);
        initTitleView("值班表");
        mContext = DutyFromActivity.this;
        TAG = "MyAssessmentActivity";
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
