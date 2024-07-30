package com.planb.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.planb.RpcApplication;
import com.planb.config.RpcConfig;
import com.planb.constant.RpcConstant;
import com.planb.model.RpcRequest;
import com.planb.model.RpcResponse;
import com.planb.model.ServiceMetaInfo;
import com.planb.protocol.*;
import com.planb.register.Registry;
import com.planb.register.RegistryFactory;
import com.planb.serialization.Serialization;
import com.planb.serialization.SerializerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

//            // 发送请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServerAddress())
//                    .body(bytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                // 反序列化
//                RpcResponse rpcResponse = serialization.deserialize(RpcResponse.class, result);
//                return rpcResponse.getData();
//            }
            // 发送TCP请求
            Vertx vertx = Vertx.vertx();
            NetClient netClient = vertx.createNetClient();
            CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
            netClient.connect(selectedServiceMetaInfo.getServerPort(), selectedServiceMetaInfo.getServerHost(),
                    result -> {
                        if (result.succeeded()) {
                            System.out.println("TCP连接成功");
                            io.vertx.core.net.NetSocket socket = result.result();
                            // 发送消息
                            // 构造消息
                            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                            ProtocolMessage.Header header = new ProtocolMessage.Header();
                            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                            header.setSerialization((byte) ProtocolMessageSerializationEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerialization()).getKey());
                            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                            header.setRequestId(IdUtil.getSnowflakeNextId());
                            protocolMessage.setHeader(header);
                            protocolMessage.setBody(rpcRequest);
                            // 编码请求
                            try {
                                Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                                socket.write(encodeBuffer);
                            } catch (IOException e) {
                                throw new RuntimeException("协议信息编码失败");
                            }

                            // 接受响应
                            socket.handler(buffer -> {
                                try {
                                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException("协议信息解码失败");
                                }
                            });
                        } else {
                            System.out.println("TCP连接失败");
                        }
                    });

            RpcResponse rpcResponse = responseFuture.get();
            // 关闭连接
            netClient.close();
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
