package com.sky.repository;

import com.sky.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
    // 实验2会议室筛选：按容量筛选启用的会议室（匹配meeting_room表capacity/is_active字段）
    List<MeetingRoom> findByCapacityGreaterThanEqualAndIsActiveTrue(Integer capacity);

    // 实验2会议室筛选：按设备配置模糊查询（匹配meeting_room表equipment字段）
    List<MeetingRoom> findByEquipmentContainingAndIsActiveTrue(String equipment);

    // 实验2会议室筛选：按状态查询（匹配meeting_room表status字段）
    List<MeetingRoom> findByStatusAndIsActiveTrue(MeetingRoom.StatusEnum status);

    // 实验2预约冲突检测：查询指定时间段内空闲的会议室（基于表间关系排除冲突预约）
    @Query("SELECT mr FROM MeetingRoom mr WHERE mr.isActive = true AND mr.room_id NOT IN (" +
           "SELECT r.meetingRoom.room_id FROM Reservation r WHERE " +
           "r.startTime < :endTime AND r.endTime > :startTime AND r.status != '已取消')")
    List<MeetingRoom> findAvailableRooms(@Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
}