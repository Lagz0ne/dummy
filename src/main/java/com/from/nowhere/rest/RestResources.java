package com.from.nowhere.rest;

import com.from.nowhere.rest.resource.ProductResource;
import io.vertx.rxjava.ext.web.Router;

public class RestResources implements RestResourcesHelper {

    private final Router router;

    private RestResources(Router router) {
        this.router = router;
    }

    @Override
    public Router getRouter() {
        return router;
    }

    static void registerModules(Router router) {
        new RestResources(router).registerProductResource();
    }

    private void registerProductResource() {
        ProductResource productResource = ProductResource.getInstance();

        GET("/product/:productID", productResource::getProduct);
        GET("/products", productResource::getProducts);
    }


}
