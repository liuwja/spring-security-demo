package com.liuwjg.service.impl;

import com.liuwjg.dao.SysUserMapper;
import com.liuwjg.entity.SysUser;
import com.liuwjg.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser selectByName(String name) {
        return sysUserMapper.selectByName(name);
    }
}
