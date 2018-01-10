package com.pvirtech.pzpolice.entity;

/**
 * Created by youpengda on 2017/2/14.
 * 查看考勤情况的某一天的实体类
 */

public class WorkDay {

    /**
     * 日期的文本
     */
    private String text;
    /**
     * 1、空白天，2、普通天，3、今天，4、已选择
     */
    private int dayStatus;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(int dayStatus) {
        this.dayStatus = dayStatus;
    }

    /**
     * date : 2017-04-20
     * dateId : 476
     * state : -1
     * totalnum : 0
     * type : 2
     */

    private String date;
    private int dateId;
    private int state;
    private int totalnum;
    private int type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
