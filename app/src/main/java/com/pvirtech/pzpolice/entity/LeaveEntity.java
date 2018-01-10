package com.pvirtech.pzpolice.entity;

/**
 * Created by youpengda on 2017/3/1.
 * 访问请假剩余天数实体类
 */

public class LeaveEntity {

    private int icon;
    private boolean isChecked = false;


    /**
     * day : 4.5
     * name : 公休假
     * type : 4001
     * tatal : 12.0
     */


    private String name;
    private String type;
    private double day = 0;
    private double total = 0;

    public LeaveEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public LeaveEntity(int icon, String name, String type) {
        this.icon = icon;
        this.name = name;
        this.type = type;

    }

    public LeaveEntity(int icon, String name, String type, double day) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.day = day;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
