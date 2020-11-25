package com.liuwjg.dao;

import com.liuwjg.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    @Select("select * from sys_permission where role_id = #{id}")
    List<SysPermission> listByRoleId(Long id);
}
