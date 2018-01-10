package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/5.
 */

public class BusinessWorkEntity implements Parcelable {


    /**
     * AUDIT_STATE : 1
     * DATE_STR : 2017-05-08
     * ID : 71
     * ISGROUP : 0
     * NAME : 李四
     * TASK_DESC : 每队不少于一件，否则每人扣10分
     * TASK_NAME : 消防案件
     * TASK_NO : 2017050817505951028202
     * TASK_TYPE : 6
     * TOTAL_SCORE : 25.0
     * complete : 3
     * total : 2
     * CREATE_TIME
     */

    private int AUDIT_STATE;
    private String DATE_STR;
    private String ID;
    private int ISGROUP;
    private String NAME;
    private String TASK_DESC;
    private String TASK_NAME;
    private String TASK_NO;
    private int TASK_TYPE;
    private double TOTAL_SCORE;
    private int complete;
    private int total;
    private String CREATE_TIME;

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getAUDIT_STATE() {
        return AUDIT_STATE;
    }

    public void setAUDIT_STATE(int AUDIT_STATE) {
        this.AUDIT_STATE = AUDIT_STATE;
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

    public int getISGROUP() {
        return ISGROUP;
    }

    public void setISGROUP(int ISGROUP) {
        this.ISGROUP = ISGROUP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
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

    public String getTASK_NO() {
        return TASK_NO;
    }

    public void setTASK_NO(String TASK_NO) {
        this.TASK_NO = TASK_NO;
    }

    public int getTASK_TYPE() {
        return TASK_TYPE;
    }

    public void setTASK_TYPE(int TASK_TYPE) {
        this.TASK_TYPE = TASK_TYPE;
    }

    public double getTOTAL_SCORE() {
        return TOTAL_SCORE;
    }

    public void setTOTAL_SCORE(double TOTAL_SCORE) {
        this.TOTAL_SCORE = TOTAL_SCORE;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.AUDIT_STATE);
        dest.writeString(this.DATE_STR);
        dest.writeString(this.ID);
        dest.writeInt(this.ISGROUP);
        dest.writeString(this.NAME);
        dest.writeString(this.TASK_DESC);
        dest.writeString(this.TASK_NAME);
        dest.writeString(this.TASK_NO);
        dest.writeInt(this.TASK_TYPE);
        dest.writeDouble(this.TOTAL_SCORE);
        dest.writeInt(this.complete);
        dest.writeInt(this.total);
        dest.writeString(this.CREATE_TIME);
    }

    public BusinessWorkEntity() {
    }

    protected BusinessWorkEntity(Parcel in) {
        this.AUDIT_STATE = in.readInt();
        this.DATE_STR = in.readString();
        this.ID = in.readString();
        this.ISGROUP = in.readInt();
        this.NAME = in.readString();
        this.TASK_DESC = in.readString();
        this.TASK_NAME = in.readString();
        this.TASK_NO = in.readString();
        this.TASK_TYPE = in.readInt();
        this.TOTAL_SCORE = in.readDouble();
        this.complete = in.readInt();
        this.total = in.readInt();
        this.CREATE_TIME = in.readString();
    }

    public static final Parcelable.Creator<BusinessWorkEntity> CREATOR = new Parcelable.Creator<BusinessWorkEntity>() {
        @Override
        public BusinessWorkEntity createFromParcel(Parcel source) {
            return new BusinessWorkEntity(source);
        }

        @Override
        public BusinessWorkEntity[] newArray(int size) {
            return new BusinessWorkEntity[size];
        }
    };
}
