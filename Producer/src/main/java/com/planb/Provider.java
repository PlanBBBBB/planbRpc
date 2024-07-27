package com.planb;

import com.planb.register.LocalRegister;
import com.planb.server.RpcServer;
import com.planb.server.impl.vertx.VertxHttpServer;
import com.planb.service.HelloService;
import com.planb.service.impl.HelloServiceImpl;

public class Provider {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        // 注册服务
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);
        // 启动服务
        RpcServer server = new VertxHttpServer();
        server.start(RpcApplication.getRpcConfig().getServerPort());
    }
}
