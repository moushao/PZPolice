package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/24.
 * 人员列表
 */

public class UserListEntity implements Parcelable {
    /**
     * NAME : 系统管理员
     * USERNAME : admin
     */

    private String NAME;
    private String USER_ID;


    private boolean isChecked = false;

    public UserListEntity(String NAME, String USER_ID) {
        this.NAME = NAME;
        this.USER_ID = USER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.NAME);
        dest.writeString(this.USER_ID);
    }

    protected UserListEntity(Parcel in) {
        this.NAME = in.readString();
        this.USER_ID = in.readString();
    }

    public static final Creator<UserListEntity> CREATOR = new Creator<UserListEntity>() {
        @Override
        public UserListEntity createFromParcel(Parcel source) {
            return new UserListEntity(source);
        }

        @Override
        public UserListEntity[] newArray(int size) {
            return new UserListEntity[size];
        }
    };
}
