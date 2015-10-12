package com.from.nowhere.rest.resource;

import com.from.nowhere.rest.RestValidations;
import com.from.nowhere.service.ProductStore;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProductResource implements ProductStore.Container, RestValidations
{
    private static ProductResource instance = new ProductResource();
    public static ProductResource getInstance() {
        return instance;
    }

    public void getProduct(RoutingContext routingContext) {
        notNull(routingContext.request(), "productID");
        String productID = routingContext.request().getParam("productID");
        HttpServerResponse response = routingContext.response();

        JsonObject product = getProductStore().getProduct(productID);
        response.end(product.encodePrettily());
    }

    public void putProduct(RoutingContext routingContext) {
        notNull(routingContext.request(), "productID");
        String productID = routingContext.request().getParam("productID");
        JsonObject product = routingContext.getBodyAsJson();

        getProductStore().addProduct(productID, product);
        routingContext.response().end();
    }

    public void getProducts(RoutingContext routingContext) {
        JsonArray arr = getProductStore().getProducts();
        routingContext.response().putHeader("content-type", "text/html").end(arr.encodePrettily());
    }

}
