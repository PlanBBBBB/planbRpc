package com.planb.annotation;

import com.planb.constant.RpcConstant;
import com.planb.loadbalance.LoadBalancerKeys;
import com.planb.retry.RetryKeys;
import com.planb.tolerant.TolerantKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解（用于注入服务）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryKeys.NO;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantKeys.FAIL_FAST;

}
