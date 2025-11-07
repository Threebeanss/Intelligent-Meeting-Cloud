package com.sky.Service.Impl;

import com.sky.Mapper.RoleMapper;
import com.sky.Service.RoleService;
import com.sky.entity.Role;
import com.sky.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<UserRole> getUserRolesByUserId(Integer userId) {
        return roleMapper.selectUserRolesByUserId(userId);
    }

    @Override
    @Transactional
    public void assignRoles(Integer userId, List<Long> roleIds) {
        // 1. 删除旧关联
        roleMapper.deleteUserRolesByUserId(userId);
        // 2. 插入新关联
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            roleMapper.insertUserRole(userRole);
        }
    }
}