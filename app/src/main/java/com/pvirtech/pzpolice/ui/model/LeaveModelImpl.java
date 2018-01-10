package com.pvirtech.pzpolice.ui.model;

import android.text.TextUtils;

import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.contract.LeaveContract;

/**
 * Created by Administrator on 2017/02/28
 */

public class LeaveModelImpl implements LeaveContract.Model {

    private String strLeaveType;
    private String strLeaveStartTime;
    private String strLeaveEndTime;
    private String strLeaveReason;
    private String strLeaveTotalTime;

    public LeaveModelImpl() {
    }

    public LeaveModelImpl(String strLeaveType, String strLeaveStartTime, String strLeaveEndTime, String strLeaveReason, String
            strLeaveTotalTime) {
        this.strLeaveType = strLeaveType;
        this.strLeaveStartTime = strLeaveStartTime;
        this.strLeaveEndTime = strLeaveEndTime;
        this.strLeaveReason = strLeaveReason;
        this.strLeaveTotalTime = strLeaveTotalTime;
    }

    public String getStrLeaveTotalTime() {
        return strLeaveTotalTime;
    }

    public void setStrLeaveTotalTime(String strLeaveTotalTime) {
        this.strLeaveTotalTime = strLeaveTotalTime;
    }

    public String getStrLeaveType() {
        return strLeaveType;
    }

    public void setStrLeaveType(String strLeaveType) {
        this.strLeaveType = strLeaveType;
    }

    public String getStrLeaveStartTime() {
        return strLeaveStartTime;
    }

    public void setStrLeaveStartTime(String strLeaveStartTime) {
        this.strLeaveStartTime = strLeaveStartTime;
    }

    public String getStrLeaveEndTime() {
        return strLeaveEndTime;
    }

    public void setStrLeaveEndTime(String strLeaveEndTime) {
        this.strLeaveEndTime = strLeaveEndTime;
    }

    public String getStrLeaveReason() {
        return strLeaveReason;
    }

    public void setStrLeaveReason(String strLeaveReason) {
        this.strLeaveReason = strLeaveReason;
    }

    @Override
    public String checkValidity() {
        if (TextUtils.isEmpty(strLeaveType)) {
            return "请选择请假类型";
        } else if (TextUtils.isEmpty(strLeaveStartTime)) {
            return "请选择请假开始时间";
        } else if (TextUtils.isEmpty(strLeaveEndTime)) {
            return "请选择请假结束时间";
        } else if (TextUtils.isEmpty(strLeaveReason)) {
            return "请输入请假事由";
        } else {
            return OperationUtils.compareTime(strLeaveStartTime, strLeaveEndTime);
        }
    }
}