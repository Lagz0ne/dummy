package com.from.nowhere.rest;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RestErrorHandler implements Handler<RoutingContext>
{
    @Override
    public void handle(RoutingContext context)
    {
        HttpServerResponse response = context.response();

        JsonObject jsonError = new JsonObject();
        if (context.failure() != null) {
            if (context.failure() instanceof RestValidationError) {
                jsonError.put("error", new JsonObject().put("code", 400).put("message", context.failure().getMessage()));
            } else {
                jsonError.put("error", new JsonObject().put("code", 500).put("message", context.failure().getMessage()));
                JsonArray stack = new JsonArray();
                for (StackTraceElement elem: context.failure().getStackTrace()) {
                    stack.add(elem.toString());
                }
                jsonError.put("stack", stack);
            }
        }
        response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.end(jsonError.encode());
    }
}
