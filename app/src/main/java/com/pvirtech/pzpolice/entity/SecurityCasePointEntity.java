package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class SecurityCasePointEntity {
    private String name;
    private double SCORE;
    private String USERID;

    public SecurityCasePointEntity(String name) {
        this.name = name;
    }

    public SecurityCasePointEntity(String name, double SCORE) {
        this.name = name;
        this.SCORE = SCORE;
    }

    public SecurityCasePointEntity(String name, String USERID) {
        this.name = name;
        this.USERID = USERID;
    }

    public SecurityCasePointEntity(String name, double SCORE, String USERID) {
        this.name = name;
        this.SCORE = SCORE;
        this.USERID = USERID;
    }

    public double getSCORE() {
        return SCORE;
    }

    public void setSCORE(double SCORE) {
        this.SCORE = SCORE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
