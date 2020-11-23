package com.liuwjg.dao;

import com.liuwjg.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    @Select("select * from sys_user_role where user_id = #{userId}")
    List<SysUserRole> selectByUserId(Long userId);
}
