package com.sky.Service.Impl;

import com.sky.Service.PermissionService;
import com.sky.Service.RoleService;
import com.sky.context.BaseContext;
import com.sky.entity.*;
import com.sky.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String loginAccount) throws UsernameNotFoundException {
        // 1. 查询用户基本信息
        User user = userMapper.selectByLoginAccount(loginAccount);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 2. 将用户ID存入BaseContext（供后续业务使用）
        BaseContext.setCurrentId(user.getId());

        // 3. 查询用户角色（获取角色编码）
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream()
                .map(Role::getRoleCode) // 使用getRoleCode()获取角色编码
                .collect(Collectors.toList());

        // 4. 查询用户权限（获取权限编码）
        List<UserRole> userRoles = roleService.getUserRolesByUserId(user.getId());
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        List<RolePermission> rolePermissions = permissionService.getRolePermissionsByRoleIds(roleIds);
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        List<Permission> permissions = permissionService.getByIds(permissionIds);
        List<String> permissionCodes = permissions.stream()
                .map(Permission::getPermissionCode) // 使用getPermissionCode()获取权限编码
                .collect(Collectors.toList());

        // 5. 封装权限集合（角色+权限）
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 添加角色权限（Spring Security默认角色需以ROLE_为前缀，若数据库已包含则直接添加）
        roleCodes.forEach(roleCode -> authorities.add(new SimpleGrantedAuthority(roleCode)));
        // 添加功能权限
        permissionCodes.forEach(permCode -> authorities.add(new SimpleGrantedAuthority(permCode)));

        // 6. 返回自定义UserDetails
        return new SecurityUser(user, authorities);
    }
}