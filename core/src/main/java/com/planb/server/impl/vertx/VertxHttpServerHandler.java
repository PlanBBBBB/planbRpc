package com.planb.server.impl.vertx;

import com.planb.model.RpcRequest;
import com.planb.model.RpcResponse;
import com.planb.register.LocalRegister;
import com.planb.serialization.Serialization;
import com.planb.serialization.impl.JdkSerialization;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.lang.reflect.Method;

/**
 * HTTP 请求处理
 */
public class VertxHttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        // 指定序列化器
        Serialization serialization = new JdkSerialization();

        // 记录日志
        System.out.println("HTTP 请求处理" + request.method() + " " + request.uri());

        // 异步处理 Http 请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serialization.deserialize(RpcRequest.class, bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                // 响应空结果
                doResponses(request, rpcResponse, serialization);
                return;
            }

            try {
                // 获取要调用的服务实现类，通过反射调用
                Class<?> serviceClass = LocalRegister.get(rpcRequest.getServiceName());
                Method method = serviceClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(serviceClass.newInstance(), rpcRequest.getParameters());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponses(request, rpcResponse, serialization);
        });
    }

    /**
     * 处理响应
     *
     * @param request       请求
     * @param rpcResponse   响应体
     * @param serialization 序列化器
     */
    void doResponses(HttpServerRequest request, RpcResponse rpcResponse, Serialization serialization) {
        HttpServerResponse http1xServerResponse = request.response().putHeader("Content-Type", "application/json");
        try {
            // 序列化响应
            byte[] serialize = serialization.serialize(rpcResponse);
            http1xServerResponse.end(Buffer.buffer(serialize));
        } catch (Exception e) {
            e.printStackTrace();
            http1xServerResponse.end(Buffer.buffer());
        }
    }
}
