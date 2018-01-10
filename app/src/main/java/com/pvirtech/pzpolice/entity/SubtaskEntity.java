package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pd on 2017/4/24.
 * 子任务实体类
 */

public class SubtaskEntity implements Parcelable {

    /**
     * CASE_NO : 20170509104858
     * CASE_START_TIME : 2017-05-09 10:48
     * CASE_TYPE : 9
     * NAME : 交通案件
     * id : 40
     */

    private String CASE_NO;
    private String CASE_START_TIME;
    private int CASE_TYPE;
    private String NAME;
    private String id;

    public String getCASE_NO() {
        return CASE_NO;
    }

    public void setCASE_NO(String CASE_NO) {
        this.CASE_NO = CASE_NO;
    }

    public String getCASE_START_TIME() {
        return CASE_START_TIME;
    }

    public void setCASE_START_TIME(String CASE_START_TIME) {
        this.CASE_START_TIME = CASE_START_TIME;
    }

    public int getCASE_TYPE() {
        return CASE_TYPE;
    }

    public void setCASE_TYPE(int CASE_TYPE) {
        this.CASE_TYPE = CASE_TYPE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubtaskEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CASE_NO);
        dest.writeString(this.CASE_START_TIME);
        dest.writeInt(this.CASE_TYPE);
        dest.writeString(this.NAME);
        dest.writeString(this.id);
    }

    protected SubtaskEntity(Parcel in) {
        this.CASE_NO = in.readString();
        this.CASE_START_TIME = in.readString();
        this.CASE_TYPE = in.readInt();
        this.NAME = in.readString();
        this.id = in.readString();
    }

    public static final Creator<SubtaskEntity> CREATOR = new Creator<SubtaskEntity>() {
        @Override
        public SubtaskEntity createFromParcel(Parcel source) {
            return new SubtaskEntity(source);
        }

        @Override
        public SubtaskEntity[] newArray(int size) {
            return new SubtaskEntity[size];
        }
    };
}
