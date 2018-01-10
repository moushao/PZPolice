package com.pvirtech.pzpolice.enumeration;

/**
 * Created by pd on 2017/5/14.
 */

public enum FileEnum {
    /**
     * 等待中
     */
    WAITING(1),

    /**
     * 正在上传
     */
    DOING(2),

    /**
     * 已上传
     */
    DOWN(3),
    /**
     * 出错
     */
    ERROR(4),
    /**
     * 文件不存在
     */
    NOTEXISTS(5);
    private int value;

    FileEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
