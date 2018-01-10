package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pd on 2017/4/26.
 */

public class MyInitiatedEntity implements Parcelable {


    /**
     * AUDIT_STATE : 9
     * ID : 6316fbd9e6ff4f92ad4a1b18988fb9dc
     * class : worktime
     * end : 2017-05-03 17:00
     * name : 李四
     * start : 2017-05-03 09:00
     * type : 上班
     */

    private int AUDIT_STATE;
    private String ID;
    @SerializedName("class")
    private String classX;
    private String end;
    private String name;
    private String start;
    private String type;

    public int getAUDIT_STATE() {
        return AUDIT_STATE;
    }

    public void setAUDIT_STATE(int AUDIT_STATE) {
        this.AUDIT_STATE = AUDIT_STATE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyInitiatedEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.AUDIT_STATE);
        dest.writeString(this.ID);
        dest.writeString(this.classX);
        dest.writeString(this.end);
        dest.writeString(this.name);
        dest.writeString(this.start);
        dest.writeString(this.type);
    }

    protected MyInitiatedEntity(Parcel in) {
        this.AUDIT_STATE = in.readInt();
        this.ID = in.readString();
        this.classX = in.readString();
        this.end = in.readString();
        this.name = in.readString();
        this.start = in.readString();
        this.type = in.readString();
    }

    public static final Creator<MyInitiatedEntity> CREATOR = new Creator<MyInitiatedEntity>() {
        @Override
        public MyInitiatedEntity createFromParcel(Parcel source) {
            return new MyInitiatedEntity(source);
        }

        @Override
        public MyInitiatedEntity[] newArray(int size) {
            return new MyInitiatedEntity[size];
        }
    };
}
