package com.planb.register;

import com.planb.spi.SpiLoader;

/**
 * @author PlanB
 * 注册中心工厂类（用于获取注册中心）
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
