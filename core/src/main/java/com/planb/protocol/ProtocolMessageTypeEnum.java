package com.planb.protocol;

import lombok.Getter;

/**
 * @author PlanB
 * 协议消息的类型枚举
 */
@Getter
public enum ProtocolMessageTypeEnum {

    REQUEST(0),
    RESPONSE(1),
    HEARTBEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据key获取枚举
     *
     * @param key
     * @return
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum protocolMessageTypeEnum : ProtocolMessageTypeEnum.values()) {
            if (protocolMessageTypeEnum.getKey() == key) {
                return protocolMessageTypeEnum;
            }
        }
        return null;
    }
}
