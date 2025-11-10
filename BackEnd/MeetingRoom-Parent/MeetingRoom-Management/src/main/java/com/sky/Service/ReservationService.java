package com.sky.Service;

import com.sky.dto.ReservationPageDto;
import com.sky.entity.Reservation;
import com.sky.result.PageResult;

import java.util.List;

public interface ReservationService {
    /**
     * 添加预约
     *
     * @param reservation
     * @return
     */
    int addReservation(Reservation reservation);

    /**
     * 分页查询预约信息
     *
     * @return
     */
    PageResult pageSelect(ReservationPageDto reservationPageDto);

    /**
     * 修改预约信息
     *
     * @param reservation
     * @return
     */
    int update(Reservation reservation);

    /**
     * 根据会议室id查询预约信息
     *
     * @param id
     * @return
     */

    List<Reservation> getByRoomId(Integer id);

    /**
     * 获取当前用户的预约信息
     * @return
     */
    List<Reservation> getMyReservation();
    /**
     * 取消预约
     * @param id
     * @return
     */

    int cancel(Integer id);
}
