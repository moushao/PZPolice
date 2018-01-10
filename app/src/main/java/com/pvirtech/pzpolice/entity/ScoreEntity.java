package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/26.
 */

public class ScoreEntity implements Parcelable {

    private int position;

    /**
     * name : 系统管理员
     * role : 1
     * totalScore : 4.0
     * dept : 默认
     * userId : 1
     */

    private String name;
    private String role;
    private double totalScore;
    private String dept;
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ScoreEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeString(this.name);
        dest.writeString(this.role);
        dest.writeDouble(this.totalScore);
        dest.writeString(this.dept);
        dest.writeString(this.userId);
    }

    protected ScoreEntity(Parcel in) {
        this.position = in.readInt();
        this.name = in.readString();
        this.role = in.readString();
        this.totalScore = in.readDouble();
        this.dept = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<ScoreEntity> CREATOR = new Parcelable.Creator<ScoreEntity>() {
        @Override
        public ScoreEntity createFromParcel(Parcel source) {
            return new ScoreEntity(source);
        }

        @Override
        public ScoreEntity[] newArray(int size) {
            return new ScoreEntity[size];
        }
    };
}
