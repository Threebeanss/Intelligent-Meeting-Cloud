package com.sky.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "meeting_room") // 关联实验2设计的meeting_room表
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer room_id; // 对应实验2表room_id字段（主键）

    @Column(name = "room_code", nullable = false)
    private String roomCode; // 对应实验2表room_code字段（会议室编号，如301）

    @Column(name = "capacity", nullable = false)
    private Integer capacity; // 对应实验2表capacity字段（容纳人数，非空）

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status; // 对应实验2表status字段（实时状态）

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 对应实验2表is_active字段（是否启用，默认true）

    @Column(name = "location")
    private String location; // 对应实验2表location字段（位置，如3楼东区）

    @Column(name = "equipment")
    private String equipment; // 对应实验2表equipment字段（设备配置）

    @OneToMany(mappedBy = "meetingRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public enum StatusEnum {
        空闲, 占用
    }
}