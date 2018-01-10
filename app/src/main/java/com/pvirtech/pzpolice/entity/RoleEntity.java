package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/18.
 */

public class RoleEntity {
    private String roleId;
    private String roleName;

    public RoleEntity(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
