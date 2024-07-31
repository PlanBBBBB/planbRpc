package com.planb.tolerant.impl;

import com.planb.RpcApplication;
import com.planb.config.RpcConfig;
import com.planb.model.RpcResponse;
import com.planb.tolerant.mock.MockService;
import com.planb.tolerant.mock.MockServiceFactory;
import com.planb.tolerant.Tolerant;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 降级到其他服务 - 容错策略
 */
@Slf4j
public class FailBackTolerant implements Tolerant {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        MockService mockService = MockServiceFactory.getInstance(rpcConfig.getMockService());
        Object mock = mockService.mock();
        return RpcResponse.builder().data(mock).message("ok").build();
    }
}
