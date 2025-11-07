package com.sky.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表实体类
 */
@Data
public class Permission implements Serializable {
    private Long id;
    private String permissionCode; // 如reservation:create
    private String permissionName;
    private String url;
    private String method;
    private Long parentId;
}
