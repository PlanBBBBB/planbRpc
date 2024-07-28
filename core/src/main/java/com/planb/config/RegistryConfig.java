package com.planb.config;

import lombok.Data;

/**
 * @author PlanB
 * RPC 注册中心配置类
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";

    /**
     * 注册中心用户名
     */
    private String username;

    /**
     * 注册中心密码
     */
    private String password;

    /**
     * 超时时间（毫秒）
     */
    private Long timeout = 10000L;
}
