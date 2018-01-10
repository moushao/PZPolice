package com.pvirtech.pzpolice.enumeration;

/**
 * Created by youpengda on 2017/2/14.
 * 这一天的状况
 */

public enum DayStatus {
    /**
     * 空
     */
    EMPTY(1),
    /**
     * 正常
     */
    NORMAL(2),
    /**
     * 今天
     */
    TODAY(3),
    /**
     * 已选择
     */
    SELECTED(4),
    /**
     * 周几
     */
    WEEK(5);
    private int value;

    DayStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
