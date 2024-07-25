package com.planb.proxy;

import java.lang.reflect.Proxy;

/**
 * @author PlanB
 * 服务代理工厂
 */
public class ServiceProxyFactory {

    /**
     * 获取服务代理
     *
     * @param serviceClass 服务接口
     * @return 服务代理
     */
    public static <T> T getServiceProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
