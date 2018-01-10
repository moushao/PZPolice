package com.pvirtech.pzpolice.enumeration;

/**
 * Created by youpengda on 2017/2/14.
 * 这一天的工作状态
 */

public enum WorkStatus {
    /**
     * 空
     */
    EMPTY(0),
    /**
     * 绿
     */
    MORMAL(1),
    /**
     * 黄
     */
    LEAVE(2),
    /**
     * 红
     */
    TARDINESS(3);


    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    WorkStatus(int value) {

        this.value = value;
    }
}
