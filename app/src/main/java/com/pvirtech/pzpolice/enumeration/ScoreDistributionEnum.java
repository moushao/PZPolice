package com.pvirtech.pzpolice.enumeration;

/**
 * Created by pd on 2017/5/5.
 * 分值分配的枚举
 */

public enum ScoreDistributionEnum {
    /**
     *
     */
    FIXED(0),

    /**
     *
     */
    AVERAGE(1),

    /**
     *
     */
    CUSTOM(2);

    private int value;

    ScoreDistributionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
