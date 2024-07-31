package com.planb;

import com.planb.bootstrap.ConsumerBootstrap;
import com.planb.proxy.ServiceProxyFactory;
import com.planb.service.HelloService;

public class Consumer {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();
        // 获取服务代理
        HelloService helloService = ServiceProxyFactory.getServiceProxy(HelloService.class);
        String result = helloService.sayHello("planb");
        System.out.println(result);
    }
}
