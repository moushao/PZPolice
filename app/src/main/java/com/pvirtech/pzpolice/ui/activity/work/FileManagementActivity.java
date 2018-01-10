package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.FileManagementActivityContract;
import com.pvirtech.pzpolice.ui.presenter.FileManagementActivityPresenterImpl;

import butterknife.ButterKnife;

/**
 * 档案管理
 */
public class FileManagementActivity extends BaseActivity implements FileManagementActivityContract.View {
    FileManagementActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_management);
        ButterKnife.bind(this);
        initTitleView("档案管理");
        mContext = FileManagementActivity.this;
        TAG = "MyAssessmentActivity";
        presenter = new FileManagementActivityPresenterImpl(FileManagementActivity.this);
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
