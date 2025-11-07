package com.sky.Mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.ReservationPageDto;
import com.sky.entity.Reservation;
import com.sky.enumeration.OperationType;
import com.sky.vo.ReservationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationMapper {
    /**
     * 添加预约信息
     * @param reservation
     * @return
     */
    @AutoFill(value = OperationType.INSERT)
    int insertReservation(Reservation reservation);

    /**
     * 分页查询预约信息
     * @param reservationPageDto
     * @return
     */
    Page<ReservationVo> pageSelect(ReservationPageDto reservationPageDto);

    /**
     * 修改预约信息
     * @param reservation
     * @return
     */
    @AutoFill(value = OperationType.UPDATE)
    int update(Reservation reservation);
    /**
     * 查询指定时间范围内开始的预约
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return 预约列表
     */
    @Select("SELECT * FROM reservation WHERE start_time > #{startTime} AND start_time <= #{endTime} AND status = 1")
    List<Reservation> getReservationsStartingBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据会议室id查询预约信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM reservation WHERE room_id = #{id}")
    List<Reservation> selectByRoomId(Integer id);

    /**
     * 根据用户id查询预约信息
     * @param userId
     * @return
     */
    @Select("SELECT * FROM reservation WHERE user_id = #{userId}")
    List<Reservation> getByUserId(Integer userId);
}
