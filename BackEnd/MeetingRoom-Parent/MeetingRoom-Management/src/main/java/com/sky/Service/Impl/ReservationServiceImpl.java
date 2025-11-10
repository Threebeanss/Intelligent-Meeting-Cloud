package com.sky.Service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.Mapper.ReservationMapper;
import com.sky.Service.ReservationService;
import com.sky.Service.RoomService;
import com.sky.Service.UserService;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.ReservationPageDto;
import com.sky.entity.MeetingRoom;
import com.sky.entity.Reservation;
import com.sky.entity.User;
import com.sky.exception.EndTimeBeforeStartTime;
import com.sky.exception.ReservationIsConfirmed;
import com.sky.exception.RoomIsAccupied;
import com.sky.result.PageResult;
import com.sky.vo.ReservationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private RoomService roomService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    /**
     * 添加预约
     * @param reservation
     * @return
     */
    @Transactional
    @Override
    public int addReservation(Reservation reservation) {
        log.info("添加预约：{}",reservation);
        List<Reservation> reservations = reservationMapper.selectByRoomId(reservation.getRoomId());
        MeetingRoom room = roomService.getById(reservation.getRoomId());
        for (Reservation res : reservations) {
            // 判断时间段是否冲突，并预留10分钟间隙
            if(res.getStartTime().isBefore(reservation.getEndTime().plusMinutes(10))
                    && res.getEndTime().isAfter(reservation.getStartTime().minusMinutes(10))){
                log.info("时间段冲突或间隔不足10分钟");
                throw new RoomIsAccupied(MessageConstant.ROOM_IS_ACCUPIED);
            }
        }
        if(room.getStatus()== StatusConstant.OCCUPIED){
            log.info("会议室已占用");
            throw new RoomIsAccupied(MessageConstant.ROOM_IS_ACCUPIED);
        }
        if(reservation.getEndTime().isBefore(reservation.getStartTime())){
            log.info("结束时间小于开始时间");
            throw new EndTimeBeforeStartTime(MessageConstant.END_TIME_IS_BEFORE_START_TIME);
        }
        //默认待审核
        reservation.setStatus(StatusConstant.PENDING);
        // 使用乐观锁更新会议室状态
        int updateResult = roomService.updateStatusWithVersion(
                room.getId(),
                StatusConstant.OCCUPIED,
                room.getVersion()
        );

        if(updateResult == 0) {
            // 版本号不匹配，说明已被其他用户修改
            throw new RuntimeException("会议室状态已被其他用户修改，请重新预约");
        }

        int result = reservationMapper.insertReservation(reservation);

        // 如果预约添加成功，则发送邮件提醒
        if (result > 0) {
            sendReservationSuccessEmail(reservation);
        }

        return result;
    }
    /**
     * 发送预约成功邮件
     * @param reservation
     */
    private void sendReservationSuccessEmail(Reservation reservation) {
        try {

        User user = userService.getById(reservation.getUserId()); // 需要注入UserService或UserMapper

        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("会议室预约成功通知");
            message.setText(
                "您好，\n\n" +
                "您的会议室预约已成功提交：\n" +
                "会议主题: " + reservation.getMeetingTopic() + "\n" +
                "会议室ID: " + reservation.getRoomId() + "\n" +
                "开始时间: " + reservation.getStartTime() + "\n" +
                "结束时间: " + reservation.getEndTime() + "\n" +
                "当前状态: 待审核\n\n" +
                "请耐心等待管理员审核。\n\n" +
                "智能会议室系统"
            );

            mailSender.send(message);
            log.info("预约成功邮件已发送至: {}", user.getEmail());
        }
        } catch (Exception e) {
            log.error("发送预约成功邮件失败，预约ID: {}", reservation.getId(), e);
        }
    }

    /**
     * 分页查询预约信息
     * @param reservationPageDto
     * @return
     */
    @Override
    public PageResult pageSelect(ReservationPageDto reservationPageDto) {
        log.info("分页查询预约信息");
        PageHelper.startPage(reservationPageDto.getPage(),reservationPageDto.getPageSize());
        Page<ReservationVo> page = reservationMapper.pageSelect(reservationPageDto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改预约信息
     * @param reservation
     * @return
     */
    @Override
    public int update(Reservation reservation) {
        log.info("修改预约信息");
        return reservationMapper.update(reservation);
    }

    /**
     * 根据会议室id查询预约信息
     * @param id
     * @return
     */
    @Override
    public List<Reservation> getByRoomId(Integer id) {
        log.info("根据会议室id查询预约信息");
        return reservationMapper.selectByRoomId(id);
    }

    /**
     * 获取当前用户预约信息
     * @return
     */
    @Override
    public List<Reservation> getMyReservation() {
        log.info("获取当前用户预约信息");
        Integer userId = BaseContext.getCurrentId();
        return  reservationMapper.getByUserId(userId);
    }

    /**
     * 取消预约
     * @param id
     * @return
     */
    @Override
    public int cancel(Integer id) {
        log.info("取消预约");
        Reservation reservation = reservationMapper.selectById(id);
        if(reservation==null){
            log.info("预约不存在");
            throw new RuntimeException("预约不存在");
        }
        if(reservation.getStatus() == StatusConstant.CONFIRMED){
            log.info("预约已确认，不能取消");
            throw new ReservationIsConfirmed("预约已确认，不能取消");
        }
        reservation.setStatus(StatusConstant.CANCELED);
        return reservationMapper.update(reservation);

    }
}
