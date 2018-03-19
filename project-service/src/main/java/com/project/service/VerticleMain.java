package com.project.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;

import java.util.function.Consumer;


public class VerticleMain extends AbstractVerticle {


    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {
            routingContext.response()
                    .putHeader("content-type","text/html;charset=UTF-8")
                    .end("我的人设开始");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8181);
    }

    public static void deployVertx() {
        String verticleId = VerticleMain.class.getName();
        VertxOptions options = new VertxOptions();
        Consumer<Vertx> runner = vertxStart -> {
            vertxStart.deployVerticle(verticleId);
        };
        Vertx vertx = Vertx.vertx(options);
        runner.accept(vertx);
    }

    public static void main(String[] args) {

        VerticleMain.deployVertx();
    }
}