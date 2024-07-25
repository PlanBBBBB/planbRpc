package com.planb;

import com.planb.proxy.ServiceProxyFactory;
import com.planb.service.HelloService;

public class Consumer {
    public static void main(String[] args) {
        HelloService helloService = ServiceProxyFactory.getServiceProxy(HelloService.class);
        String result = helloService.sayHello("planb");
        System.out.println(result);
    }
}
