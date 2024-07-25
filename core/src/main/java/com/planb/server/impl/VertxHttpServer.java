package com.planb.server.impl;

import com.planb.server.HttpServer;
import io.vertx.core.Vertx;

/**
 * VertxHttpServer 服务启动器
 */
public class VertxHttpServer implements HttpServer {
    @Override
    public void start(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(new HttpServerHandler());

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port " + port);
            } else {
                System.out.println("Server failed to start " + result.cause());
            }
        });
    }
}
