package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.AttendanceContract;
import com.pvirtech.pzpolice.ui.model.AttendanceModelImpl;

/**
 * Created by Administrator on 2017/03/03
 */

public class AttendancePresenterImpl implements AttendanceContract.Presenter {
    AttendanceModelImpl model = new AttendanceModelImpl();
    AttendanceContract.View view;

    public AttendancePresenterImpl(AttendanceContract.View view) {
        this.view = view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(String date, String loginName) {
        view.onInitData();
    }

    @Override
    public void submit(String latitude, String longitude, String addres, String Id, String time) {

    }
}