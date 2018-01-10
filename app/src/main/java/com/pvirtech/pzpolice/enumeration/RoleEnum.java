package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.entity.RoleEntity;

/**
 * Created by Administrator on 2017/4/17.
 */

public enum RoleEnum {

    /**
     * 系统管理员
     */
    SYSTEM(new RoleEntity("1", "系统管理员")),

    /**
     * 所长/教导员
     */
    LEADER(new RoleEntity("2", "所长/教导员")),

    /**
     * 副所长
     */
    VICE_LEADER(new RoleEntity("3", "副所长")),

    /**
     * 警长
     */
    SHERIFF(new RoleEntity("4", "警长")),

    /**
     * 警员
     */
    POLICE(new RoleEntity("5", "警员")),

    /**
     * 协警
     */
    AUXILIARY(new RoleEntity("6", "协警"));

    private RoleEntity value;

    RoleEnum(RoleEntity value) {
        this.value = value;
    }

    public RoleEntity getValue() {
        return value;
    }

    public void setValue(RoleEntity value) {
        this.value = value;
    }
}
