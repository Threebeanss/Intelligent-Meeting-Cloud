package com.sky.Mapper;

import com.sky.entity.Permission;
import com.sky.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    // 根据角色ID列表查询角色-权限关联
    List<RolePermission> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
    // 根据权限ID列表查询权限
    List<Permission> selectByIds(@Param("permissionIds") List<Long> permissionIds);
    // 根据角色ID查询权限
    List<Permission> selectByRoleId(@Param("roleId") Long roleId);
}