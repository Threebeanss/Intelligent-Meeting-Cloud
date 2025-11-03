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
    //待审核
    public static final Integer PENDING = 0;
    //已确认
    public static final Integer CONFIRMED = 1;
    //已取消
    public static final Integer CANCELED = 2;
}
