package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/3/27.
 */

public class VacationDetailsEntity {
    /**
     * AUDIT_STATE : -1
     * AUDIT_TIME : 2017-04-07 17:47:57
     * AUDIT_USER : 1
     */

    private int AUDIT_STATE;
    private String AUDIT_TIME;
    private String AUDIT_USER;

    public VacationDetailsEntity(int AUDIT_STATE, String AUDIT_TIME, String AUDIT_USER) {
        this.AUDIT_STATE = AUDIT_STATE;
        this.AUDIT_TIME = AUDIT_TIME;
        this.AUDIT_USER = AUDIT_USER;
    }

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
