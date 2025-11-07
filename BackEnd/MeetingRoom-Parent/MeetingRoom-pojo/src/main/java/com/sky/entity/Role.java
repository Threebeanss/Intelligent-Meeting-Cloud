package com.sky.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表实体类
 */
@Data
public class Role implements Serializable {
    private Long id;
    private String roleCode; // 如ROLE_USER
    private String roleName;
    private Integer status;
}