package com.sky.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RoomVo implements Serializable {
    private Integer id;
    private String roomCode;// 会议室ID（主键）
    private Integer capacity; // 容纳人数
    private Integer status; // 实时状态（0-空闲，1-占用，2-维修）
    private Boolean isActive; // 启用状态（true-启用，false-禁用）
    private String location; // 会议室位置（如"3楼东区301室"）
    private String equipment;// 会议室设备
    private String remark; // 备注信息
}
