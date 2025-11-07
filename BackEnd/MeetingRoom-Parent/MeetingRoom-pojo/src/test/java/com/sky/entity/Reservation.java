package com.sky.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservation") // 关联实验2设计的reservation表
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservation_id; // 对应实验2表reservation_id字段（主键）

    @Column(name = "reservation_no", unique = true)
    private String reservationNo; // 对应实验2表reservation_no字段（预订单号，唯一）

    // 实验2表间关系：Reservation与User为多对一（关联预约用户）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 外键关联User表user_id
    private User user;

    // 实验2表间关系：Reservation与MeetingRoom为多对一（关联预约会议室）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false) // 外键关联MeetingRoom表room_id
    private MeetingRoom meetingRoom;

    // 实验2表间关系：Reservation与RoomAdmin为多对一（关联审核管理员）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id") // 外键关联RoomAdmin表admin_id（可为空，未审核时无值）
    private RoomAdmin roomAdmin;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 对应实验2表start_time字段（预约开始时间，非空）

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime; // 对应实验2表end_time字段（预约结束时间，非空）

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status; // 对应实验2表status字段（预约状态）

    // 枚举定义：与实验2表status字段枚举值一致
    public enum StatusEnum {
        待审核, 已确认, 已取消
    }
}