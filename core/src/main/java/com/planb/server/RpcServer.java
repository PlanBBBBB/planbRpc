package com.planb.server;

/**
 * @author PlanB
 * 服务启动器接口
 */
public interface RpcServer {
    /**
     * 启动服务
     */
    void start(int port);
}
