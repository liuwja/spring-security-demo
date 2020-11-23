package com.liuwjg.security;

import com.liuwjg.entity.SysRole;
import com.liuwjg.entity.SysUser;
import com.liuwjg.entity.SysUserRole;
import com.liuwjg.service.ISysRoleService;
import com.liuwjg.service.ISysUserRoleService;
import com.liuwjg.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.selectByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        //获取用户-角色对应关系列表
        List<SysUserRole> userRoles = userRoleService.selectByUserId(user.getId());
        userRoles.forEach(userRole -> {
            //循环处理用户-角色，给认证列表authorities中添加角色
            SysRole sysRole = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        });
        return new User(user.getName(), user.getPassword(), authorities);
    }
}
