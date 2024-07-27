package com.planb.serialization;

import com.planb.serialization.impl.*;
import com.planb.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PlanB
 * 序列化器工厂
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serialization.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serialization DEFAULT_SERIALIZATION = new JdkSerialization();


    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serialization getInstance(String key) {
        return SpiLoader.getInstance(Serialization.class, key);
    }
}
