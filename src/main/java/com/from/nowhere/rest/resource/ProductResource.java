package com.from.nowhere.rest.resource;

import com.from.nowhere.rest.RestValidations;
import com.from.nowhere.service.ProductManagerImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Observable;

public class ProductResource implements ProductManagerImpl.Container, RestValidations {

    private static ProductResource instance = new ProductResource();
    public static ProductResource getInstance() {
        return instance;
    }

    public Observable<JsonObject> getProduct(RoutingContext routingContext) {
        notNull(routingContext.request(), "productID");
        String productID = routingContext.request().getParam("productID");

        return getProductManager().getProduct(productID);
    }

    public Observable<JsonArray> getProducts(RoutingContext routingContext) {
        return getProductManager().getProducts()
                .map(JsonArray::new);
    }

}