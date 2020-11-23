package com.liuwjg.service.impl;


import com.liuwjg.dao.SysUserRoleMapper;
import com.liuwjg.entity.SysUserRole;
import com.liuwjg.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> selectByUserId(Long userId) {
        return sysUserRoleMapper.selectByUserId(userId);
    }
}
