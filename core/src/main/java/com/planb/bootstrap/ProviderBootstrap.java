package com.planb.bootstrap;

import com.planb.RpcApplication;
import com.planb.config.RegistryConfig;
import com.planb.config.RpcConfig;
import com.planb.model.ServiceMetaInfo;
import com.planb.model.ServiceRegisterInfo;
import com.planb.register.LocalRegister;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.server.impl.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者启动类（初始化）
 */
public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegister.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServerVersion(rpcConfig.getVersion());
            serviceMetaInfo.setServerHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServerPort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.start(rpcConfig.getServerPort());
    }
}
