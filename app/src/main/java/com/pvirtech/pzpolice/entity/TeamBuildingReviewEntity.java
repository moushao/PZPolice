package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/4/7.
 */

public class TeamBuildingReviewEntity {

    /**
     * ID : 2
     * day : 2.0
     * dept : 1队
     * end : 2017-03-31 17:01
     * name : 系统管理员
     * start : 2017-03-30 09:00
     * type : 公1
     */

    private int ID;
    private double day;
    private String dept;
    private String end;
    private String name;
    private String start;
    private String type;
    private String no;
    private String state;
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
