package com.liuwjg.security;

import com.liuwjg.entity.SysPermission;
import com.liuwjg.service.ISysPermissionService;
import com.liuwjg.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        User user = (User) authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            String roleName = auth.getAuthority();
            //根据角色名获取角色
            Long roleId = sysRoleService.selectByName(roleName).getId();
            //根据角色id获取权限列表
            List<SysPermission> permissionList = sysPermissionService.listByRoleId(roleId);
            for (SysPermission sysPermission : permissionList) {
                List permissions = sysPermission.getPermissions();
                //如果权限的url与目标url相等且权限列表包括目标权限
                if (targetUrl.equals(sysPermission.getUrl()) && permissions.contains(targetPermission)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
