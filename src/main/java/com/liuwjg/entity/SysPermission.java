package com.liuwjg.entity;

import java.util.Arrays;
import java.util.List;

public class SysPermission {
    private Long id;
    private String url;
    private Long roleId;
    private String permission;
    private List permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List getPermissions() {
        return Arrays.asList(permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }
}
