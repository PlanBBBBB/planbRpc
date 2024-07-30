package com.planb.protocol;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PlanB
 * 协议消息的序列化器枚举
 */
@Getter
public enum ProtocolMessageSerializationEnum {

    JDK(0, "jdk"),
    PROTOSTUFF(1, "protostuff"),
    HESSIAN(2, "hessian"),
    JSON(3, "json"),
    KRYO(4, "kryo");


    private final int key;

    private final String value;

    ProtocolMessageSerializationEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据key获取枚举
     *
     * @param key
     * @return
     */
    public static ProtocolMessageSerializationEnum getEnumByKey(int key) {
        for (ProtocolMessageSerializationEnum protocolMessageSerializationEnum : ProtocolMessageSerializationEnum.values()) {
            if (protocolMessageSerializationEnum.getKey() == key) {
                return protocolMessageSerializationEnum;
            }
        }
        return null;
    }

    /**
     * 获取所有枚举值
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }


    /**
     * 根据value获取枚举
     *
     * @param value
     * @return
     */
    public static ProtocolMessageSerializationEnum getEnumByValue(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (ProtocolMessageSerializationEnum protocolMessageSerializationEnum : ProtocolMessageSerializationEnum.values()) {
            if (protocolMessageSerializationEnum.value.equals(value)) {
                return protocolMessageSerializationEnum;
            }
        }
        return null;
    }
}
