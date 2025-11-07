package com.sky.Service;

import com.sky.entity.Role;
import com.sky.entity.UserRole;

import java.util.List;

public interface RoleService {
    List<Role> getRolesByUserId(Integer userId);
    List<UserRole> getUserRolesByUserId(Integer userId);
    void assignRoles(Integer userId, List<Long> roleIds);
}
