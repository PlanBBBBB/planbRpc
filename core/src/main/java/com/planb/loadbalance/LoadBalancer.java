package com.planb.loadbalance;


import com.planb.model.RpcRequest;
import com.planb.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author PlanB
 * 负载均衡器（消费端使用）
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     */
    ServiceMetaInfo select(RpcRequest rpcRequest, List<ServiceMetaInfo> serviceMetaInfoList);
}
