package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/29.
 * 我的请假记录实体类
 */

public class LeaveRecordEntity implements Parcelable {
    private int icon;
    private String name;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ID : 1
     * day : 1.0
     * end : 2017-03-30 17:00
     * reason : 请选择(必填)
     * start : 2017-03-30 09:00
     * state : 0
     * type : 4005
     */

    private int ID;
    private double day;
    private String end;
    private String reason;
    private String start;
    private int state;
    private String type;
    private String create_time;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public LeaveRecordEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.name);
        dest.writeInt(this.ID);
        dest.writeDouble(this.day);
        dest.writeString(this.end);
        dest.writeString(this.reason);
        dest.writeString(this.start);
        dest.writeInt(this.state);
        dest.writeString(this.type);
        dest.writeString(this.create_time);
    }

    protected LeaveRecordEntity(Parcel in) {
        this.icon = in.readInt();
        this.name = in.readString();
        this.ID = in.readInt();
        this.day = in.readDouble();
        this.end = in.readString();
        this.reason = in.readString();
        this.start = in.readString();
        this.state = in.readInt();
        this.type = in.readString();
        this.create_time = in.readString();
    }

    public static final Creator<LeaveRecordEntity> CREATOR = new Creator<LeaveRecordEntity>() {
        @Override
        public LeaveRecordEntity createFromParcel(Parcel source) {
            return new LeaveRecordEntity(source);
        }

        @Override
        public LeaveRecordEntity[] newArray(int size) {
            return new LeaveRecordEntity[size];
        }
    };
}
