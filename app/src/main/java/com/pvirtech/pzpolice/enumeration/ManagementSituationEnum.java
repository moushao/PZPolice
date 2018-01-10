package com.pvirtech.pzpolice.enumeration;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by pd on 2017/5/24.
 */

public enum ManagementSituationEnum {
    /**
     * 破案
     */
    CASE(new DictionariesEntity("", "破案")),

    /**
     * 起诉
     */
    PROSECUTION(new DictionariesEntity("", "起诉")),
    /**
     * 拘留
     */
    DETENTION(new DictionariesEntity("", "拘留")),

    /**
     * 逮捕
     */
    ARREST(new DictionariesEntity("", "逮捕"));

    private DictionariesEntity value;

    ManagementSituationEnum(DictionariesEntity value) {
        this.value = value;
    }

    public DictionariesEntity getValue() {
        return value;
    }

    public void setValue(DictionariesEntity value) {
        this.value = value;
    }
}
