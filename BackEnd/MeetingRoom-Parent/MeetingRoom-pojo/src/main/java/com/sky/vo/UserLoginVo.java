package com.sky.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserLoginVo implements Serializable {
    private Integer id;//用户ID（主键）
    private String username;//用户姓名
    private String loginAccount;//登录账号
    private String token;//登录令牌
}
