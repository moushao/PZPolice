package com.pvirtech.pzpolice.entity;

import java.util.List;

/**
 * Created by pd on 2017/7/19.
 */

public class TeamAssessmentRecordEntity {

    /**
     * BUILD_ID : 36
     * CHECK_STATE : 0
     * CHECK_TIME : 2017-07-19
     * CHECK_TYPE : 6
     * CREATE_TIME : 2017-07-19 16:50:37
     * OPERATE_USER : 3
     * REASON : 迟到
     * REMARKS :
     * SCORE : -3.0
     * USER_NO : 9
     * userlist : ["协警1"]
     */

    private String BUILD_ID;
    private String CHECK_STATE;
    private String CHECK_TIME;
    private String CHECK_TYPE;
    private String CREATE_TIME;
    private String OPERATE_USER;
    private String REASON;
    private String REMARKS;
    private double SCORE;
    private String USER_NO;
    private List<String> userlist;

    public String getBUILD_ID() {
        return BUILD_ID;
    }

    public void setBUILD_ID(String BUILD_ID) {
        this.BUILD_ID = BUILD_ID;
    }

    public String getCHECK_STATE() {
        return CHECK_STATE;
    }

    public void setCHECK_STATE(String CHECK_STATE) {
        this.CHECK_STATE = CHECK_STATE;
    }

    public String getCHECK_TIME() {
        return CHECK_TIME;
    }

    public void setCHECK_TIME(String CHECK_TIME) {
        this.CHECK_TIME = CHECK_TIME;
    }

    public String getCHECK_TYPE() {
        return CHECK_TYPE;
    }

    public void setCHECK_TYPE(String CHECK_TYPE) {
        this.CHECK_TYPE = CHECK_TYPE;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getOPERATE_USER() {
        return OPERATE_USER;
    }

    public void setOPERATE_USER(String OPERATE_USER) {
        this.OPERATE_USER = OPERATE_USER;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public double getSCORE() {
        return SCORE;
    }

    public void setSCORE(double SCORE) {
        this.SCORE = SCORE;
    }

    public String getUSER_NO() {
        return USER_NO;
    }

    public void setUSER_NO(String USER_NO) {
        this.USER_NO = USER_NO;
    }

    public List<String> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<String> userlist) {
        this.userlist = userlist;
    }
}
