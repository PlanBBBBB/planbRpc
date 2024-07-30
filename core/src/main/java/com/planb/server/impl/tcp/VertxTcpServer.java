package com.planb.server.impl.tcp;

import com.planb.server.RpcServer;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

/**
 * @author PlanB
 * VertxTcpServer 服务启动器
 */
public class VertxTcpServer implements RpcServer {

    @Override
    public void start(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(new TcpServerHandler());

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.out.println("Failed to start TCP server: " + result.cause());
            }
        });
    }
}
