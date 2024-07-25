package com.planb.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PlanB
 * 本地注册中心
 */
public class LocalRegister {

    /**
     * 注册信息存储
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param serviceName  服务名称
     * @param serviceClass 服务实例
     */
    public static void register(String serviceName, Class<?> serviceClass) {
        map.put(serviceName, serviceClass);
    }

    /**
     * 获取服务
     *
     * @param serviceName 服务名称
     * @return 服务实例
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    /**
     * 删除服务
     *
     * @param serviceName 服务名称
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
