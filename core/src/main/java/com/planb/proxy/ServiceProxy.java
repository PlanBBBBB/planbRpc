package com.planb.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.planb.RpcApplication;
import com.planb.config.RpcConfig;
import com.planb.constant.RpcConstant;
import com.planb.model.RpcRequest;
import com.planb.model.RpcResponse;
import com.planb.model.ServiceMetaInfo;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.serialization.Serialization;
import com.planb.serialization.SerializerFactory;

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
        // 指定序列化器
        final Serialization serialization = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerialization());
        // 构造请求
        String serverName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serverName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        try {
            // 序列化
            byte[] bytes = serialization.serialize(rpcRequest);

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
            // todo 暂时取第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServerAddress())
                    .body(bytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serialization.deserialize(RpcResponse.class, result);
                return rpcResponse.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
