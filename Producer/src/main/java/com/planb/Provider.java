package com.planb;

import com.planb.config.RegistryConfig;
import com.planb.config.RpcConfig;
import com.planb.model.ServiceMetaInfo;
import com.planb.register.LocalRegister;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.server.RpcServer;
import com.planb.server.impl.tcp.VertxTcpServer;
import com.planb.service.HelloService;
import com.planb.service.impl.HelloServiceImpl;

public class Provider {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        // 注册服务
        String serverName = HelloService.class.getName();
        LocalRegister.register(serverName, HelloServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serverName);
        serviceMetaInfo.setServerVersion(rpcConfig.getVersion());
        serviceMetaInfo.setServerHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServerPort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 启动服务
//        RpcServer server = new VertxHttpServer();
//        server.start(RpcApplication.getRpcConfig().getServerPort());

        // 启动TCP服务
        RpcServer server = new VertxTcpServer();
        server.start(RpcApplication.getRpcConfig().getServerPort());
    }
}
