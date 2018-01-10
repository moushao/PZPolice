package com.pvirtech.pzpolice.ui.contract;

/**
 * Created by youpengda on 2017/3/2.
 */

public interface SickLeaveContract {


    public interface View extends BaseContractView {
        void initView(String strId, String strLeaveType, String strLeaveStartTime, String strLeaveEndTime, String
                strLeaveReason, String strLeaveTotalTime);


    }

    public interface Presenter {
        void initView();

        void submit(String strId, String strStartTime, String strEndTime, String strReason);
    }

    public interface Model {

        String checkValidity();
    }


}