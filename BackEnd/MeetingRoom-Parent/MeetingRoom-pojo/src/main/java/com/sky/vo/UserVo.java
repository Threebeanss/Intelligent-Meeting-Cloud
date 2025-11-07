package com.sky.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserVo implements Serializable {
    private Integer id;
    private String username; // 用户姓名
    private Integer gender; // 性别（1-男，2-女，0-未知）
    private String phone;// 手机号
    private String email;// 邮箱
    private Integer isActive;// 是否激活（0-未激活，1-已激活）
    private LocalDateTime createTime; // 创建时间
    private String createBy; // 创建人（登录账号）
    private String remark; // 备注信息
}
