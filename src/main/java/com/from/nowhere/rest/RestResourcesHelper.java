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

public interface RestResourcesHelper {

    Router getRouter();

    default void GET(String path, Function<RoutingContext, Observable<JsonObject>> handler) {
        register(GET, path, handler);
    }

    default void register(HttpMethod method, String path, Function<RoutingContext, Observable<JsonObject>> handler) {
        getRouter().route(method, path).handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            handler.apply(routingContext)
                    .subscribe(
                            result    -> response.end(result.encodePrettily()),
                            throwable -> response.end(generateErrorJson(throwable).encodePrettily())
                    );
        });
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
