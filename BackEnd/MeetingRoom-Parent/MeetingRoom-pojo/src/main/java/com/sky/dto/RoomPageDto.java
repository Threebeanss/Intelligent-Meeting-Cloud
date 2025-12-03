package com.sky.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomPageDto implements Serializable {
    private int page;
    private int pageSize;
    private String roomCode;
    private Integer capacity; // 容纳人数
    private Integer status; // 实时状态（0-空闲，1-占用，2-维修）
    private Boolean isActive; // 启用状态（true-启用，false-禁用）
    private String location;
    private String equipment;
}
