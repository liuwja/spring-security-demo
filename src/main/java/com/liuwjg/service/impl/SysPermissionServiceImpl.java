package com.liuwjg.service.impl;

import com.liuwjg.dao.SysPermissionMapper;
import com.liuwjg.entity.SysPermission;
import com.liuwjg.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> listByRoleId(Long id) {
        return sysPermissionMapper.listByRoleId(id);
    }
}
