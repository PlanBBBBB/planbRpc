package com.planb.retry;


import com.planb.retry.impl.NoRetry;
import com.planb.spi.SpiLoader;

/**
 * 重试策略工厂（用于获取重试器对象）
 */
public class RetryFactory {

    static {
        SpiLoader.load(Retry.class);
    }

    /**
     * 默认重试器
     */
    private static final Retry DEFAULT_RETRY_STRATEGY = new NoRetry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Retry getInstance(String key) {
        return SpiLoader.getInstance(Retry.class, key);
    }

}
