package com.pvirtech.pzpolice.enumeration;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by pd on 2017/5/24.
 * 处置结果
 */

public enum ManagementEnum {
    /**
     * 现场处置完毕
     */
    SCENE(new DictionariesEntity("", "现场处置完毕")),
    /**
     * 受案处理
     */
    BY_THE_CASE(new DictionariesEntity("", "受案处理")),
    /**
     * 移交处理
     */
    TRANSFER(new DictionariesEntity("", "移交处理"));

    private DictionariesEntity value;

    ManagementEnum(DictionariesEntity value) {
        this.value = value;
    }

    public DictionariesEntity getValue() {
        return value;
    }

    public void setValue(DictionariesEntity value) {
        this.value = value;
    }
}
