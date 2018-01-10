package com.pvirtech.pzpolice.enumeration;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by pd on 2017/5/9.
 */

public enum TaskTypeEnum {
    /**
     * 日常任务
     */
    DAILIY(new DictionariesEntity("0", "日常任务")),

    /**
     * 交办任务
     */
    ASSIGNED(new DictionariesEntity("1", "交办任务"));


    private DictionariesEntity value;

    public DictionariesEntity getValue() {
        return value;
    }

    public void setValue(DictionariesEntity value) {
        this.value = value;
    }

    TaskTypeEnum(DictionariesEntity value) {
        this.value = value;
    }
}
