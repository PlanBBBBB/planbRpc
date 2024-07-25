package com.planb;

import com.planb.register.LocalRegister;
import com.planb.server.HttpServer;
import com.planb.server.impl.VertxHttpServer;
import com.planb.service.HelloService;
import com.planb.service.impl.HelloServiceImpl;

public class Provider {
    public static void main(String[] args) {
        // 注册服务
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);
        // 启动服务
        HttpServer server = new VertxHttpServer();
        server.start(8080);
    }
}
