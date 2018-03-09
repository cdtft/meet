package com.cdut.enums;

/**
 * 角色枚举
 * Created by king on 2017/10/24.
 */
public enum RoleEnum {

    /**
     * 管理员角色
     */
    ADMIN("admin"),

    /**
     * 普通用户角色
     */
    NORMAL("normal");

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
