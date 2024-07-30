package com.planb.retry.impl;

import com.github.rholder.retry.*;
import com.planb.model.RpcResponse;
import com.planb.retry.Retry;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author PlanB
 * 固定时间间隔
 */
public class FixedIntervalRetry implements Retry {

    /**
     * 重试
     */
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws ExecutionException, RetryException {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        System.out.println("重试次数" + attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }

}
