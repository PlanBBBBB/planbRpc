package com.planb.tolerant.impl;

import cn.hutool.core.collection.CollUtil;
import com.planb.loadbalance.LoadBalancer;
import com.planb.loadbalance.LoadBalancerFactory;
import com.planb.loadbalance.LoadBalancerKeys;
import com.planb.model.RpcRequest;
import com.planb.model.RpcResponse;
import com.planb.model.ServiceMetaInfo;
import com.planb.server.impl.tcp.VertxTcpClient;
import com.planb.tolerant.Tolerant;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 转移到其他服务节点 - 容错策略
 */
@Slf4j
public class FailOverTolerant implements Tolerant {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceList");
        ServiceMetaInfo errorService = (ServiceMetaInfo) context.get("errorService");
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        // 从服务列表中移除错误服务
        serviceMetaInfoList.remove(errorService);
        // 重新调用其他服务
        if (CollUtil.isNotEmpty(serviceMetaInfoList)) {
            // 重新调用其他服务
            // 负载均衡
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(LoadBalancerKeys.ROUND_ROBIN);
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(rpcRequest, serviceMetaInfoList);
            try {
                return VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
}
