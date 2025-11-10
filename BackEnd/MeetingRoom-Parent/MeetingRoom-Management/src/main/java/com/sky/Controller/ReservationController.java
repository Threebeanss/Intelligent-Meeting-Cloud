package com.sky.Controller;

import com.sky.Service.ReservationService;
import com.sky.dto.ReservationPageDto;
import com.sky.entity.Reservation;
import com.sky.result.PageResult;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    /**
     * 添加预约信息
     * @param reservation
     * @return
     */
    @PreAuthorize("hasAuthority('reservation:create')")
    @PostMapping
    public Result addReservation(@RequestBody Reservation reservation){
        log.info("添加预约：{}",reservation);
        return reservationService.addReservation(reservation)>0?
                Result.success():Result.error("添加预约失败");
    }

    /**
     * 分页查询预约信息
     * @param reservationPageDto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ROOM_ADMIN', 'ROLE_SYS_ADMIN')")
    @GetMapping("/page")
    public Result<PageResult> pageSelect(@RequestBody ReservationPageDto reservationPageDto){
        log.info("分页查询预约");
        return Result.success(reservationService.pageSelect(reservationPageDto));
    }
    /**
     * 修改预约信息
     * @param reservation
     * @return
     */
    @PreAuthorize("hasAuthority('reservation:update')")
    @PutMapping
    public Result update(@RequestBody Reservation reservation){
        log.info("修改预约信息:{}",reservation);
        return reservationService.update(reservation)>0?
                Result.success():Result.error("修改预约信息失败");
    }
    /**
     * 根据会议室id查询预约信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<List<Reservation>> getByRoomId(@PathVariable Integer id){
        log.info("根据会议室id查询预约信息:{}",id);
        return Result.success(reservationService.getByRoomId(id));
    }
    /**
     * 查询当前用户预约信息
     * @return
     */
    @PreAuthorize("hasAuthority('reservation:my')")
    @GetMapping("/my")
    public Result<List<Reservation>> getMyReservation(){
        log.info("查询当前用户预约信息");
        return Result.success(reservationService.getMyReservation());
    }

    /**
     * 取消预约
     * @param id
     * @return
     */
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable Integer id){
        log.info("取消预约:{}",id);
        return reservationService.cancel(id)>0?
                Result.success():Result.error("取消预约失败");
    }
}
