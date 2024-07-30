package com.planb.retry.impl;

import com.planb.model.RpcResponse;
import com.planb.retry.Retry;

import java.util.concurrent.Callable;

/**
 * @author PlanB
 * 不重试
 */
public class NoRetry implements Retry {

    /**
     * 重试
     */
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }

}
