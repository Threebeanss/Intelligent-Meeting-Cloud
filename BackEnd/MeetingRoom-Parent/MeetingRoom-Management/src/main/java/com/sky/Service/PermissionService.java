package com.sky.Service;

import com.sky.entity.Permission;
import com.sky.entity.RolePermission;

import java.util.List;

public interface PermissionService {
    List<RolePermission> getRolePermissionsByRoleIds(List<Long> roleIds);
    List<Permission> getByIds(List<Long> permissionIds);
    List<Permission> getPermissionsByRoleId(Long roleId);
}
