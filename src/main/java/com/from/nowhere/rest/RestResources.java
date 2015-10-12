package com.from.nowhere.rest;

import com.from.nowhere.rest.resource.ProductResource;
import io.vertx.ext.web.Router;

public class RestResources {

    public static void registerModules(Router router) {
        registerProductResource(router);
    }

    private static void registerProductResource(Router router) {
        ProductResource productResource = ProductResource.getInstance();

        router.get("/product/:productID").handler(productResource::getProduct);
        router.put("/product/:productID").handler(productResource::putProduct);
        router.get("/product").handler(productResource::getProducts);
    }

}
