package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class MyTasksEntity implements Parcelable {


    /**
     * CASE_NAME : 涉赌案件
     * CASE_TYPE : 3
     * DATE_STR : 2017-05-08
     * ID : 60
     * SCORE_WAY : 2
     * STATE : 0
     * TASK_CHILDE_TYPE : 1
     * TASK_DESC : ggff
     * TASK_NAME : 涉赌拘留
     * COMPLETED_COUNT :1
     * TOTAL_COUNT : 5
     * TOTAL_SCORE : 25.0
     * AUDIT_STATE:1
     * TASK_NO
     * NAME
     * CREATE_TIME
     */
    private String CREATE_TIME;
    private String CASE_NAME;
    private int CASE_TYPE;
    private String DATE_STR;
    private String ID;
    private int SCORE_WAY;
    private int STATE;
    private int AUDIT_STATE;
    private int TASK_CHILDE_TYPE;
    private String TASK_DESC;
    private String TASK_NAME;
    private int COMPLETED_COUNT;
    private int TOTAL_COUNT;
    private double TOTAL_SCORE;
    private String TASK_NO;
    private String NAME;

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getTASK_NO() {
        return TASK_NO;
    }

    public void setTASK_NO(String TASK_NO) {
        this.TASK_NO = TASK_NO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCASE_NAME() {
        return CASE_NAME;
    }

    public void setCASE_NAME(String CASE_NAME) {
        this.CASE_NAME = CASE_NAME;
    }

    public int getCASE_TYPE() {
        return CASE_TYPE;
    }

    public void setCASE_TYPE(int CASE_TYPE) {
        this.CASE_TYPE = CASE_TYPE;
    }

    public String getDATE_STR() {
        return DATE_STR;
    }

    public void setDATE_STR(String DATE_STR) {
        this.DATE_STR = DATE_STR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getSCORE_WAY() {
        return SCORE_WAY;
    }

    public void setSCORE_WAY(int SCORE_WAY) {
        this.SCORE_WAY = SCORE_WAY;
    }

    public int getSTATE() {
        return STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public int getTASK_CHILDE_TYPE() {
        return TASK_CHILDE_TYPE;
    }

    public void setTASK_CHILDE_TYPE(int TASK_CHILDE_TYPE) {
        this.TASK_CHILDE_TYPE = TASK_CHILDE_TYPE;
    }

    public String getTASK_DESC() {
        return TASK_DESC;
    }

    public void setTASK_DESC(String TASK_DESC) {
        this.TASK_DESC = TASK_DESC;
    }

    public String getTASK_NAME() {
        return TASK_NAME;
    }

    public void setTASK_NAME(String TASK_NAME) {
        this.TASK_NAME = TASK_NAME;
    }

    public int getCOMPLETED_COUNT() {
        return COMPLETED_COUNT;
    }

    public void setCOMPLETED_COUNT(int COMPLETED_COUNT) {
        this.COMPLETED_COUNT = COMPLETED_COUNT;
    }

    public int getTOTAL_COUNT() {
        return TOTAL_COUNT;
    }

    public void setTOTAL_COUNT(int TOTAL_COUNT) {
        this.TOTAL_COUNT = TOTAL_COUNT;
    }

    public double getTOTAL_SCORE() {
        return TOTAL_SCORE;
    }

    public void setTOTAL_SCORE(double TOTAL_SCORE) {
        this.TOTAL_SCORE = TOTAL_SCORE;
    }

    public int getAUDIT_STATE() {
        return AUDIT_STATE;
    }

    public void setAUDIT_STATE(int AUDIT_STATE) {
        this.AUDIT_STATE = AUDIT_STATE;
    }

    public MyTasksEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CREATE_TIME);
        dest.writeString(this.CASE_NAME);
        dest.writeInt(this.CASE_TYPE);
        dest.writeString(this.DATE_STR);
        dest.writeString(this.ID);
        dest.writeInt(this.SCORE_WAY);
        dest.writeInt(this.STATE);
        dest.writeInt(this.AUDIT_STATE);
        dest.writeInt(this.TASK_CHILDE_TYPE);
        dest.writeString(this.TASK_DESC);
        dest.writeString(this.TASK_NAME);
        dest.writeInt(this.COMPLETED_COUNT);
        dest.writeInt(this.TOTAL_COUNT);
        dest.writeDouble(this.TOTAL_SCORE);
        dest.writeString(this.TASK_NO);
        dest.writeString(this.NAME);
    }

    protected MyTasksEntity(Parcel in) {
        this.CREATE_TIME = in.readString();
        this.CASE_NAME = in.readString();
        this.CASE_TYPE = in.readInt();
        this.DATE_STR = in.readString();
        this.ID = in.readString();
        this.SCORE_WAY = in.readInt();
        this.STATE = in.readInt();
        this.AUDIT_STATE = in.readInt();
        this.TASK_CHILDE_TYPE = in.readInt();
        this.TASK_DESC = in.readString();
        this.TASK_NAME = in.readString();
        this.COMPLETED_COUNT = in.readInt();
        this.TOTAL_COUNT = in.readInt();
        this.TOTAL_SCORE = in.readDouble();
        this.TASK_NO = in.readString();
        this.NAME = in.readString();
    }

    public static final Creator<MyTasksEntity> CREATOR = new Creator<MyTasksEntity>() {
        @Override
        public MyTasksEntity createFromParcel(Parcel source) {
            return new MyTasksEntity(source);
        }

        @Override
        public MyTasksEntity[] newArray(int size) {
            return new MyTasksEntity[size];
        }
    };
}
