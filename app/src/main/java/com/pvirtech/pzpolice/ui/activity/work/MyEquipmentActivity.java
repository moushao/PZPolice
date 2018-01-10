package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.MyEquipmentActivityContract;
import com.pvirtech.pzpolice.ui.presenter.MyEquipmentActivityPresenterImpl;

import butterknife.ButterKnife;

public class MyEquipmentActivity extends BaseActivity implements MyEquipmentActivityContract.View {
    MyEquipmentActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_equipment);
        ButterKnife.bind(this);
        initTitleView("我的装备");
        mContext = MyEquipmentActivity.this;
        TAG = "MyEquipmentActivity";
        presenter = new MyEquipmentActivityPresenterImpl(MyEquipmentActivity.this);
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
