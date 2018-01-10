package com.pvirtech.pzpolice.enumeration;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by pd on 2017/5/27.
 */

public enum WorkContent02Enum {
    /**
     * 新增档案
     */
    NEW_FILE(new DictionariesEntity("", "新增档案")),

    /**
     * 定期访谈
     */
    REGULAR_INTERVIEW(new DictionariesEntity("", "定期访谈")),

    /**
     * 定期回访
     */
    REGULAR_RETURN_VISIT(new DictionariesEntity("", "定期回访")),
    /**
     * 信息上报
     */
    INFORMATION_REPORT(new DictionariesEntity("", "信息上报")),


    /**
     * 家访谈话
     */
    HOME_VISIT_CONVERSATION(new DictionariesEntity("", "家访谈话")),


    /**
     * 其他
     */
    OTHER(new DictionariesEntity("", "其他"));


    private DictionariesEntity value;

    WorkContent02Enum(DictionariesEntity value) {
        this.value = value;
    }

    public DictionariesEntity getValue() {
        return value;
    }

    public void setValue(DictionariesEntity value) {
        this.value = value;
    }
}
