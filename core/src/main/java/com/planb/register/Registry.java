package com.planb.register;

import com.planb.config.RegistryConfig;
import com.planb.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author PlanB
 * 注册中心接口
 */
public interface Registry {

    /**
     * 初始化注册中心
     *
     * @param registryConfig 注册中心配置
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo 服务元信息
     * @throws Exception 注册失败信息
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;


    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo 服务元信息
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（客户端）
     *
     * @param serviceKey 服务key
     * @return 服务元信息列表
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 关闭注册中心
     */
    void close();
}
