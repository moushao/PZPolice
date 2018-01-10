package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/4/25.
 * 任务轨迹实体类
 */

public class TaskTrajectory {

    /**
     * AUDIT_STATE : 9
     * AUDIT_TIME : 2017-04-25 15:37:39
     * AUDIT_USER : 李教导员
     */

    private int AUDIT_STATE;
    private String AUDIT_TIME;
    private String AUDIT_USER;

    public int getAUDIT_STATE() {
        return AUDIT_STATE;
    }

    public void setAUDIT_STATE(int AUDIT_STATE) {
        this.AUDIT_STATE = AUDIT_STATE;
    }

    public String getAUDIT_TIME() {
        return AUDIT_TIME;
    }

    public void setAUDIT_TIME(String AUDIT_TIME) {
        this.AUDIT_TIME = AUDIT_TIME;
    }

    public String getAUDIT_USER() {
        return AUDIT_USER;
    }

    public void setAUDIT_USER(String AUDIT_USER) {
        this.AUDIT_USER = AUDIT_USER;
    }
}
