package com.from.nowhere.rest;

import com.from.nowhere.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;

public class RestVerticle extends AbstractVerticle {

    public static void main(String args[]) {
        Runner.run(RestVerticle.class);
    }

    @Override
    public void start() {
        startRestServer();
    }

    private void startRestServer() {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.route().handler(new RestHeaderHandler());
        router.route().failureHandler(new RestErrorHandler());

        RestResources.registerModules(router);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

}