package com.pvirtech.pzpolice.enumeration;

/**
 * 是做任务还是自主申报
 */

public enum CaseComeEnum {
    /**
     * 自主申报
     */
    AUTONOMY(1),
    /**
     * 做任务
     */
    TASK(2);
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    CaseComeEnum(int value) {

        this.value = value;
    }
}
