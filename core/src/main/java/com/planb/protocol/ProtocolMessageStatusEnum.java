package com.planb.protocol;

import lombok.Getter;

/**
 * @author PlanB
 * 协议消息的状态枚举
 */
@Getter
public enum ProtocolMessageStatusEnum {

    OK("ok", 20),
    BAD_REQUEST("badRequest", 40),
    BAD_RESPONSE("badResponse", 50);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据状态值获取枚举
     *
     * @param value 状态值
     * @return 枚举
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum protocolMessageStatusEnum : ProtocolMessageStatusEnum.values()) {
            if (protocolMessageStatusEnum.getValue() == value) {
                return protocolMessageStatusEnum;
            }
        }
        return null;
    }
}
