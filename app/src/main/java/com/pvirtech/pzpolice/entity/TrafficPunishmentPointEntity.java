package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class TrafficPunishmentPointEntity {
    private String name;
    private double point;
    private String user_ID;
    private boolean isChecked = false;

    public TrafficPunishmentPointEntity(String name) {
        this.name = name;
    }

    public TrafficPunishmentPointEntity(String name, double point) {
        this.name = name;
        this.point = point;
    }

    public TrafficPunishmentPointEntity(String name, String user_ID) {
        this.name = name;
        this.user_ID = user_ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
