package com.planb.serialization.impl;

import com.planb.serialization.Serialization;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Protostuff 序列化算法实现类
 */
public class ProtostuffSerialization implements Serialization {

    /**
     * 提前分配好 Buffer，避免每次进行序列化都需要重新分配 buffer 内存空间
     */
    private final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> byte[] serialize(T object) {
        try {
            Schema schema = RuntimeSchema.getSchema(object.getClass());
            return ProtostuffIOUtil.toByteArray(object, schema, BUFFER);
        } catch (Exception e) {
            throw new RuntimeException("Protostuff 序列化失败，", e);
        } finally {
            // 重置 buffer
            BUFFER.clear();
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            Schema<T> schema = RuntimeSchema.getSchema(clazz);
            T object = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, object, schema);
            return object;
        } catch (Exception e) {
            throw new RuntimeException("Protostuff 反序列化失败，", e);
        }
    }
}
