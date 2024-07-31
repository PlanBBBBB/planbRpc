package com.planb.tolerant.mock;

import com.planb.spi.SpiLoader;

public class MockServiceFactory {

    static {
        SpiLoader.load(MockService.class);
    }


    /**
     * 获取实例
     *
     * @param key 钥匙
     * @return {@link MockService}
     */
    public static MockService getInstance(String key) {
        return SpiLoader.getInstance(MockService.class, key);
    }

}