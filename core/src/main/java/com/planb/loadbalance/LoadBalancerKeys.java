package com.planb.loadbalance;

/**
 * 负载均衡策略常量类
 */
public class LoadBalancerKeys {

    /**
     * 轮询
     */
    public static final String ROUND_ROBIN = "roundRobin";

    /**
     * 随机
     */
    public static final String RANDOM = "random";

    /**
     * 一致性哈希
     */
    public static final String CONSISTENT_HASH = "consistentHash";
}
