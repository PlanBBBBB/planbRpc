package com.planb.tolerant.impl;


import com.planb.model.RpcResponse;
import com.planb.tolerant.Tolerant;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 */
public class FailFastTolerant implements Tolerant {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
