package com.planb.proxy;

import cn.hutool.core.collection.CollUtil;
import com.planb.RpcApplication;
import com.planb.config.RpcConfig;
import com.planb.constant.RpcConstant;
import com.planb.loadbalance.LoadBalancer;
import com.planb.loadbalance.LoadBalancerFactory;
import com.planb.model.RpcRequest;
import com.planb.model.RpcResponse;
import com.planb.model.ServiceMetaInfo;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.retry.Retry;
import com.planb.retry.RetryFactory;
import com.planb.server.impl.tcp.VertxTcpClient;
import com.planb.tolerant.TolerantFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author PlanB
 * 服务代理（动态代理）
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serverName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serverName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        try {
            // 使用注册中心和服务发现机制解决
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serverName);
            serviceMetaInfo.setServerVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂未发现服务");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(rpcRequest, serviceMetaInfoList);

            // 发送TCP请求 - 使用重试机制
            RpcResponse rpcResponse;
            try {
                Retry retry = RetryFactory.getInstance(rpcConfig.getRetry());
                rpcResponse = retry.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
            } catch (Exception e) {
                // 容错机制
                return TolerantFactory.getInstance(rpcConfig.getTolerant()).doTolerant(null, e);
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
