package com.liuwjg.dao;

import com.liuwjg.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper {

    @Select("select * from sys_role where id = #{id}")
    SysRole selectById(Long id);

    @Select("select * from sys_role where name = #{name}")
    SysRole selectByName(String name);
}
