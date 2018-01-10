package com.pvirtech.pzpolice.enumeration;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by pd on 2017/5/27.
 */

public enum WorkContentEnum {
    /**
     * 安全检查
     */
    SECURITY_CHECK(new DictionariesEntity("", "安全检查")),

    /**
     * 防范宣传
     */
    PROPAGANDA_AGAINST(new DictionariesEntity("", "防范宣传")),

    /**
     * 其他
     */
    OTHER(new DictionariesEntity("", "其他"));
    private DictionariesEntity value;

    WorkContentEnum(DictionariesEntity value) {
        this.value = value;
    }

    public DictionariesEntity getValue() {
        return value;
    }

    public void setValue(DictionariesEntity value) {
        this.value = value;
    }
}
