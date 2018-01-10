package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/4/26.
 */

public class ToExamineEntity {
    private String notice;
    private int state;

    public ToExamineEntity(String notice, int state) {
        this.notice = notice;
        this.state = state;
    }

    public String getNotice() {
        return notice;
    }

    public int getState() {
        return state;
    }
}
