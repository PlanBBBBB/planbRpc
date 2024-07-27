package com.planb.config;

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
}
