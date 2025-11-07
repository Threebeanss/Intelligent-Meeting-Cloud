package com.sky.Mapper;

import com.sky.entity.Role;
import com.sky.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    // 根据用户ID查询角色
    List<Role> selectByUserId(@Param("userId") Integer userId);
    // 根据用户ID查询用户-角色关联
    List<UserRole> selectUserRolesByUserId(@Param("userId") Integer userId);
    // 批量删除用户-角色关联
    void deleteUserRolesByUserId(@Param("userId") Integer userId);
    // 插入用户-角色关联
    void insertUserRole(UserRole userRole);
}
