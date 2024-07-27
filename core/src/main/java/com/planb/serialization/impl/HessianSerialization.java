package com.planb.serialization.impl;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.planb.serialization.Serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian 序列化算法
 */
public class HessianSerialization implements Serialization {
    @Override
    public <T> byte[] serialize(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HessianSerializerOutput hso = new HessianSerializerOutput(baos);
            hso.writeObject(object);
            hso.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Hessian 序列化失败，", e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            HessianSerializerInput hsi = new HessianSerializerInput(bis);
            return (T) hsi.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Hessian 反序列化失败，", e);
        }
    }

}
