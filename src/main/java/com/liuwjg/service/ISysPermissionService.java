package com.liuwjg.service;

import com.liuwjg.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService {
    List<SysPermission> listByRoleId(Long id);
}
