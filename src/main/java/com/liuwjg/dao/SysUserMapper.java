package com.liuwjg.dao;

import com.liuwjg.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper {
    @Select("select * from sys_user where id = #{id}")
    SysUser selectById(Long id);

    @Select("select * from sys_user where name = #{name}")
    SysUser selectByName(String name);
}
