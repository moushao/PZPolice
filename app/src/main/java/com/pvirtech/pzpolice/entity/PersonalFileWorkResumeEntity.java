package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class PersonalFileWorkResumeEntity {
    private String workUnit;
    private String workTime;
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }


    private boolean isChecked = false;
    public PersonalFileWorkResumeEntity(String workUnit,String workTime){
        this.workUnit = workUnit;
        this.workTime = workTime;

    }



    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
