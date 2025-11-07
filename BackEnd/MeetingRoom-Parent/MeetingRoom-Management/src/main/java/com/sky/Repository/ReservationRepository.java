package com.sky.repository;

import com.sky.entity.MeetingRoom;
import com.sky.entity.Reservation;
import com.sky.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // 实验2个人预约历史：按用户查询预约记录（匹配reservation表user_id外键，按时间倒序）
    List<Reservation> findByUserIdOrderByStartTimeDesc(Integer userId);

    // 实验2预约审批列表：按状态查询（匹配reservation表status字段，按时间倒序）
    List<Reservation> findByStatusOrderByStartTimeDesc(Reservation.StatusEnum status);

    // 实验2预约冲突检测：按会议室和时间段查询冲突预约（匹配reservation表room_id/start_time/end_time字段）
    List<Reservation> findByMeetingRoomAndStartTimeLessThanAndEndTimeGreaterThan(
        MeetingRoom meetingRoom, LocalDateTime endTime, LocalDateTime startTime);

    // 实验2管理员审核记录：按审核管理员查询（匹配reservation表admin_id外键）
    List<Reservation> findByRoomAdmin_AdminIdOrderByStartTimeDesc(Integer adminId);
}