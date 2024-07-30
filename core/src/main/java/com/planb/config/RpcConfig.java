package com.planb.config;

import com.planb.loadbalance.LoadBalancerKeys;
import com.planb.retry.RetryKeys;
import lombok.Data;

/**
 * @author PlanB
 * RPC 框架配置类
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "planbRpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口
     */
    private Integer serverPort = 8080;

    /**
     * 序列化方式
     */
    private String serialization = "jdk";

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retry = RetryKeys.NO;
}
