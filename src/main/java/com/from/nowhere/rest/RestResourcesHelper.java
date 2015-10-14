package com.from.nowhere.rest;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Observable;

import java.util.function.Function;

import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;

public interface RestResourcesHelper {

    Router getRouter();

    default void GET(String path, Function<RoutingContext, Observable<?>> handler) {
        register(GET, path, handler);
    }

    default void PUT(String path, Function<RoutingContext, Observable<?>> handler) {
        register(PUT, path, handler);
    }

    default void POST(String path, Function<RoutingContext, Observable<?>> handler) {
        register(POST, path, handler);
    }

    default void register(HttpMethod method, String path, Function<RoutingContext, Observable<?>> handler) {
        getRouter().route(method, path).handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            handler.apply(routingContext)
                    .subscribe(
                            result    -> handleResult(response, result),
                            throwable -> response.end(generateErrorJson(throwable).encodePrettily())
                    );
        });
    }

    default void handleResult(HttpServerResponse response, Object result) {
        if (result == null) {
            response.end();
            return;
        }

        if (result instanceof JsonObject) {
            response.end(((JsonObject) result).encodePrettily());
        } else if (result instanceof JsonArray) {
            response.end(((JsonArray) result).encodePrettily());
        } else  {
            response.end(result.toString());
        }
    }

    default JsonObject generateErrorJson(Throwable throwable) {
        JsonObject errorContainer = new JsonObject();
        JsonObject error = new JsonObject();

        if (throwable instanceof RestError) {
            error.put("code", 400);
            error.put("message", throwable.getMessage());
        } else {
            JsonArray stack = new JsonArray();
            for (StackTraceElement elem : throwable.getStackTrace()) {
                stack.add(elem.toString());
            }

            error.put("code", 500);
            error.put("stack", stack);
            error.put("message", throwable.getMessage());
        }
        errorContainer.put("error", error);

        return errorContainer;
    }

}
