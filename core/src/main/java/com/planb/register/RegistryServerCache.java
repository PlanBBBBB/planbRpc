package com.planb.register;

import com.planb.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author PlanB
 * 注册中心缓存
 */
public class RegistryServerCache {

    /**
     * 服务缓存列表
     */
    List<ServiceMetaInfo> serviceCache;


    /**
     * 写缓存
     */
    public void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     */
    public List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        this.serviceCache = null;
    }
}
