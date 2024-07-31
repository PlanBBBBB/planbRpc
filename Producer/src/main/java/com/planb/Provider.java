package com.planb;

import com.planb.bootstrap.ProviderBootstrap;
import com.planb.model.ServiceRegisterInfo;
import com.planb.service.HelloService;
import com.planb.service.impl.HelloServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Provider {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfos = new ArrayList<>();
        ServiceRegisterInfo<HelloService> serviceRegisterInfo = new ServiceRegisterInfo<>(HelloService.class.getName(), HelloServiceImpl.class);
        serviceRegisterInfos.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfos);
    }
}
