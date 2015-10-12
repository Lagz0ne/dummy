package com.from.nowhere.rest;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class RestHeaderHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext event) {
        final HttpServerResponse response = event.response();
        response.headersEndHandler(e -> {
            if (!response.headers().contains("content-type")) {
                response.putHeader("content-type", "application-json");
            }
        });
        event.next();
    }
}
