package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/12.
 */

public class GroupcountEntity {

    /**
     * COMPLETE : 0
     * GROUP_ID : 2002
     * GROUP_NAME : 内勤
     * TOTAL : 2
     */

    private int COMPLETE;
    private String GROUP_ID;
    private String GROUP_NAME;
    private int TOTAL;

    public int getCOMPLETE() {
        return COMPLETE;
    }

    public void setCOMPLETE(int COMPLETE) {
        this.COMPLETE = COMPLETE;
    }

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }
}
