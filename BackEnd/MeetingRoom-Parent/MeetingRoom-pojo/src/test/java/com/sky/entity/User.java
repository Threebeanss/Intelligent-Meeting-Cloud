package com.sky.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user") // 关联实验2设计的user表
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 适配实验2表自增主键
    private Integer user_id; // 对应实验2表user_id字段（主键）

    @Column(name = "login_account", nullable = false, unique = true)
    private String loginAccount; // 对应实验2表login_account字段（非空、唯一）

    @Column(name = "password", nullable = false)
    private String password; // 对应实验2表password字段（非空，加密存储）

    @Column(name = "name", nullable = false)
    private String name; // 对应实验2表name字段（非空）

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role; // 对应实验2表role字段（枚举类型）

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 对应实验2表is_active字段（是否启用）

    @Column(name = "create_by")
    private String createBy; // 对应实验2表create_by字段（创建人）

    // 实验2表间关系：User与Reservation为一对多（一个用户可多笔预约）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    // 枚举定义：与实验2表role字段枚举值完全一致
    public enum RoleEnum {
        普通用户, 系统管理员, 会议室管理员
    }
}