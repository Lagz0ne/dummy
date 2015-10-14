package com.from.nowhere.rest;

import io.vertx.rxjava.core.http.HttpServerRequest;

import java.util.Objects;

public interface RestValidations {

    default void notNull(HttpServerRequest request, String... args) {
        Objects.nonNull(request);
        Objects.nonNull(args);

        for (String arg : args) {
            if (request.getParam(arg) == null) throw new RestValidationError();
        }
    }
}
