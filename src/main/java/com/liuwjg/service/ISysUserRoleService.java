package com.liuwjg.service;

import com.liuwjg.entity.SysUserRole;

import java.util.List;

public interface ISysUserRoleService {
    List<SysUserRole> selectByUserId(Long userId);
}

