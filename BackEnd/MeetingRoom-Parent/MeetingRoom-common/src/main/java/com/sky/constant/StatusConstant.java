package com.sky.constant;

/**
 * 状态常量，启用或者禁用
 */
public class StatusConstant {

    //启用
    public static final Integer ENABLE = 1;

    //禁用
    public static final Integer DISABLE = 0;

    //空闲
    public static final Integer AVAILABLE = 0;

    //占用
    public static final Integer OCCUPIED = 1;

    //维修
    public static final Integer REPAIR = 2;

    public static final Integer PENDING = 0;       // 待审核
    public static final Integer CONFIRMED = 1;     // 已确认
    public static final Integer CANCELED = 2;      // 已取消
    public static final Integer REJECTED = 3;      // 已拒绝
    public static final Integer COMPLETED1 = 4;     // 预约已完成
    public static final Integer TIMEOUT = 5;       // 已超时
    //待处理
    public static final Integer WAITING = 0;
    //处理中
    public static final Integer PROCESSING = 1;
    //已修复
    public static final Integer COMPLETED2 = 2;
    //无法修复
    public static final Integer UNFIXABLE = 3;
}
