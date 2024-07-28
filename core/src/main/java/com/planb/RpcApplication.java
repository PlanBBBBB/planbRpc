package com.planb;

import com.planb.config.RegistryConfig;
import com.planb.config.RpcConfig;
import com.planb.constant.RpcConstant;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.util.ConfigUtils;

/**
 * @author PlanB
 * RPC 框架应用 双检锁单例模式实现
 */
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        System.out.println("RpcApplication init :" + newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        System.out.println("registry init: " + registryConfig);

        // 创建并注册Shutdown Hook，JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::close));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
