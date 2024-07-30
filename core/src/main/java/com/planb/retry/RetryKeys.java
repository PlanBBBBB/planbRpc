package com.planb.retry;

/**
 * 重试策略键名常量
 */
public interface RetryKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
