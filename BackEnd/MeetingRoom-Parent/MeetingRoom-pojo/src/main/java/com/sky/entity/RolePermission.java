package com.sky.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色-权限关联表实体类（复合主键）
 */
@Data
public class RolePermission implements Serializable {
    private Long id;
    private Long roleId;
    private Long permissionId;
}