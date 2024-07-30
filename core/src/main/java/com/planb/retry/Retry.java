package com.planb.retry;


import com.planb.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author PlanB
 * 重试策略
 */
public interface Retry {

    /**
     * 重试
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
