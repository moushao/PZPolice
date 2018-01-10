package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;
import android.view.Menu;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.WorkOneContract;
import com.pvirtech.pzpolice.ui.presenter.WorkOnePresenterImpl;

import butterknife.ButterKnife;

public class WorkOneActivity extends BaseActivity implements WorkOneContract.View {

    WorkOneContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_one);
        ButterKnife.bind(this);
        initTitleView("aaaaaaaaaaaa");
        mContext = WorkOneActivity.this;
        TAG = "WorkOneActivity";
        presenter = new WorkOnePresenterImpl(WorkOneActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void getDataSucessed(String data) {
        hideLoading();
    }


    @Override
    public void getDataFailed(String data) {
        showError("");
    }
}
