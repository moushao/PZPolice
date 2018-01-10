package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class WorkLogEntity implements Parcelable {


    /**
     * id: 12
     * CONTENT : eew
     * CREATE_TIME : 2017-05-15 15:58:16
     * RESOURCE_ID : 8737.jpg,c86e963cd4999615b2376b88fa953809.jpg
     * UPDATE_TIME : 2017-05-15 15:58:16
     * USER_NO : 3
     */
    private String ID;
    private String CONTENT;
    private String CREATE_TIME;
    private String RESOURCE_ID;
    private String UPDATE_TIME;
    private String USER_NO;

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getRESOURCE_ID() {
        return RESOURCE_ID;
    }

    public void setRESOURCE_ID(String RESOURCE_ID) {
        this.RESOURCE_ID = RESOURCE_ID;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getUSER_NO() {
        return USER_NO;
    }

    public void setUSER_NO(String USER_NO) {
        this.USER_NO = USER_NO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.CONTENT);
        dest.writeString(this.CREATE_TIME);
        dest.writeString(this.RESOURCE_ID);
        dest.writeString(this.UPDATE_TIME);
        dest.writeString(this.USER_NO);
    }

    public WorkLogEntity() {
    }

    protected WorkLogEntity(Parcel in) {
        this.ID = in.readString();
        this.CONTENT = in.readString();
        this.CREATE_TIME = in.readString();
        this.RESOURCE_ID = in.readString();
        this.UPDATE_TIME = in.readString();
        this.USER_NO = in.readString();
    }

    public static final Parcelable.Creator<WorkLogEntity> CREATOR = new Parcelable.Creator<WorkLogEntity>() {
        @Override
        public WorkLogEntity createFromParcel(Parcel source) {
            return new WorkLogEntity(source);
        }

        @Override
        public WorkLogEntity[] newArray(int size) {
            return new WorkLogEntity[size];
        }
    };
}
