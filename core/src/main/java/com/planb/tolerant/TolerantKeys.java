package com.planb.tolerant;

/**
 * 容错策略键名常量
 */
public class TolerantKeys {

    /**
     * 故障恢复
     */
    public static final String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    public static final String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    public static final String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    public static final String FAIL_SAFE = "failSafe";

}
