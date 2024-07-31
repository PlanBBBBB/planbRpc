package com.planb.tolerant;


import com.planb.spi.SpiLoader;
import com.planb.tolerant.impl.FailFastTolerant;

/**
 * 容错策略工厂（工厂模式，用于获取容错策略对象）
 */
public class TolerantFactory {

    static {
        SpiLoader.load(Tolerant.class);
    }

    /**
     * 默认容错策略
     */
    private static final Tolerant DEFAULT_RETRY_STRATEGY = new FailFastTolerant();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Tolerant getInstance(String key) {
        return SpiLoader.getInstance(Tolerant.class, key);
    }

}
