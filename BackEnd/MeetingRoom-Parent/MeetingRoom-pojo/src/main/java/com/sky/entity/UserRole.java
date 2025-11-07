package com.sky.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户-角色关联表实体类（复合主键）
 */
@Data
public class UserRole implements Serializable {
    private Long id;
    private Integer userId;
    private Long roleId;
}
