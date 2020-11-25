package com.liuwjg.service.impl;

import com.liuwjg.dao.SysRoleMapper;
import com.liuwjg.entity.SysRole;
import com.liuwjg.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysRole selectById(Long id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public SysRole selectByName(String name) {
        return sysRoleMapper.selectByName(name);
    }

}
