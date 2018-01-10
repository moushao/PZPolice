package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/6.
 */

public class TeamBuildingEntity implements Parcelable {

    /**
     * AUDIT_STATE : 9
     * ID : 6
     * class : holiday
     * end : 2017-03-31
     * name : 系统管理员
     * start : 2017-03-31
     * type : 病假
     */

    private String ID;
    @SerializedName("class")
    private String classX;
    private String end;
    private String name;
    private String start;
    private String type;
    private int AUDIT_STATE;


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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.classX);
        dest.writeString(this.end);
        dest.writeString(this.name);
        dest.writeString(this.start);
        dest.writeString(this.type);
        dest.writeInt(this.AUDIT_STATE);
    }

    public TeamBuildingEntity() {
    }

    protected TeamBuildingEntity(Parcel in) {
        this.ID = in.readString();
        this.classX = in.readString();
        this.end = in.readString();
        this.name = in.readString();
        this.start = in.readString();
        this.type = in.readString();
        this.AUDIT_STATE = in.readInt();
    }

    public static final Parcelable.Creator<TeamBuildingEntity> CREATOR = new Parcelable.Creator<TeamBuildingEntity>() {
        @Override
        public TeamBuildingEntity createFromParcel(Parcel source) {
            return new TeamBuildingEntity(source);
        }

        @Override
        public TeamBuildingEntity[] newArray(int size) {
            return new TeamBuildingEntity[size];
        }
    };
}
