package com.liuwjg.service;

import com.liuwjg.entity.SysUser;

public interface ISysUserService {
    SysUser selectById(Long id);
    SysUser selectByName(String name);
}
