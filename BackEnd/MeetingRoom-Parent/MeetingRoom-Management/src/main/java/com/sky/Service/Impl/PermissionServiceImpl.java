package com.sky.Service.Impl;

import com.sky.Mapper.PermissionMapper;
import com.sky.Service.PermissionService;
import com.sky.entity.Permission;
import com.sky.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<RolePermission> getRolePermissionsByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        return permissionMapper.selectByRoleIds(roleIds);
    }

    @Override
    public List<Permission> getByIds(List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return List.of();
        }
        return permissionMapper.selectByIds(permissionIds);
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }
}