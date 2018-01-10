package com.pvirtech.pzpolice.ui.contract;

/**
 * Created by youpengda on 2017/3/3.
 */

public interface AttendanceContract {

    public interface View extends BaseContractView {
        void onInitData();
        void initViewASignOut(String time, String address, String status);

        void initViewASign(String time, String address, String status);
    }

    public interface Presenter {
        void initView();

        void initData(String date, String loginName);

        void submit(String latitude, String longitude, String addres, String Id, String time);
    }

    public interface Model {

        String checkValidity();
    }

}